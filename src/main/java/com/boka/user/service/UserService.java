package com.boka.user.service;

import com.boka.common.constant.ProductType;
import com.boka.common.exception.AuthException;
import com.boka.common.exception.CommonException;
import com.boka.common.exception.ExceptionCode;
import com.boka.common.exception.LoginException;
import com.boka.common.util.AuthUtil;
import com.boka.user.dto.PasswordTO;
import com.boka.user.dto.UserTO;
import com.boka.user.model.ReserveInfo;
import com.boka.user.model.User;
import com.boka.user.repository.BaseInfoRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Admin on 2015/5/5 0005.
 */
public abstract class UserService {

    @Autowired
    protected BaseInfoRepository baseInfoRepository;

    @Autowired
    private AuthUtil authUtil;

    /**
     * 用户注册
     */
    public abstract UserTO reg(UserTO user, String deviceId) throws CommonException, LoginException;

    /**
     * 激活保存用户信息
     */
    public abstract UserTO activate(UserTO user) throws CommonException;

    /**
     * 登录
     */
    public abstract UserTO login(UserTO user, String deviceId) throws LoginException, CommonException;

    /**
     * 第三方登录
     */
    public abstract UserTO openAuth(UserTO user, String deviceId) throws Exception;

    /**
     * 绑定手机号
     */
    public abstract UserTO bindMobile(UserTO user, String deviceId) throws CommonException, AuthException;

    /**
     * 修改密码
     */
    public void changePassword(String userId, PasswordTO password) throws CommonException, AuthException {
        User user = baseInfoRepository.findOne(userId);
        if (user == null) {
            throw new CommonException(ExceptionCode.DATA_NOT_EXISTS);
        }
        String secretPassword = DigestUtils.md5Hex(user.getSalt() + password.getOldPassword());
        if (!user.getPassword().equals(secretPassword)) {
            throw new CommonException(ExceptionCode.PASSWORD_ERROR);
        }
        secretPassword = DigestUtils.md5Hex(user.getSalt() + password.getNewPassword());
        user.setPassword(secretPassword);
        user.setUpdateDate(Calendar.getInstance().getTime());
        baseInfoRepository.save(user);
    }

    /**
     * 忘记密码
     */
    public void forgetPassword(UserTO user) throws CommonException {
        //验证码检验
        if (!authUtil.authMobile(user.getMobile(), user.getAuthcode(), user.getProduct())) {
            throw new CommonException(ExceptionCode.MOBILE_AUTH_FAILD);
        }

        User bean = baseInfoRepository.findByMobile(user.getMobile(), user.getProduct());
        if (bean == null) {
            throw new CommonException(ExceptionCode.USER_NOT_EXISTS);
        }
        if(user.getPassword() != null) {
            bean.setResetStatus(0);
            bean.setUpdateDate(Calendar.getInstance().getTime());
            //MD5加盐
            bean.setPassword(DigestUtils.md5Hex(bean.getSalt() + user.getPassword()));
            baseInfoRepository.save(bean);
        }
    }

    /**
     * 修改用户信息
     */
    public UserTO edit(UserTO user) throws CommonException {
        User bean = baseInfoRepository.findOne(user.getId());
        if (bean == null) {
            throw new CommonException(ExceptionCode.DATA_NOT_EXISTS);
        }
        if(user.getName() != null) {
            bean.setName(user.getName());
        }
        if(user.getSex() != 0) {
            bean.setSex(user.getSex());
        }
        if(user.getAvatar() != null) {
            bean.setAvatar(user.getAvatar());
        }
        if (user.getLoc() != null) {
            bean.setLoc(user.getLoc());
        }
        if(user.getRegion() != null) {
            bean.setRegion(user.getRegion());
        }
        baseInfoRepository.save(bean);
        UserTO result = new UserTO();
        result.setId(bean.getId());
        result.setQqId(bean.getQqId());
        result.setWechatId(bean.getWechatId());
        result.setAvatar(bean.getAvatar());
        result.setActivatedStatus(bean.getActivatedStatus());
        result.setMobile(bean.getMobile());
        result.setName(bean.getName());
        result.setSex(bean.getSex());
        return result;
    }

    /**
     * 购买会员时长
     */
    public abstract void upgradeVIP(String userId, int month);

    /**
     * 获取用户信息
     */
    public User getUserById(String userId) {
        return baseInfoRepository.findOne(userId);
    }

    /**
     * 微信openId获取用户信息
     */
    public List<User> getUserByOpenId(String openId) {
        return baseInfoRepository.findUserByOpenId(openId);
    }

    //初始化预约设置
    protected ReserveInfo initReserveInfo() {
        //预约设置信息
        ReserveInfo result = new ReserveInfo();
        result.setStatus(1);
        Calendar start = Calendar.getInstance();
        start.set(Calendar.HOUR_OF_DAY, 10);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        result.setStartTime(start.getTime());
        Calendar end = Calendar.getInstance();
        end.set(Calendar.HOUR_OF_DAY, 22);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        end.set(Calendar.MILLISECOND, 0);
        result.setEndTime(end.getTime());
        result.setInterval(30);
        result.setInAdvanceMin(0);
        result.setInAdvanceMax(10);
        return result;
    }

}
