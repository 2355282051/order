package com.boka.user.controller;

import com.boka.common.constant.ProductType;
import com.boka.common.constant.ServiceType;
import com.boka.common.dto.ResultTO;
import com.boka.common.exception.AuthException;
import com.boka.common.exception.CommonException;
import com.boka.common.exception.LoginException;
import com.boka.common.util.Assert;
import com.boka.common.util.AuthUtil;
import com.boka.common.util.LogUtil;
import com.boka.user.constant.StatusConstant;
import com.boka.user.dto.UserTO;
import com.boka.user.model.Employee;
import com.boka.user.service.BaseInfoService;
import com.boka.user.service.EmployeeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class EmployeeController {

    private static Logger logger = Logger.getLogger(EmployeeController.class);

    @Autowired
    private BaseInfoService baseInfoService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AuthUtil authUtil;

    @RequestMapping(value = "/desktop/shop/accept/{status}", method = RequestMethod.POST)
    public ResultTO shopAccept(HttpServletRequest request, @RequestBody UserTO user, @PathVariable("status") String status) {
        ResultTO result = new ResultTO();
        String deviceId = null;
        String userId = null;
        try {
            Map<String, String> map = authUtil.auth(request);
            deviceId = map.get("deviceId");
            if(Assert.isNull(user.getProduct())) {
                user.setProduct(ProductType.DESKTOP);
            }
            if ("1".equals(status)) {
                employeeService.acceptByShop(user);
            }else {
                employeeService.refuseByShop(user);
            }
        } catch (CommonException ce) {
            result.setCode(500);
            result.setSuccess(false);
            result.setMsg(ce.getMessage());
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action(ServiceType.USER, "接受或拒绝员工加入,{},{},{}", user.getId(), deviceId, ProductType.DESKTOP);
        return result;
    }

    @RequestMapping(value = "/desktop/shop/{id}/get/p/{pid}", method = RequestMethod.GET)
    public ResultTO getShopEmployee(HttpServletRequest request, @PathVariable("id") String id, @PathVariable("pid") String pid, String keyword) {
        ResultTO result = new ResultTO();
        String deviceId = null;
        String userId = null;
        try {
            Map<String, String> map = authUtil.preAuth(request);
            deviceId = map.get("deviceId");
            userId = map.get("userId");
            result.setResult(employeeService.getShopEmployee(id, pid, keyword));
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtil.action(ServiceType.USER, "获取门店的员工信息,{},{},{}", userId, deviceId, id);
        return result;
    }

    @RequestMapping(value = "/desktop/employee/edit", method = RequestMethod.POST)
    public ResultTO editUser(HttpServletRequest request,@RequestBody Employee emp) {
        ResultTO result = new ResultTO();
        String userId = null;
        String deviceId = null;

        String access_token = request.getHeader("access_token");
        deviceId = request.getHeader("device_id");
        try {
            Map<String, String> map = authUtil.auth(request);
            userId = map.get("userId");
            deviceId = map.get("deviceId");
            emp.setProduct(ProductType.DESKTOP);
            emp.setId(userId);
            employeeService.edit(emp);
        } catch (AuthException ae) {
            result.setCode(403);
            result.setSuccess(false);
            result.setMsg(ae.getMessage());
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action(ServiceType.USER, "用户编辑,{},{},{}", userId, deviceId, ProductType.DESKTOP);
        return result;
    }

}
