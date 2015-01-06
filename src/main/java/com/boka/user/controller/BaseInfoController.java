package com.boka.user.controller;

import com.alibaba.fastjson.JSON;
import com.boka.common.constant.ProductType;
import com.boka.common.exception.AuthException;
import com.boka.common.exception.CommonException;
import com.boka.common.exception.LoginException;
import com.boka.common.util.Assert;
import com.boka.common.util.AuthUtil;
import com.boka.common.util.LogUtil;
import com.boka.user.dto.ResultTO;
import com.boka.user.dto.UserTO;
import com.boka.user.service.BaseInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class BaseInfoController {

    private static Logger logger = Logger.getLogger(BaseInfoController.class);

    @Autowired
    private BaseInfoService baseInfoService;

    @Autowired
    private AuthUtil authUtil;

    @RequestMapping(value = "/beauty/reg", method = RequestMethod.POST)
    public ResultTO addUser(HttpServletRequest request, @RequestBody UserTO user) {
        ResultTO result = new ResultTO();
        String deviceId = null;
        try {
            Map<String, String> map = authUtil.preAuth(request);
            deviceId = map.get("deviceId");
            user.setProduct(ProductType.BEAUTY);
            result.setResult(baseInfoService.reg(user));
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

    @RequestMapping(value = "/beauty/activate", method = RequestMethod.POST)
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

    @RequestMapping(value = "/beauty/login", method = RequestMethod.POST)
    public ResultTO loginUser(HttpServletRequest request, @RequestBody UserTO user) {
        ResultTO result = new ResultTO();
        String deviceId = null;
        try {
            Map<String, String> map = authUtil.preAuth(request);
            deviceId = map.get("deviceId");
            user.setProduct(ProductType.BEAUTY);
            result.setResult(baseInfoService.login(user));
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

    @RequestMapping(value = "/beauty/logout", method = RequestMethod.GET)
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

    @RequestMapping(value = "/beauty/openauth", method = RequestMethod.POST)
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
            result.setResult(baseInfoService.openAuth(user));
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action("第三方用户登陆,{},{},{}", (Assert.isNull(user.getQqId()) ?  user.getWechatId() : user.getQqId()), deviceId, ProductType.BEAUTY);
        return result;
    }

    @RequestMapping(value = "/beauty/bindmobile", method = RequestMethod.POST)
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
            result.setResult(baseInfoService.bindMobile(user));
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action("绑定手机号,{},{},{}", user.getQqId(), deviceId, ProductType.BEAUTY);
        return result;
    }
}
