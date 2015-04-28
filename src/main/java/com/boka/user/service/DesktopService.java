package com.boka.user.service;

import com.alibaba.fastjson.JSON;
import com.boka.common.constant.Constant;
import com.boka.common.dto.ResultTO;
import com.boka.user.model.Designer;
import com.boka.user.model.Employee;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by Admin on 2014/12/31 0031.
 */
@Service
public class DesktopService {

    private static Logger logger = Logger.getLogger(DesktopService.class);

    @Autowired
    private RestTemplate restTemplate;

    public List<Designer> getDesigner(String id) {
        ResultTO result = restTemplate.getForObject("http://m.lianglichina.com/shop/{id}/emp/list", ResultTO.class, id);
        if (result.getResult() != null)
            return JSON.parseArray(result.getResult().toString(), Designer.class);
        else
            return null;
    }

    public String regUser(Employee emp) {
        ResultTO result = restTemplate.postForObject(Constant.SYNC_REG_USER_URL, emp, ResultTO.class);
        if (result.isSuccess())
            return result.getResult().toString();
        else
            return null;
    }

    public boolean joinShop(Employee emp) {
        ResultTO result = restTemplate.postForObject(Constant.SYNC_JOIN_SHOP_URL, emp, ResultTO.class);
        return result.isSuccess();
    }

    public String bindShop(Employee emp) {
        ResultTO result = restTemplate.postForObject(Constant.SYNC_BIND_SHOP_URL, emp, ResultTO.class);
        if (result.isSuccess())
            return result.getResult().toString();
        else
            return null;
    }

    public String regShop(Employee emp) {
        ResultTO result = restTemplate.postForObject(Constant.SYNC_REG_SHOP_URL, emp, ResultTO.class);
        if (result.isSuccess())
            return result.getResult().toString();
        else
            return null;
    }

    public String acceptByShop(Employee emp) {
        logger.info("+++++++++++++++" + JSON.toJSON(emp));
        logger.info("+++++++++++++++" + Constant.SYNC_ACCEPT_SHOP_URL);
        ResponseEntity<ResultTO>  response = restTemplate.postForEntity(Constant.SYNC_ACCEPT_SHOP_URL, emp, ResultTO.class);
        if(response.getStatusCode() == HttpStatus.OK) {
            ResultTO result = response.getBody();
            logger.info("###############" + JSON.toJSON(result));
            if (result.isSuccess() && result.getResult() != null)
                return result.getResult().toString();
        }
        return null;
    }

    public boolean refuseByShop(Employee emp) {
        ResultTO result = restTemplate.postForObject(Constant.SYNC_REFUSE_SHOP_URL, emp, ResultTO.class);
        return result.isSuccess();
    }

    public Employee addUser(Employee emp) {
        ResultTO result = restTemplate.postForObject(Constant.SYNC_ADD_USER_URL, emp, ResultTO.class);
        if (result.isSuccess())
            return JSON.parseObject(result.getResult().toString(), Employee.class);
        else
            return null;
    }

    public boolean editUser(Employee emp) {
        ResultTO result = restTemplate.postForObject(Constant.SYNC_EDIT_USER_URL, emp, ResultTO.class);
        return result.isSuccess();
    }

    public boolean leave(Employee emp) {
        ResultTO result = restTemplate.postForObject(Constant.SYNC_LEAVE_EMP_URL, emp, ResultTO.class);
        return result.isSuccess();
    }

    public boolean resetPassword(Employee emp) {
        ResultTO result = restTemplate.postForObject(Constant.SYNC_RESET_PWD_URL, emp, ResultTO.class);
        return result.isSuccess();
    }
}
