package com.boka.user.controller;

import com.boka.common.constant.ProductType;
import com.boka.common.constant.ServiceType;
import com.boka.common.dto.ResultTO;
import com.boka.common.exception.AuthException;
import com.boka.common.exception.CommonException;
import com.boka.common.util.AuthUtil;
import com.boka.common.util.LogUtil;
import com.boka.user.model.VipPack;
import com.boka.user.service.VipPackService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequestMapping(value = "/vippack")
@RestController
public class VipPackController {

    private static Logger logger = Logger.getLogger(VipPackController.class);

    @Autowired
    private VipPackService vipPackService;

    @Autowired
    private AuthUtil authUtil;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultTO addVipPack(HttpServletRequest request, @RequestBody VipPack vipPack) {
        ResultTO result = new ResultTO();
        String userId = null;
        String deviceId = null;
        try {
            Map<String, String> map = authUtil.auth(request);
            userId = map.get("userId");
            deviceId = map.get("deviceId");
            vipPackService.add(vipPack);
        } catch (CommonException ce) {
            result.setCode(500);
            result.setSuccess(false);
            result.setMsg(ce.getMessage());
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action(ServiceType.USER, "添加会员套餐,{},{},{}", userId, deviceId, ProductType.BEAUTY);
        return result;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultTO getAllVipPack(HttpServletRequest request) {
        ResultTO result = new ResultTO();
        String userId = null;
        String deviceId = null;
        try {
            Map<String, String> map = authUtil.auth(request);
            userId = map.get("userId");
            deviceId = map.get("deviceId");
            result.setResult(vipPackService.getAllVipPack());
        } catch (AuthException ae) {
            result.setCode(403);
            result.setSuccess(false);
            result.setMsg(ae.getMessage());
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action(ServiceType.USER, "获取所有会员套餐,{},{},{}", userId, deviceId, ProductType.BEAUTY);
        return result;
    }

    @RequestMapping(value = "/{vipPackId}", method = RequestMethod.GET)
    public ResultTO getVipPack(HttpServletRequest request, @PathVariable String vipPackId) {
        ResultTO result = new ResultTO();
        String userId = null;
        String deviceId = null;
        try {
            Map<String, String> map = authUtil.auth(request);
            userId = map.get("userId");
            deviceId = map.get("deviceId");
            result.setResult(vipPackService.getVipPackById(vipPackId));
        } catch (AuthException ae) {
            result.setCode(403);
            result.setSuccess(false);
            result.setMsg(ae.getMessage());
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action(ServiceType.USER, "获取会员套餐,{},{},{},{}", vipPackId, userId, deviceId, ProductType.BEAUTY);
        return result;
    }
}
