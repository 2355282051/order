package com.boka.user.controller;

import com.boka.common.constant.ServiceType;
import com.boka.common.dto.ResultTO;
import com.boka.common.exception.AuthException;
import com.boka.common.exception.CommonException;
import com.boka.common.exception.LoginException;
import com.boka.common.util.Assert;
import com.boka.common.util.AuthUtil;
import com.boka.common.util.LogUtil;
import com.boka.user.constant.StatusConstant;
import com.boka.user.dto.OrderTO;
import com.boka.user.dto.UserTO;
import com.boka.user.model.User;
import com.boka.user.model.VipPack;
import com.boka.user.service.OrderService;
import com.boka.user.service.VipPackService;
import com.boka.user.service.impl.BeautyUserService;
import com.boka.user.service.impl.VolumeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequestMapping(value = "/open")
@RestController
public class OpenAuthController {

    @Autowired
    private VolumeUserService baseInfoService;
    @Resource
    private OrderService orderService;
    @Resource
    private VipPackService vipPackService;

    @Autowired
    private AuthUtil authUtil;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultTO getUserInfo(HttpServletRequest request) {
        ResultTO result = new ResultTO();
        String deviceId = null;
        String userId = null;
        try {
            Map<String, String> map = authUtil.auth(request);
            deviceId = map.get("deviceId");
            userId = map.get("userId");
            result.setResult(baseInfoService.getUserById(userId));
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
        LogUtil.action(ServiceType.USER, "第三方系统获取用户信息,{},{},{}", userId, deviceId);
        return result;
    }

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


    @RequestMapping(value = "/order/v/{vipPackId}", method = RequestMethod.POST)
    public ResultTO buyVIP(HttpServletRequest request, @PathVariable String vipPackId, @RequestParam String product) {
        ResultTO result = new ResultTO();
        String deviceId = null;
        String userId = null;
        try {
            Map<String, String> map = authUtil.auth(request);
            deviceId = map.get("deviceId");
            userId = map.get("userId");

            VipPack vipPack = vipPackService.getVipPackById(vipPackId);
            if(vipPack == null) {
                throw new CommonException("会员套餐不存在");
            }

            OrderTO order = new OrderTO();
            User user = baseInfoService.getUserById(userId);
            order.setObjectId(vipPackId);
            order.setAmount(vipPack.getPay());
            order.setUserId(userId);
            order.setAvatar(user.getAvatar());
            order.setMobile(user.getMobile());
            if(Assert.isNotNull(user.getName())){
                order.setName(user.getName());
            } else {
                order.setName(user.getMobile());
            }
            order.setQuantity(vipPack.getMonth());
            order.setContent(vipPack.getVipPackName());
            order.setProduct(product);
            order.setSource(product);
            return orderService.generateOrder(order, request.getHeader("access_token"), deviceId);
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
        LogUtil.action(ServiceType.USER, "购买会员,{},{},{},{}", userId, deviceId, vipPackId, product);
        return result;
    }


    @RequestMapping(value = "{userId}/upgrade/v/{vipPackId}", method = RequestMethod.POST)
    public ResultTO upgradeVIP(HttpServletRequest request,
                               @PathVariable String userId,
                               @PathVariable String vipPackId) {
        ResultTO result = new ResultTO();
        try {
            VipPack vipPack = vipPackService.getVipPackById(vipPackId);
            if(vipPack == null) {
                throw new CommonException("会员套餐不存在");
            }
            baseInfoService.upgradeVIP(userId, vipPack.getMonth());
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action(ServiceType.USER, "用户购买会员，升级完成{}", userId);
        return result;
    }

}
