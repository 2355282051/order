package com.boka.user.controller;

import com.boka.common.constant.ProductType;
import com.boka.common.constant.ServiceType;
import com.boka.common.dto.ResultTO;
import com.boka.common.exception.AuthException;
import com.boka.common.exception.CommonException;
import com.boka.common.exception.ExceptionCode;
import com.boka.common.exception.LoginException;
import com.boka.common.util.Assert;
import com.boka.common.util.AuthUtil;
import com.boka.common.util.LogUtil;
import com.boka.user.dto.PasswordTO;
import com.boka.user.dto.UserTO;
import com.boka.user.factory.UserServiceFactory;
import com.boka.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 用户相关基本操作控制器
 **/
@RestController
public class UserController {

    @Autowired
    private AuthUtil authUtil;

    @RequestMapping(value = "/beauty/reg", method = RequestMethod.POST)
    public ResultTO addUser(HttpServletRequest request, @RequestBody UserTO user) {
        ResultTO result = new ResultTO();
        String deviceId = null;
        try {
            Map<String, String> map = authUtil.preAuth(request);
            deviceId = map.get("deviceId");
            //FIXME 暂时定位beauty
            user.setProduct(ProductType.BEAUTY);
            result.setResult(UserServiceFactory.getService(request, ProductType.BEAUTY).reg(user, deviceId));
        } catch (CommonException ce) {
            result.setCode(500);
            result.setSuccess(false);
            result.setMsg(ce.getMessage());
        } catch (LoginException le) {
            result.setCode(409);
            result.setSuccess(false);
            result.setMsg(le.getMessage());
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action(ServiceType.USER, "用户注册,{},{},{}", user.getId(), deviceId, ProductType.BEAUTY);
        return result;
    }

    @RequestMapping(value = "/desktop/reg", method = RequestMethod.POST)
    public ResultTO addDesktopUser(HttpServletRequest request, @RequestBody UserTO user) {
        ResultTO result = new ResultTO();
        String deviceId = null;
        try {
            Map<String, String> map = authUtil.preAuth(request);
            deviceId = map.get("deviceId");
            //FIXME 暂时定位beauty
            user.setProduct(ProductType.DESKTOP);
            result.setResult(UserServiceFactory.getService(request, ProductType.DESKTOP).reg(user, deviceId));
        } catch (CommonException ce) {
            result.setCode(500);
            result.setSuccess(false);
            result.setMsg(ce.getMessage());
        } catch (LoginException le) {
            result.setCode(409);
            result.setSuccess(false);
            result.setMsg(le.getMessage());
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action(ServiceType.USER, "用户注册,{},{},{}", user.getId(), deviceId, ProductType.BEAUTY);
        return result;
    }

    @RequestMapping(value = "/{product}/activate", method = RequestMethod.POST)
    public ResultTO activateUser(HttpServletRequest request, @PathVariable String product, @RequestBody UserTO user) {
        ResultTO result = new ResultTO();
        String userId = null;
        String deviceId = null;
        try {
            Map<String, String> map = authUtil.auth(request);
            userId = map.get("userId");
            deviceId = map.get("deviceId");
            user.setProduct(product);
            user.setId(userId);
            result.setResult(UserServiceFactory.getService(request,product).activate(user));
        } catch (AuthException ae) {
            result.setCode(403);
            result.setSuccess(false);
            result.setMsg(ae.getMessage());
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action(ServiceType.USER, "用户激活,{},{},{}", userId, deviceId, product);
        return result;
    }

    @RequestMapping(value = "/{product}/changepwd", method = RequestMethod.POST)
    public ResultTO ChangePassword(HttpServletRequest request, @PathVariable String product, @RequestBody PasswordTO password) {
        ResultTO result = new ResultTO();
        String userId = null;
        String deviceId = null;
        String access_token = null;
        try {
            Map<String, String> map = authUtil.auth(request);
            userId = map.get("userId");
            deviceId = map.get("deviceId");
            UserServiceFactory.getService(request,product).changePassword(userId, password);
            Map<String, String> newAuth = authUtil.changeAuth(request);
            access_token = newAuth.get("access_token");
            result.setResult(access_token);
        } catch (AuthException ae) {
            result.setCode(403);
            result.setSuccess(false);
            result.setMsg(ae.getMessage());
        } catch (CommonException ce) {
            result.setCode(400);
            result.setSuccess(false);
            result.setMsg(ce.getMessage());
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action(ServiceType.USER, "修改密码,{},{},{}", userId, deviceId, product);
        return result;
    }

    @RequestMapping(value = "/{product}/edit", method = RequestMethod.POST)
    public ResultTO editUser(HttpServletRequest request, @PathVariable String product, @RequestBody UserTO user) {
        ResultTO result = new ResultTO();
        String userId = null;
        String deviceId = null;

        String access_token = request.getHeader("access_token");
        deviceId = request.getHeader("device_id");
        try {
            Map<String, String> map = authUtil.auth(request);
            userId = map.get("userId");
            deviceId = map.get("deviceId");
            user.setProduct(product);
            user.setId(userId);
            result.setResult(UserServiceFactory.getService(request,product).edit(user));
        } catch (AuthException ae) {
            result.setCode(403);
            result.setSuccess(false);
            result.setMsg(ae.getMessage());
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action(ServiceType.USER, "用户编辑,{},{},{}", userId, deviceId, product);
        return result;
    }

    @RequestMapping(value = "/beauty/login", method = RequestMethod.POST)
    public ResultTO loginUser(HttpServletRequest request, @RequestBody UserTO user) {
        ResultTO result = new ResultTO();
        String deviceId = null;
        try {
            Map<String, String> map = authUtil.preAuth(request);
            deviceId = map.get("deviceId");
            // FIXME
            user.setProduct(ProductType.BEAUTY);
            result.setResult(UserServiceFactory.getService(request, ProductType.BEAUTY).login(user, deviceId));
        } catch (LoginException ae) {
            result.setCode(401);
            result.setSuccess(false);
            result.setMsg(ae.getMessage());
        } catch (CommonException ce) {
            result.setCode(400);
            result.setSuccess(false);
            result.setMsg(ce.getMessage());
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action(ServiceType.USER, "用户登陆,{},{},{}", user.getId(), deviceId, ProductType.BEAUTY);
        return result;
    }

    @RequestMapping(value = "/desktop/login", method = RequestMethod.POST)
    public ResultTO loginDesktopUser(HttpServletRequest request, @RequestBody UserTO user) {
        ResultTO result = new ResultTO();
        String deviceId = null;
        try {
            Map<String, String> map = authUtil.preAuth(request);
            deviceId = map.get("deviceId");
            // FIXME
            user.setProduct(ProductType.DESKTOP);
            result.setResult(UserServiceFactory.getService(request, ProductType.DESKTOP).login(user, deviceId));
        } catch (LoginException ae) {
            result.setCode(401);
            result.setSuccess(false);
            result.setMsg(ae.getMessage());
        } catch (CommonException ce) {
            result.setCode(400);
            result.setSuccess(false);
            result.setMsg(ce.getMessage());
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action(ServiceType.USER, "用户登陆,{},{},{}", user.getId(), deviceId, ProductType.BEAUTY);
        return result;
    }

    @RequestMapping(value = "/{product}/u/{userId}/checked", method = RequestMethod.GET)
    public ResultTO loginUser(HttpServletRequest request, @PathVariable String product, @PathVariable String userId) {
        ResultTO result = new ResultTO();
        try {
            List<User> users = UserServiceFactory.getService(request,product).getUserByOpenId(userId);
            if(users != null && users.size() > 0) {
                result.setResult(true);
            } else {
                result.setResult(false);
            }
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action(ServiceType.USER, "检测用户ID是否存在,{},{},{}", userId, product);
        return result;
    }

    @RequestMapping(value = "/{product}/logout", method = RequestMethod.GET)
    public ResultTO logoutUser(HttpServletRequest request, @PathVariable String product) {
        ResultTO result = new ResultTO();
        String deviceId = null;
        String userId = null;
        try {
            Map<String, String> map = authUtil.removeAuth(request);
            userId = map.get("userId");
            deviceId = map.get("deviceId");
        } catch (AuthException ae) {
            result.setCode(403);
            result.setSuccess(false);
            result.setMsg(ae.getMessage());
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action(ServiceType.USER, "用户登出,{},{},{}", userId, deviceId, product);
        return result;
    }

    @RequestMapping(value = "/{product}/openauth", method = RequestMethod.POST)
    public ResultTO openAuth(HttpServletRequest request, @PathVariable String product, @RequestBody UserTO user) {
        ResultTO result = new ResultTO();
        String deviceId = null;
        String userId = null;
        try {
            Map<String, String> map = authUtil.openAuth(request);
            deviceId = map.get("deviceId");
            userId = map.get("userId");
            user.setProduct(product);
            user.setId(userId);
            user.setAccess_token(request.getHeader("access_token"));
            result.setResult(UserServiceFactory.getService(request,product).openAuth(user, deviceId));
        } catch (AuthException ae) {
            result.setCode(403);
            result.setSuccess(false);
            result.setMsg(ae.getMessage());
        } catch (CommonException ce) {
            result.setCode(400);
            result.setSuccess(false);
            result.setMsg(ce.getMessage());
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action(ServiceType.USER, "第三方用户登陆,{},{},{}", (Assert.isNull(user.getQqId()) ?  user.getWechatId() : user.getQqId()), deviceId, product);
        return result;
    }

    @RequestMapping(value = "/{product}/bindmobile", method = RequestMethod.POST)
    public ResultTO bindMobile(HttpServletRequest request, @PathVariable String product, @RequestBody UserTO user) {
        ResultTO result = new ResultTO();
        String userId = null;
        String deviceId = null;
        try {
            Map<String, String> map = authUtil.auth(request);
            userId = map.get("userId");
            deviceId = map.get("deviceId");
            user.setProduct(product);
            user.setId(userId);
            user.setAccess_token(request.getHeader("access_token"));
            result.setResult(UserServiceFactory.getService(request,product).bindMobile(user, deviceId));
        } catch (AuthException le) {
            result.setCode(403);
            result.setSuccess(false);
            result.setMsg(le.getMessage());
        } catch (CommonException ce) {
            result.setCode(400);
            result.setSuccess(false);
            result.setMsg(ce.getMessage());
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action(ServiceType.USER, "绑定手机号,{},{},{}", user.getQqId(), deviceId, product);
        return result;
    }

    @RequestMapping(value = "/{product}/checkcode", method = RequestMethod.GET)
    public ResultTO checkCode(HttpServletRequest request, String mobile, String authcode, @PathVariable String product) {
        ResultTO result = new ResultTO();
        String deviceId = null;
        try {

            Map<String, String> map = authUtil.preAuth(request);
            //userId = map.get("userId");
            deviceId = map.get("deviceId");
            //验证码检验
            if (!authUtil.authMobile(mobile, authcode, product)) {
                throw new CommonException(ExceptionCode.MOBILE_AUTH_FAILD);
            }
        } catch (AuthException ae) {
            result.setCode(403);
            result.setSuccess(false);
            result.setMsg(ae.getMessage());
        } catch (CommonException ce) {
            result.setCode(400);
            result.setSuccess(false);
            result.setMsg(ce.getMessage());
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action(ServiceType.USER, "忘记密码,检测验证码,{},{},{},{}", mobile, authcode, deviceId, product);
        return result;
    }

    @RequestMapping(value = "/{product}/forgetpwd", method = RequestMethod.POST)
    public ResultTO forgetPassword(HttpServletRequest request, @PathVariable String product, @RequestBody PasswordTO password) {
        ResultTO result = new ResultTO();
        String userId = null;
        String deviceId = null;
        try {
            Map<String, String> map = authUtil.preAuth(request);
            userId = map.get("userId");
            deviceId = map.get("deviceId");
            if(Assert.isNull(password.getMobile())) {
                throw new CommonException(ExceptionCode.MOBILE_AUTH_FAILD);
            }
            UserTO user = new UserTO();
            user.setMobile(password.getMobile());
            user.setAuthcode(password.getAuthcode());
            user.setPassword(password.getNewPassword());
            user.setProduct(product);
            UserServiceFactory.getService(request,product).forgetPassword(user);
        } catch (AuthException ae) {
            result.setCode(403);
            result.setSuccess(false);
            result.setMsg(ae.getMessage());
        } catch (CommonException ce) {
            result.setCode(400);
            result.setSuccess(false);
            result.setMsg(ce.getMessage());
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action(ServiceType.USER, "忘记密码,修改密码,{},{},{},{}", password.getMobile(), password.getAuthcode(), deviceId, product);
        return result;
    }

    @RequestMapping(value = "/{product}/auth", method = RequestMethod.GET)
    public ResultTO auth(HttpServletRequest request, @PathVariable String product) {
        ResultTO result = new ResultTO();
        String deviceId = null;
        String userId = null;
        try {
            Map<String, String> map = authUtil.auth(request);
            deviceId = map.get("deviceId");
            userId = map.get("userId");
        } catch (AuthException ae) {
            result.setCode(403);
            result.setSuccess(false);
            result.setMsg(ae.getMessage());
        }
        LogUtil.action(ServiceType.USER, "校验用户登录,{},{},{}", userId, deviceId, product);
        return result;
    }

}
