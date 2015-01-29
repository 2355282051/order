package com.boka.user.controller;

import com.alibaba.fastjson.JSON;
import com.boka.common.constant.ProductType;
import com.boka.common.exception.AuthException;
import com.boka.common.exception.CommonException;
import com.boka.common.exception.ExceptionCode;
import com.boka.common.exception.LoginException;
import com.boka.common.util.Assert;
import com.boka.common.util.AuthUtil;
import com.boka.common.util.LogUtil;
import com.boka.user.dto.PasswordTO;
import com.boka.common.dto.ResultTO;
import com.boka.user.dto.UserTO;
import com.boka.user.service.BaseInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RequestMapping(value = "/beauty")
@RestController
public class BaseInfoController {

    private static Logger logger = Logger.getLogger(BaseInfoController.class);

    @Autowired
    private BaseInfoService baseInfoService;

    @Autowired
    private AuthUtil authUtil;

    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public ResultTO addUser(HttpServletRequest request, @RequestBody UserTO user) {
        ResultTO result = new ResultTO();
        String deviceId = null;
        try {
            Map<String, String> map = authUtil.preAuth(request);
            deviceId = map.get("deviceId");
            user.setProduct(ProductType.BEAUTY);
            result.setResult(baseInfoService.reg(user, deviceId));
        } catch (CommonException ce) {
            result.setCode(500);
            result.setSuccess(false);
            result.setMsg(ce.getMessage());
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action("用户注册,{},{},{}", user.getId(), deviceId, ProductType.BEAUTY);
        return result;
    }

    @RequestMapping(value = "/activate", method = RequestMethod.POST)
    public ResultTO activateUser(HttpServletRequest request, @RequestBody UserTO user) {
        ResultTO result = new ResultTO();
        String userId = null;
        String deviceId = null;
        try {
            Map<String, String> map = authUtil.auth(request);
            userId = map.get("userId");
            deviceId = map.get("deviceId");
            user.setProduct(ProductType.BEAUTY);
            user.setId(userId);
            result.setResult(baseInfoService.activate(user));
        } catch (AuthException ae) {
            result.setCode(403);
            result.setSuccess(false);
            result.setMsg(ae.getMessage());
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action("用户激活,{},{},{}", userId, deviceId, ProductType.BEAUTY);
        return result;
    }

    @RequestMapping(value = "/changepwd", method = RequestMethod.POST)
    public ResultTO ChangePassword(HttpServletRequest request, @RequestBody PasswordTO password) {
        ResultTO result = new ResultTO();
        String userId = null;
        String deviceId = null;
        String access_token = null;
        try {
            Map<String, String> map = authUtil.auth(request);
            userId = map.get("userId");
            deviceId = map.get("deviceId");
            baseInfoService.changePassword(userId, password);
            Map<String, String> newAuth = authUtil.changeAuth(request);
            access_token = newAuth.get("access_token");
            result.setResult(access_token);
        } catch (AuthException ae) {
            result.setCode(403);
            result.setSuccess(false);
            result.setMsg(ae.getMessage());
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action("用户激活,{},{},{}", userId, deviceId, ProductType.BEAUTY);
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultTO editUser(HttpServletRequest request, @RequestBody UserTO user) {
        ResultTO result = new ResultTO();
        String userId = null;
        String deviceId = null;
        try {
            Map<String, String> map = authUtil.auth(request);
            userId = map.get("userId");
            deviceId = map.get("deviceId");
            user.setProduct(ProductType.BEAUTY);
            user.setId(userId);
            baseInfoService.edit(user);
        } catch (AuthException ae) {
            result.setCode(403);
            result.setSuccess(false);
            result.setMsg(ae.getMessage());
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action("用户激活,{},{},{}", userId, deviceId, ProductType.BEAUTY);
        return result;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultTO loginUser(HttpServletRequest request, @RequestBody UserTO user) {
        ResultTO result = new ResultTO();
        String deviceId = null;
        try {
            Map<String, String> map = authUtil.preAuth(request);
            deviceId = map.get("deviceId");
            user.setProduct(ProductType.BEAUTY);
            result.setResult(baseInfoService.login(user, deviceId));
        } catch (LoginException le) {
            result.setCode(401);
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
        LogUtil.action("用户登陆,{},{},{}", user.getId(), deviceId, ProductType.BEAUTY);
        return result;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResultTO logoutUser(HttpServletRequest request) {
        ResultTO result = new ResultTO();
        String deviceId = null;
        String userId = null;
        try {
            Map<String, String> map = authUtil.removeAuth(request);
            userId = map.get("userId");
            deviceId = map.get("deviceId");
            result.setResult(new Object());
        } catch (AuthException le) {
            result.setCode(403);
            result.setSuccess(false);
            result.setMsg(le.getMessage());
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action("用户登出,{},{},{}", userId, deviceId, ProductType.BEAUTY);
        return result;
    }

    @RequestMapping(value = "/openauth", method = RequestMethod.POST)
    public ResultTO openAuth(HttpServletRequest request, @RequestBody UserTO user) {
        logger.debug(JSON.toJSON(user));
        ResultTO result = new ResultTO();
        String deviceId = null;
        String userId = null;
        try {
            Map<String, String> map = authUtil.openAuth(request);
            deviceId = map.get("deviceId");
            userId = map.get("userId");
            user.setProduct(ProductType.BEAUTY);
            user.setId(userId);
            user.setAccess_token(request.getHeader("access_token"));
            result.setResult(baseInfoService.openAuth(user, deviceId));
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
        LogUtil.action("第三方用户登陆,{},{},{}", (Assert.isNull(user.getQqId()) ?  user.getWechatId() : user.getQqId()), deviceId, ProductType.BEAUTY);
        return result;
    }

    @RequestMapping(value = "/bindmobile", method = RequestMethod.POST)
    public ResultTO bindMobile(HttpServletRequest request, @RequestBody UserTO user) {
        logger.debug(JSON.toJSON(user));
        ResultTO result = new ResultTO();
        String userId = null;
        String deviceId = null;
        try {
            Map<String, String> map = authUtil.auth(request);
            userId = map.get("userId");
            deviceId = map.get("deviceId");
            user.setProduct(ProductType.BEAUTY);
            user.setId(userId);
            user.setAccess_token(request.getHeader("access_token"));
            result.setResult(baseInfoService.bindMobile(user, deviceId));
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
        LogUtil.action("绑定手机号,{},{},{}", user.getQqId(), deviceId, ProductType.BEAUTY);
        return result;
    }

    @RequestMapping(value = "/checkcode", method = RequestMethod.GET)
    public ResultTO checkCode(HttpServletRequest request, String mobile, String authcode) {
        ResultTO result = new ResultTO();
        String userId = null;
        String deviceId = null;
        try {
            Map<String, String> map = authUtil.preAuth(request);
            userId = map.get("userId");
            deviceId = map.get("deviceId");
            UserTO user = new UserTO();
            HttpSession session = request.getSession();
            session.setAttribute("mobile", mobile);
            session.setAttribute("authcode", authcode);
            user.setMobile(mobile);
            user.setAuthcode(authcode);
            baseInfoService.forgetPassword(user);
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
        LogUtil.action("忘记密码,检测验证码,{},{},{},{}", mobile, authcode, deviceId, ProductType.BEAUTY);
        return result;
    }

    @RequestMapping(value = "/forgetpwd", method = RequestMethod.POST)
    public ResultTO forgetPassword(HttpServletRequest request, @RequestBody PasswordTO password) {
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
            baseInfoService.forgetPassword(user);
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
        LogUtil.action("忘记密码,修改密码,{},{},{},{}", password.getMobile(), password.getAuthcode(), deviceId, ProductType.BEAUTY);
        return result;
    }

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public ResultTO auth(HttpServletRequest request) {
        ResultTO result = new ResultTO();
        String deviceId = null;
        String userId = null;
        try {
            Map<String, String> map = authUtil.auth(request);
            deviceId = map.get("deviceId");
            userId = map.get("userId");
            result.setResult(true);
        } catch (AuthException le) {
            result.setCode(403);
            result.setSuccess(false);
            result.setMsg(le.getMessage());
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action("校验用户登陆,{},{},{}", deviceId, deviceId, ProductType.BEAUTY);
        return result;
    }




}
