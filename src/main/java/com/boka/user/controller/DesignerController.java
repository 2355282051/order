package com.boka.user.controller;

import com.boka.common.constant.ProductType;
import com.boka.common.exception.AuthException;
import com.boka.common.util.AuthUtil;
import com.boka.common.util.LogUtil;
import com.boka.user.dto.ResultTO;
import com.boka.user.model.Location;
import com.boka.user.service.DesignerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class DesignerController {

    @Autowired
    private DesignerService designerService;

    @Autowired
    private AuthUtil authUtil;

    @RequestMapping(value = "/designer/near/c/{city}", method = RequestMethod.GET)
    public ResultTO getNearDesigners(HttpServletRequest request, Double lat, Double lng, int page, @PathVariable("city") String city) {
        ResultTO result = new ResultTO();
        String deviceId = null;
        try {
            Map<String, String> map = authUtil.preAuth(request);
            deviceId = map.get("deviceId");
            Location loc = new Location(lat, lng);
            result.setResult(designerService.findNearDesigners(loc, city, page));
        } catch (AuthException ae) {
            result.setCode(403);
            result.setSuccess(false);
            result.setMsg(ae.getMessage());
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action("获取附近发型师用户,{},{},{},{},{}", city, lat, lng, deviceId, ProductType.BEAUTY);
        return result;
    }

    @RequestMapping(value = "/designer/city/c/{city}", method = RequestMethod.GET)
    public ResultTO getCityDesigners(HttpServletRequest request, Double lat, Double lng, int page, @PathVariable("city") String city) {
        ResultTO result = new ResultTO();
        String deviceId = null;
        try {
            Map<String, String> map = authUtil.preAuth(request);
            deviceId = map.get("deviceId");
            Location loc = new Location(lat, lng);
            result.setResult(designerService.findCityDesigners(loc, city, page));
        } catch (AuthException ae) {
            result.setCode(403);
            result.setSuccess(false);
            result.setMsg(ae.getMessage());
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action("获取同城发型师用户,{},{},{},{},{}", city, lat, lng, deviceId, ProductType.BEAUTY);
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
        LogUtil.action("获取全国发型师用户,{},{},{},{}", lat, lng, deviceId, ProductType.BEAUTY);
        return result;
    }

    @RequestMapping(value = "/designer/get/{id}", method = RequestMethod.GET)
    public ResultTO getUserInfo(@PathVariable("id") String id) {
        ResultTO result = new ResultTO();
        try {
            result.setResult(designerService.getUserInfo(id));
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

}
