package com.boka.user.service.impl;

import com.boka.common.constant.ProductType;
import com.boka.common.exception.AuthException;
import com.boka.common.exception.CommonException;
import com.boka.common.exception.ExceptionCode;
import com.boka.common.exception.LoginException;
import com.boka.common.util.Assert;
import com.boka.common.util.AuthUtil;
import com.boka.common.util.RandomUtil;
import com.boka.user.constant.StatusConstant;
import com.boka.user.dto.Device;
import com.boka.user.dto.PasswordTO;
import com.boka.user.dto.UserTO;
import com.boka.user.model.User;
import com.boka.user.repository.BaseInfoRepository;
import com.boka.user.repository.EmployeeRepository;
import com.boka.user.service.DesktopService;
import com.boka.user.service.DeviceService;
import com.boka.user.service.ShopService;
import com.boka.user.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Admin on 2015/5/5 0005.
 */
@Service("volumeUserService")
public class VolumeUserService extends UserService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ShopService shopService;

    @Autowired
    private DesktopService desktopService;

    @Autowired
    private RestTemplate restTemplate;

    @Resource
    private DeviceService deviceService;

    @Autowired
    private AuthUtil authUtil;

    @Override
    public UserTO reg(UserTO user, String deviceId) throws CommonException, LoginException {
        //验证码检验
        if (!authUtil.authMobile(user.getMobile(), user.getAuthcode(), user.getProduct())) {
            throw new CommonException(ExceptionCode.MOBILE_AUTH_FAILD);
        }
        //校验密码
        if (Assert.isNull(user.getPassword())) {
            throw new CommonException(ExceptionCode.PARAM_NULL);
        }

        User bean = baseInfoRepository.findByMobile(user.getMobile(), user.getProduct());
        if (bean != null) {
            throw new CommonException(ExceptionCode.MOBILE_EXISTS);
        }
        bean = new User();
        bean.setProduct(user.getProduct());
        bean.setCreateDate(Calendar.getInstance().getTime());
        bean.setMobile(user.getMobile());
        bean.setRegProduct(user.getRegProduct());
        bean.setActivatedStatus(user.getActivatedStatus());
        bean.setInviteCode(user.getInviteCode());
        //MD5加盐
        bean.setSalt(RandomUtil.randomSalt());
        bean.setPassword(DigestUtils.md5Hex(bean.getSalt() + user.getPassword()));
        bean = baseInfoRepository.save(bean);
        //生成令牌
        user.setAccess_token(authUtil.getToken(bean.getId(), deviceId));
        user.setCreateDate(bean.getCreateDate());
        //生成token
        return user;
    }

    @Override
    public UserTO activate(UserTO user) throws CommonException {
        User bean = baseInfoRepository.findOne(user.getId());
        bean.setName(user.getName());
        bean.setAvatar(user.getAvatar());
        bean.setSex(user.getSex());
        bean.setUpdateDate(Calendar.getInstance().getTime());
        bean.setLastLoginDate(bean.getCreateDate());
        bean.setLoc(user.getLoc());
        if(Assert.isNotNull(bean.getMobile())) {
            bean.setActivatedStatus(StatusConstant.activated);
        }
        bean.setProduct(ProductType.VOLUME);
        baseInfoRepository.save(bean);
        UserTO result = new UserTO();
        result.setId(bean.getId());
        result.setAvatar(bean.getAvatar());
        result.setActivatedStatus(bean.getActivatedStatus());
        result.setMobile(bean.getMobile());
        result.setName(bean.getName());
        result.setLoc(user.getLoc());
        result.setSex(bean.getSex());
        return result;
    }

    @Override
    public UserTO login(UserTO user, String deviceId) throws LoginException, CommonException {
        User bean = baseInfoRepository.findByMobile(user.getMobile(), user.getProduct());
        if (bean == null) {
            throw new LoginException(ExceptionCode.USER_NOT_EXISTS);
        } else if (!DigestUtils.md5Hex(bean.getSalt() + user.getPassword()).equals(bean.getPassword())) {
            throw new LoginException(ExceptionCode.PASSWORD_ERROR);
        }

        UserTO result = new UserTO();
        result.setId(bean.getId());
        result.setAvatar(bean.getAvatar());
        result.setActivatedStatus(bean.getActivatedStatus());
        result.setMobile(bean.getMobile());
        result.setName(bean.getName());
        result.setSex(bean.getSex());
        result.setExpireDate(bean.getExpireDate());
        // FIXME
        // 临时给iPad会员过期使用，后期可以去掉
        Device device = deviceService.getDeviceInfo(deviceId);
        if(device != null) {
            result.setExpireDate(null);
        }
        result.setLastLoginDate(bean.getLastLoginDate());
        result.setAccess_token(authUtil.getToken(bean.getId(), deviceId));
        return result;
    }

    @Override
    public UserTO openAuth(UserTO user, String deviceId) throws Exception {
        return null;
    }

    @Override
    public UserTO bindMobile(UserTO user, String deviceId) throws CommonException, AuthException {
        return null;
    }

    @Override
    public void upgradeVIP(String userId, int month) {

    }

    @Override
    public User getUserById(String userId) {
        return null;
    }

    @Override
    public List<User> getUserByOpenId(String openId) {
        return null;
    }
}
