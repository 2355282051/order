package com.boka.user.controller;

import com.boka.common.constant.ServiceType;
import com.boka.common.dto.ResultTO;
import com.boka.common.exception.AuthException;
import com.boka.common.exception.CommonException;
import com.boka.common.exception.LoginException;
import com.boka.common.util.AuthUtil;
import com.boka.common.util.LogUtil;
import com.boka.user.constant.StatusConstant;
import com.boka.user.dto.UserTO;
import com.boka.user.service.BaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequestMapping(value = "/open")
@RestController
public class OpenAuthController {

    @Autowired
    private BaseInfoService baseInfoService;

    @Autowired
    private AuthUtil authUtil;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultTO loginUser(HttpServletRequest request,
                              @RequestParam String mobile,
                              @RequestParam String password,
                              @RequestParam String product) {
        ResultTO result = new ResultTO();
        String deviceId = null;
        try {
            Map<String, String> map = authUtil.preAuth(request);
            deviceId = map.get("deviceId");
            UserTO user = new UserTO();
            user.setMobile(mobile);
            user.setPassword(password);
            user.setProduct(product);
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
        LogUtil.action(ServiceType.USER, "第三方系统用户登陆,{},{},{}", mobile, deviceId, product);
        return result;
    }


    /**
     *
     * @param request
     * @param mobile
     * @param password
     * @param authCode
     * @param product
     * @param inviteCode 邀请码
     * @return
     */
    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public ResultTO addUser(HttpServletRequest request,
                            @RequestParam String mobile,
                            @RequestParam  String password,
                            @RequestParam  String authCode,
                            @RequestParam  String product,
                            @RequestParam(required = false) String inviteCode) {
        ResultTO result = new ResultTO();
        String deviceId = null;
        try {
            Map<String, String> map = authUtil.preAuth(request);
            deviceId = map.get("deviceId");
            UserTO user = new UserTO();
            user.setMobile(mobile);
            user.setPassword(password);
            user.setAuthcode(authCode);
            user.setProduct(product);
            user.setActivatedStatus(StatusConstant.activated);
            user.setInviteCode(inviteCode);
            result.setResult(baseInfoService.reg(user, deviceId));
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
        LogUtil.action(ServiceType.USER, "第三方系统用户注册,{},{},{}", mobile, deviceId, product);
        return result;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.DELETE)
    public ResultTO logoutUser(HttpServletRequest request) {
        ResultTO result = new ResultTO();
        String deviceId = null;
        String userId = null;
        try {
            Map<String, String> map = authUtil.removeAuth(request);
            userId = map.get("userId");
            deviceId = map.get("deviceId");
        } catch (AuthException le) {
            result.setCode(403);
            result.setSuccess(false);
            result.setMsg(le.getMessage());
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action(ServiceType.USER, "用户登出,{},{},{}", userId, deviceId);
        return result;
    }

}
