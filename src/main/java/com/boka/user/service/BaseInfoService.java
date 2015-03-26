package com.boka.user.service;

import com.boka.common.constant.Constant;
import com.boka.common.constant.ProductType;
import com.boka.common.dto.ResultTO;
import com.boka.common.exception.AuthException;
import com.boka.common.exception.CommonException;
import com.boka.common.exception.ExceptionCode;
import com.boka.common.exception.LoginException;
import com.boka.common.util.Assert;
import com.boka.common.util.AuthUtil;
import com.boka.common.util.RandomUtil;
import com.boka.user.constant.StatusConstant;
import com.boka.user.dto.PasswordTO;
import com.boka.user.dto.UserTO;
import com.boka.user.model.Employee;
import com.boka.user.model.ReserveInfo;
import com.boka.user.model.Shop;
import com.boka.user.model.User;
import com.boka.user.repository.BaseInfoRepository;
import com.boka.user.repository.EmployeeRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;

@Service
public class BaseInfoService {

    @Autowired
    private BaseInfoRepository baseInfoRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ShopService shopService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthUtil authUtil;

    /**
     * 用户注册
     *
     * @param user
     * @return
     * @throws CommonException
     */
    public UserTO reg(UserTO user, String deviceId) throws CommonException, LoginException {
        //验证码检验
        if (!authUtil.authMobile(user.getMobile(), user.getAuthcode(), user.getProduct())) {
            throw new CommonException(ExceptionCode.MOBILE_AUTH_FAILD);
        }
        if (Assert.isNull(user.getPassword())) {
            throw new CommonException(ExceptionCode.PARAM_NULL);
        }

        //靓丽前台用户都注册为发界用户
        if (user.getProduct().equals(ProductType.DESKTOP)) {
            return desktopReg(user, deviceId);
        }

        User bean = baseInfoRepository.findByMobile(user.getMobile(), user.getProduct());
        if (bean != null) {
            //靓丽前台注册时发现已注册发界账号,则需要特殊提示
            if (bean.getProduct().equals(ProductType.FZONE) && ProductType.DESKTOP.equals(user.getRegProduct())) {
                throw new LoginException(ExceptionCode.MOBILE_EXISTS);
            } else {
                throw new CommonException(ExceptionCode.MOBILE_EXISTS);
            }

        }
        bean = new User();
        bean.setProduct(user.getProduct());
        bean.setCreateDate(Calendar.getInstance().getTime());
        bean.setMobile(user.getMobile());
        bean.setRegProduct(user.getRegProduct());
        bean.setActivatedStatus(user.getActivatedStatus());
        bean.setSalt(RandomUtil.randomSalt());
        bean.setInviteCode(user.getInviteCode());
        //MD5加盐
        bean.setPassword(DigestUtils.md5Hex(bean.getSalt() + user.getPassword()));
        bean = baseInfoRepository.save(bean);
        user.setAccess_token(authUtil.getToken(bean.getId(), deviceId));
        user.setCreateDate(bean.getCreateDate());
        //生成token
        return user;
    }

    private UserTO desktopReg(UserTO user, String deviceId) throws CommonException, LoginException {
        user.setProduct(ProductType.FZONE);
        user.setRegProduct(ProductType.DESKTOP);

        Employee bean = employeeRepository.findByMobile(user.getMobile(), user.getProduct());
        if (bean != null) {
            //靓丽前台注册时发现已注册发界账号,则需要特殊提示
            if (bean.getProduct().equals(ProductType.FZONE) && ProductType.DESKTOP.equals(user.getRegProduct())) {
                throw new LoginException(ExceptionCode.MOBILE_EXISTS);
            } else {
                throw new CommonException(ExceptionCode.MOBILE_EXISTS);
            }

        }
        bean = new Employee();
        bean.setProduct(user.getProduct());
        bean.setCreateDate(Calendar.getInstance().getTime());
        bean.setMobile(user.getMobile());
        bean.setRegProduct(user.getRegProduct());
        bean.setActivatedStatus(user.getActivatedStatus());
        bean.setSalt(RandomUtil.randomSalt());
        bean.setInviteCode(user.getInviteCode());
        ReserveInfo reserveInfo = new ReserveInfo();
        reserveInfo.setInAdvanceMin(0);
        reserveInfo.setInAdvanceMax(10);
        reserveInfo.setInterval(30);

        reserveInfo.setStatus(0);
        Calendar start = Calendar.getInstance();
        start.set(Calendar.HOUR_OF_DAY, 10);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        reserveInfo.setStartTime(start.getTime());
        Calendar end = Calendar.getInstance();
        end.set(Calendar.HOUR_OF_DAY, 22);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        end.set(Calendar.MILLISECOND, 0);
        reserveInfo.setEndTime(end.getTime());
        reserveInfo.setInterval(30);
        reserveInfo.setInAdvanceMin(0);
        reserveInfo.setInAdvanceMax(10);
        bean.setReserveInfo(reserveInfo);
        //MD5加盐
        bean.setPassword(DigestUtils.md5Hex(bean.getSalt() + user.getPassword()));
        bean = employeeRepository.save(bean);
        user.setAccess_token(authUtil.getToken(bean.getId(), deviceId));
        user.setCreateDate(bean.getCreateDate());
        //生成token
        return user;
    }

