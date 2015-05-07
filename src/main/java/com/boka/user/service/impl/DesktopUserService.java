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
import com.boka.user.model.Employee;
import com.boka.user.model.ReserveInfo;
import com.boka.user.model.Shop;
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
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 2015/5/5 0005.
 */
@Service("desktopUserService")
public class DesktopUserService extends UserService {

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
        user.setProduct(ProductType.FZONE);
        user.setRegProduct(ProductType.DESKTOP);

        Employee bean = employeeRepository.findByMobile(user.getMobile(), user.getProduct());
        if (bean != null) {
            //靓丽前台注册时发现已注册发界账号,则需要特殊提示
            if (bean.getProduct().equals(ProductType.FZONE) && bean.getRegProduct() == null) {
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
        bean.setPassword(user.getPassword());
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
        bean.setReserveInfo(reserveInfo);

        //同步老系统
        String id = desktopService.regUser(bean);
        if (id == null) {
            throw new CommonException(ExceptionCode.DATA_NOT_EXISTS);
        }else if ("0".equals(id)) {
            throw new CommonException(ExceptionCode.MOBILE_EXISTS);
        }
        bean.setId(id);
        //MD5加盐
        bean.setPassword(DigestUtils.md5Hex(bean.getSalt() + user.getPassword()));
        bean = employeeRepository.save(bean);

        user.setAccess_token(authUtil.getToken(bean.getId(), deviceId));
        user.setCreateDate(bean.getCreateDate());
        //生成token
        return user;
    }

    @Override
    public UserTO activate(UserTO user) throws CommonException {
        Employee bean = employeeRepository.findOne(user.getId());
        if (user.getShop().getCreator() != null) {
            //注册
            regShop(bean,user);
        } else if (user.getShop().getAdmin() != null) {
            //认领
            bindShop(bean,user);
        }else {
            //加入
            joinShop(bean,user);
        }
        bean.setName(user.getName());
        bean.setAvatar(user.getAvatar());
        bean.setSex(user.getSex());
        bean.setAdminStatus(user.getAdminStatus());
        bean.setUpdateDate(Calendar.getInstance().getTime());
        bean.setLastLoginDate(bean.getCreateDate());
        bean.setLoc(user.getLoc());
        //预约设置
        bean.setReserveInfo(initReserveInfo());
        bean.setActivatedStatus(StatusConstant.activated);
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

    /*
    * 认领门店
    * */
    private void bindShop(Employee bean, UserTO user) {
        Shop shop = shopService.getShop(user.getShop().getId());
        if (shop == null) {
            throw new CommonException(ExceptionCode.DATA_NOT_EXISTS);
        }
        if (Assert.isNotNull(shop.getAdmin())) {
            throw new CommonException(ExceptionCode.DATA_NOT_EXISTS);
        }
        if (bean.getShop() == null || Assert.isNull(bean.getShop().getId())) {
            throw new CommonException(ExceptionCode.DATA_NOT_EXISTS);
        }
        user.setAdminStatus(StatusConstant.TRUE);
        bean.setAcceptStatus(StatusConstant.TRUE);
        shop.setAdmin(user.getShop().getAdmin());
        bean.setShop(shop);
        bean.setName(user.getName());
        bean.setRealName(user.getRealName());
        bean.setSex(user.getSex());
        //同步老系统
        String empSerial = desktopService.bindShop(bean);
        bean.setEmpSerial(empSerial);
        //更新门店管理员信息
        if (!shopService.updateShopAdmin(shop)) {
            throw new CommonException(ExceptionCode.DATA_NOT_EXISTS);
        }
    }

    /*
    * 注册门店
    * */
    private void regShop(Employee bean, UserTO user) {
        user.setAdminStatus(StatusConstant.TRUE);
        user.getShop().setAdmin(user.getShop().getCreator());
        bean.setAcceptStatus(StatusConstant.TRUE);
        Shop shop = shopService.addShop(user.getShop());
        bean.setShop(shop);
        bean.setName(user.getName());
        bean.setRealName(user.getRealName());
        bean.setSex(user.getSex());
        //同步老系统
        String empSerial = desktopService.regShop(bean);
        bean.setEmpSerial(empSerial);
    }

    /*
    * 加入门店
    * */
    private void joinShop(Employee bean, UserTO user) {
        Shop shop = shopService.getShop(user.getShop().getId());
        bean.setShop(shop);
        bean.setName(user.getName());
        bean.setRealName(user.getRealName());
        bean.setSex(user.getSex());
        bean.setApplyDate(new Date());
        //同步老系统
        if (!desktopService.joinShop(bean)) {
            throw new CommonException(ExceptionCode.DATA_NOT_EXISTS);
        }
    }

    @Override
    public UserTO login(UserTO user, String deviceId) throws LoginException, CommonException {
        //设置为博卡账户
        user.setProduct(ProductType.FZONE);
        Employee bean = employeeRepository.findByMobile(user.getMobile(), user.getProduct());
        if (bean == null) {
            throw new LoginException(ExceptionCode.USER_NOT_EXISTS);
        } else if (!DigestUtils.md5Hex(bean.getSalt() + user.getPassword()).equals(bean.getPassword())) {
            throw new LoginException(ExceptionCode.PASSWORD_ERROR);
        }
        //记录登陆时间
        bean.setLastLoginDate(new Date());
        employeeRepository.save(bean);

        UserTO result = new UserTO();
        result.setId(bean.getId());
        result.setAvatar(bean.getAvatar());
        result.setResetStatus(bean.getResetStatus());
        result.setActivatedStatus(bean.getActivatedStatus());
        result.setMobile(bean.getMobile());
        result.setName(bean.getName());
        result.setRealName(bean.getRealName());
        result.setSex(bean.getSex());
        result.setEmpSerial(bean.getEmpSerial());
        result.setEmpId(bean.getEmpId());
        result.setExpireDate(bean.getExpireDate());
        result.setAdminStatus(bean.getAdminStatus());
        result.setResetStatus(bean.getResetStatus());
        result.setShop(bean.getShop());
        result.setAccess_token(authUtil.getToken(bean.getId(), deviceId));
        result.setLastLoginDate(bean.getLastLoginDate());
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
    public void forgetPassword(UserTO user) throws CommonException {
        //验证码检验
        if (!authUtil.authMobile(user.getMobile(), user.getAuthcode(), user.getProduct())) {
            throw new CommonException(ExceptionCode.MOBILE_AUTH_FAILD);
        }

        //设置为博卡账户
        user.setProduct(ProductType.FZONE);
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

    @Override
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

    @Override
    public User getUserById(String userId) {
        return null;
    }

    @Override
    public List<User> getUserByOpenId(String openId) {
        return null;
    }
}
