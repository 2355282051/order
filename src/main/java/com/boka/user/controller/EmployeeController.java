package com.boka.user.controller;

import com.boka.common.constant.ServiceType;
import com.boka.common.dto.ResultTO;
import com.boka.common.exception.CommonException;
import com.boka.common.exception.LoginException;
import com.boka.common.util.Assert;
import com.boka.common.util.AuthUtil;
import com.boka.common.util.LogUtil;
import com.boka.user.constant.StatusConstant;
import com.boka.user.dto.UserTO;
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

    @RequestMapping(value = "/{product}/shop/accept/{status}", method = RequestMethod.POST)
    public ResultTO shopAccept(HttpServletRequest request, @PathVariable String product, @RequestBody UserTO user, @PathVariable("status") String status) {
        ResultTO result = new ResultTO();
        String deviceId = null;
        String userId = null;
        try {
            Map<String, String> map = authUtil.auth(request);
            deviceId = map.get("deviceId");
            if(Assert.isNull(user.getProduct())) {
                user.setProduct(product);
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
        LogUtil.action(ServiceType.USER, "接受或拒绝员工加入,{},{},{}", user.getId(), deviceId, product);
        return result;
    }

}
