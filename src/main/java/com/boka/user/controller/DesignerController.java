package com.boka.user.controller;

import com.boka.common.constant.ProductType;
import com.boka.common.constant.ServiceType;
import com.boka.common.exception.AuthException;
import com.boka.common.util.AuthUtil;
import com.boka.common.util.LogUtil;
import com.boka.common.dto.ResultTO;
import com.boka.user.model.Location;
import com.boka.user.service.DesignerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@RestController
public class DesignerController {

    @Autowired
    private DesignerService designerService;

    @Autowired
    private AuthUtil authUtil;

    @RequestMapping(value = "/designer/near/c/{city}", method = RequestMethod.GET)
    public ResultTO getNearDesigners(HttpServletRequest request, Double lat, Double lng, int page, @PathVariable("city") String city, String keyword) {
        ResultTO result = new ResultTO();
        String deviceId = null;
        try {
            Map<String, String> map = authUtil.preAuth(request);
            deviceId = map.get("deviceId");
            Location loc = new Location(lat, lng);
            result.setResult(designerService.findNearDesigners(loc, city, keyword, page));
        } catch (AuthException ae) {
            result.setCode(403);
            result.setSuccess(false);
            result.setMsg(ae.getMessage());
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action(ServiceType.USER, "获取附近发型师用户,{},{},{},{},{}", city, lat, lng, deviceId, ProductType.BEAUTY);
        return result;
    }

    @RequestMapping(value = "/designer/city/c/{city}", method = RequestMethod.GET)
    public ResultTO getCityDesigners(HttpServletRequest request, Double lat, Double lng, int page, @PathVariable("city") String city, String keyword) {
        ResultTO result = new ResultTO();
        String deviceId = null;
        try {
            Map<String, String> map = authUtil.preAuth(request);
            deviceId = map.get("deviceId");
            Location loc = new Location(lat, lng);
            result.setResult(designerService.findCityDesigners(loc, city, keyword, page));
        } catch (AuthException ae) {
            result.setCode(403);
            result.setSuccess(false);
            result.setMsg(ae.getMessage());
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action(ServiceType.USER, "获取同城发型师用户,{},{},{},{},{}", city, lat, lng, deviceId, ProductType.BEAUTY);
        return result;
    }

    @RequestMapping(value = "/designer/country", method = RequestMethod.GET)
    public ResultTO getCountryDesigners(HttpServletRequest request, Double lat, Double lng, int page) {
        ResultTO result = new ResultTO();
        String deviceId = null;
        try {
            Map<String, String> map = authUtil.preAuth(request);
            deviceId = map.get("deviceId");
            Location loc = new Location(lat, lng);
            result.setResult(designerService.findCountyDesigners(loc, page));
        } catch (AuthException ae) {
            result.setCode(403);
            result.setSuccess(false);
            result.setMsg(ae.getMessage());
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action(ServiceType.USER, "获取全国发型师用户,{},{},{},{}", lat, lng, deviceId, ProductType.BEAUTY);
        return result;
    }

    @RequestMapping(value = "/designer/get/{id}", method = RequestMethod.GET)
    public ResultTO getUserInfo(HttpServletRequest request, @PathVariable("id") String id) {
        ResultTO result = new ResultTO();
        try {
            result.setResult(designerService.getUserInfo(id));
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
//        LogUtil.action("获取发型师基本信息,{},{},{}", userId, deviceId, id);
        return result;
    }

    @RequestMapping(value = "/designer/star/today/c/{city}", method = RequestMethod.GET)
    public ResultTO getTodayStar(HttpServletRequest request, @PathVariable("city") String city) {
        ResultTO result = new ResultTO();
        String deviceId = null;
        String userId = null;
        try {
            Map<String, String> map = authUtil.preAuth(request);
            deviceId = map.get("deviceId");
            userId = map.get("userId");
            result.setResult(designerService.getDesignerStar(city));
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action(ServiceType.USER, "获取发型师今日之星信息,{},{},{}", userId, deviceId, city);
        return result;
    }

    @RequestMapping(value = "/designer/shop/{id}/get", method = RequestMethod.GET)
    public ResultTO getShopDesigner(HttpServletRequest request, @PathVariable("id") String id) {
        ResultTO result = new ResultTO();
        String deviceId = null;
        String userId = null;
        try {
//            Map<String, String> map = authUtil.preAuth(request);
//            deviceId = map.get("deviceId");
//            userId = map.get("userId");
            result.setResult(designerService.getShopDesigner(id));
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action(ServiceType.USER, "获取门店的发型师信息,{},{},{}", userId, deviceId, id);
        return result;
    }

    @RequestMapping(value="/designer/inc/{id}/reserve",method=RequestMethod.POST)
    public ResultTO incReserveCount(@PathVariable("id") String id) {
        ResultTO result = new ResultTO();
        try {
            designerService.incReserveCount(id);
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

}
