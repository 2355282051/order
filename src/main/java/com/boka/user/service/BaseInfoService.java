package com.boka.user.service;

import com.boka.common.constant.Constant;
import com.boka.common.constant.ProductType;
import com.boka.common.exception.CommonException;
import com.boka.common.exception.ExceptionCode;
import com.boka.common.exception.LoginException;
import com.boka.common.util.Assert;
import com.boka.common.util.AuthUtil;
import com.boka.common.util.RandomUtil;
import com.boka.user.constant.StatusConstant;
import com.boka.user.dto.ResultTO;
import com.boka.user.dto.UserTO;
import com.boka.user.model.User;
import com.boka.user.repository.BaseInfoRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Service
public class BaseInfoService {

    @Autowired
    private BaseInfoRepository baseInfoRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthUtil authUtil;

    /**
     * 用户注册
     * @param user
     * @return
     * @throws CommonException
     */
    public String reg(UserTO user) throws CommonException {
        //验证码检验
        if (!authUtil.authMobile(user.getMobile(), user.getAuthcode(), ProductType.BEAUTY)) {
            throw new CommonException(ExceptionCode.MOBILE_AUTH_FAILD);
        }
        if (Assert.isNull(user.getPassword())) {
            throw new CommonException(ExceptionCode.PARAM_NULL);
        }

        User bean = baseInfoRepository.findByMobile(user.getMobile());
        if(bean != null) {
            throw new CommonException(ExceptionCode.MOBILE_EXISTS);
        }
        bean = new User();
        bean.setCreateDate(Calendar.getInstance().getTime());
        bean.setMobile(user.getMobile());
        bean.setSalt(RandomUtil.randomSalt());
        //MD5加盐
        bean.setPassword(DigestUtils.md5Hex(bean.getSalt() + user.getPassword()));
        bean = baseInfoRepository.save(bean);
        //生成token
        return authUtil.getToken(bean.getId());
    }

    /**
     * 激活保存用户信息
     * @param user
     * @return
     * @throws CommonException
     */
    public UserTO activate(UserTO user) throws CommonException {

        User bean = baseInfoRepository.findOne(user.getId());
        bean.setName(user.getName());
        bean.setAvatar(user.getAvatar());
        bean.setSex(user.getSex());
        bean.setUpdateDate(Calendar.getInstance().getTime());
        bean.setLastLoginDate(bean.getCreateDate());
        bean.setLoc(user.getLoc());
        bean.setActivatedStatus(StatusConstant.activated);
        bean.setProduct(user.getProduct());
        baseInfoRepository.save(bean);
        UserTO result = new UserTO();
        result.setAvatar(bean.getAvatar());
        result.setActivatedStatus(bean.getActivatedStatus());
        result.setMobile(bean.getMobile());
        result.setName(bean.getName());
        result.setLoc(user.getLoc());
        result.setSex(bean.getSex());
        return result;
    }

    /**
     * 登录
     * @param user
     * @return
     * @throws LoginException
     * @throws CommonException
     */
    public UserTO login(UserTO user) throws LoginException, CommonException {
        User bean = baseInfoRepository.findByMobile(user.getMobile());
        if (bean == null) {
            throw new LoginException(ExceptionCode.USER_NOT_EXISTS);
        } else if (!DigestUtils.md5Hex(bean.getSalt() + user.getPassword()).equals(bean.getPassword())) {
            throw new LoginException(ExceptionCode.PASSWORD_ERROR);
        }
        UserTO result = new UserTO();
        result.setAvatar(bean.getAvatar());
        result.setActivatedStatus(bean.getActivatedStatus());
        result.setMobile(bean.getMobile());
        result.setName(bean.getName());
        result.setSex(bean.getSex());
        result.setAccess_token(authUtil.getToken(bean.getId()));
        return result;
    }

    /**
     * 第三方登录
     * @param user
     * @return
     * @throws Exception
     */
    public UserTO openAuth(UserTO user) throws Exception {
        User bean = null;
        if(Assert.isNotNull(user.getQqId())) {
            bean = baseInfoRepository.findByQqId(user.getQqId());
        } else if(Assert.isNotNull(user.getWechatId())) {
            bean = baseInfoRepository.findByWechatId(user.getWechatId());
        }
        if (bean == null) {
            bean = new User();
            bean.setCreateDate(Calendar.getInstance().getTime());
            bean.setSalt(RandomUtil.randomSalt());
            bean.setProduct(user.getProduct());
            bean.setActivatedStatus(StatusConstant.openauth_inactive);
            bean.setQqId(user.getQqId());
            bean.setWechatId(user.getWechatId());
        } else {
            bean.setUpdateDate(Calendar.getInstance().getTime());
        }
        bean.setAvatar(user.getAvatar());
        bean.setLoc(user.getLoc());
        bean.setName(user.getName());
        bean.setSex(user.getSex());
        bean.setLastLoginDate(Calendar.getInstance().getTime());
        bean = baseInfoRepository.save(bean);
        // 同步Show用户信息
        if(!bean.getAvatar().equals(user.getAvatar()) || bean.getSex() != user.getSex() || !bean.getName().equals(user.getName())) {
            syncUser(user);
        }
        // 将新的用户ID绑定到access_token上
        authUtil.saveOpenAuthToken(user.getAccess_token(), bean.getId());
        UserTO result = new UserTO();
        result.setAvatar(bean.getAvatar());
        result.setActivatedStatus(bean.getActivatedStatus());
        result.setMobile(bean.getMobile());
        result.setName(bean.getName());
        result.setSex(bean.getSex());
        result.setQqId(bean.getQqId());
        result.setWechatId(bean.getWechatId());
        return result;

    }

    /**
     * 绑定手机号
     * @param user
     * @return
     * @throws CommonException
     */
    public UserTO bindMobile(UserTO user) throws CommonException {
        //验证码检验
        if (!authUtil.authMobile(user.getMobile(), user.getAuthcode(), ProductType.BEAUTY)) {
            throw new CommonException(ExceptionCode.AUTH_FAILD);
        }
        if (Assert.isNull(user.getPassword())) {
            throw new CommonException(ExceptionCode.PARAM_NULL);
        }
        User bean = baseInfoRepository.findByMobile(user.getMobile());
        if(bean != null) {
            User openAuthUser = baseInfoRepository.findOne(user.getId());
            bean.setQqId(openAuthUser.getQqId());
            bean.setWechatId(openAuthUser.getWechatId());
            openAuthUser.setActivatedStatus(StatusConstant.removed);
            //将第三方登录帐号注销
            baseInfoRepository.updateBindUser(openAuthUser);
        } else {
            bean = baseInfoRepository.findOne(user.getId());
            bean.setMobile(user.getMobile());
            bean.setSalt(RandomUtil.randomSalt());
            bean.setActivatedStatus(StatusConstant.activated);
            //MD5加盐
            bean.setPassword(DigestUtils.md5Hex(bean.getSalt() + user.getPassword()));
        }
        bean.setUpdateDate(Calendar.getInstance().getTime());

        baseInfoRepository.save(bean);
        UserTO result = new UserTO();
        result.setId(bean.getId());
        result.setQqId(bean.getQqId());
        result.setAvatar(bean.getAvatar());
        result.setActivatedStatus(bean.getActivatedStatus());
        result.setMobile(bean.getMobile());
        result.setName(bean.getName());
        result.setSex(bean.getSex());
        return result;
    }

    /**
     * 同步秀里用户信息
     * @param user
     * @return
     */
    public ResultTO syncUser(UserTO user) {
        return restTemplate.postForObject(Constant.SHOW_USER_SYNC_URL, user, ResultTO.class);
    }

}