    /**
     * 激活保存用户信息
     *
     * @param user
     * @return
     * @throws CommonException
     */
    public UserTO activate(UserTO user) throws CommonException {
        if (user.getProduct().equals(ProductType.DESKTOP)) {
            return desktopActivate(user);
        }

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
        bean.setProduct(user.getProduct());
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

    private UserTO desktopActivate(UserTO user) {
        Employee bean = employeeRepository.findOne(user.getId());
        Shop shop;
        if (user.getShop().getCreator() == null && user.getShop().getAdmin() != null) {
            //认领门店
            shop = shopService.getShop(user.getShop().getId());
            user.setAdminStatus(StatusConstant.TRUE);
            shop.setAdmin(user.getShop().getAdmin());
            //更新门店管理员信息
            if (!shopService.updateShopAdmin(shop)) {
                throw new CommonException(ExceptionCode.DATA_NOT_EXISTS);
            }

        } else if (user.getShop().getCreator() != null) {
            //注册门店
            user.setAdminStatus(StatusConstant.TRUE);
            user.getShop().setAdmin(user.getShop().getCreator());
            shop = shopService.addShop(user.getShop());

        }else {
            //加入门店
            shop = shopService.getShop(user.getShop().getId());
        }
        if (shop == null) {
            throw new CommonException(ExceptionCode.DATA_NOT_EXISTS);
        }
        bean.setShop(shop);
        bean.setName(user.getName());
        bean.setAvatar(user.getAvatar());
        bean.setSex(user.getSex());
        bean.setAdminStatus(user.getAdminStatus());
        bean.setUpdateDate(Calendar.getInstance().getTime());
        bean.setLastLoginDate(bean.getCreateDate());
        bean.setLoc(user.getLoc());
        if(Assert.isNotNull(bean.getMobile())) {
            bean.setActivatedStatus(StatusConstant.activated);
        }
        employeeRepository.save(bean);

        UserTO result = new UserTO();
        result.setId(bean.getId());
        result.setAvatar(bean.getAvatar());
        result.setActivatedStatus(bean.getActivatedStatus());
        result.setMobile(bean.getMobile());
        result.setName(bean.getName());
        result.setShop(user.getShop());
        result.setLoc(user.getLoc());
        result.setSex(bean.getSex());
        return result;
    }

    /**
     * 登录
     *
     * @param user
     * @return
     * @throws LoginException
     * @throws CommonException
     */
    public UserTO login(UserTO user, String deviceId) throws LoginException, CommonException {
        //靓丽前台登陆
        if (user.getProduct().equals(ProductType.DESKTOP)) {
            return desktopLogin(user, deviceId);
        }

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
        result.setAccess_token(authUtil.getToken(bean.getId(), deviceId));
        return result;
    }

    /**
     * 靓丽前台登录
     *
     * @param user
     * @return
     * @throws LoginException
     * @throws CommonException
     */
    private UserTO desktopLogin(UserTO user, String deviceId) throws LoginException, CommonException {
        user.setProduct(ProductType.FZONE);
        Employee bean = employeeRepository.findByMobile(user.getMobile(), user.getProduct());
        if (bean == null) {
            throw new LoginException(ExceptionCode.USER_NOT_EXISTS);
        } else if (!DigestUtils.md5Hex(bean.getSalt() + user.getPassword()).equals(bean.getPassword())) {
            throw new LoginException(ExceptionCode.PASSWORD_ERROR);
        }

        UserTO result = new UserTO();
        result.setId(bean.getId());
        result.setAvatar(bean.getAvatar());
        result.setResetStatus(bean.getResetStatus());
        result.setActivatedStatus(bean.getActivatedStatus());
        result.setMobile(bean.getMobile());
        result.setName(bean.getName());
        result.setSex(bean.getSex());
        result.setExpireDate(bean.getExpireDate());
        result.setAdminStatus(bean.getAdminStatus());
        result.setShop(bean.getShop());
        result.setAccess_token(authUtil.getToken(bean.getId(), deviceId));
        return result;
    }

    /**
     * 第三方登录
     *
     * @param user
     * @return
     * @throws Exception
     */
    public UserTO openAuth(UserTO user, String deviceId) throws Exception {
        User bean = null;
        if (Assert.isNotNull(user.getQqId())) {
            bean = baseInfoRepository.findByQqId(user.getQqId());
        } else if (Assert.isNotNull(user.getWechatId())) {
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
            bean.setAvatar(user.getAvatar());
            bean.setName(user.getName());
            bean.setSex(user.getSex());
        } else {
            bean.setUpdateDate(Calendar.getInstance().getTime());
        }
        bean.setLoc(user.getLoc());
        bean.setLastLoginDate(Calendar.getInstance().getTime());
        bean = baseInfoRepository.save(bean);
        // 同步Show用户信息
        if (!bean.getAvatar().equals(user.getAvatar()) || bean.getSex() != user.getSex() || !bean.getName().equals(user.getName())) {
            user.setId(bean.getId());
            syncUser(user);
        }
        // 将新的用户ID绑定到access_token上
        authUtil.saveOpenAuthToken(user.getAccess_token(), bean.getId(), deviceId);
        UserTO result = new UserTO();
        result.setId(bean.getId());
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
     *
     * @param user
     * @return
     * @throws CommonException
     */
    public UserTO bindMobile(UserTO user, String deviceId) throws CommonException, AuthException {
        //验证码检验
        if (!authUtil.authMobile(user.getMobile(), user.getAuthcode(), ProductType.BEAUTY)) {
            throw new CommonException(ExceptionCode.MOBILE_AUTH_FAILD);
        }

        User bean = baseInfoRepository.findByMobile(user.getMobile(), user.getProduct());
        if (bean != null) {
            User openAuthUser = baseInfoRepository.findOne(user.getId());
            if (Assert.isNotNull(openAuthUser.getQqId())) {
                if(Assert.isNotNull(bean.getQqId())) {
                    throw new CommonException("手机号已被绑定");
                }
                bean.setQqId(openAuthUser.getQqId());
            }
            if (Assert.isNotNull(openAuthUser.getWechatId())) {
                if(Assert.isNotNull(bean.getWechatId())) {
                    throw new CommonException("手机号已被绑定");
                }
                bean.setWechatId(openAuthUser.getWechatId());
            }
            openAuthUser.setActivatedStatus(StatusConstant.removed);
            //将第三方登录帐号注销
            baseInfoRepository.updateBindUser(openAuthUser);
        } else {
            if (Assert.isNull(user.getPassword())) {
                throw new CommonException(ExceptionCode.PARAM_NULL);
            }
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
        result.setWechatId(bean.getWechatId());
        result.setAvatar(bean.getAvatar());
        result.setActivatedStatus(bean.getActivatedStatus());
        result.setMobile(bean.getMobile());
        result.setName(bean.getName());
        result.setSex(bean.getSex());
        // 将access_token绑定到新用户ID
        authUtil.saveOpenAuthToken(user.getAccess_token(), bean.getId(), deviceId);
        return result;
    }

    /**
     * 同步秀里用户信息
     *
     * @param user
     * @return
     */
    public ResultTO syncUser(UserTO user) {
        return restTemplate.postForObject(Constant.SHOW_USER_SYNC_URL, user, ResultTO.class);
    }

    /**
     * 修改密码
     *
     * @param userId
     * @param password
     * @throws CommonException
     * @throws AuthException
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
            bean.setUpdateDate(Calendar.getInstance().getTime());
            //MD5加盐
            bean.setPassword(DigestUtils.md5Hex(bean.getSalt() + user.getPassword()));
            baseInfoRepository.save(bean);
        }
    }

    /**
     * 修改用户信息
     *
     * @param user
     * @throws CommonException
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
     * @param userId
     * @param month
     */
    public void upgradeVIP(String userId, int month) {
        User bean = baseInfoRepository.findOne(userId);
        if (bean == null) {
            throw new CommonException(ExceptionCode.DATA_NOT_EXISTS);
        }
        Calendar calendar = Calendar.getInstance();
        if(bean.getExpireDate() != null) {
            calendar.setTime(bean.getExpireDate());
        }
        calendar.add(Calendar.MONTH, month);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        bean.setExpireDate(calendar.getTime());
        baseInfoRepository.save(bean);
    }

    public User getUserById(String userId) {
        return baseInfoRepository.findOne(userId);
    }


}
