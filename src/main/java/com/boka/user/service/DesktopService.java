package com.boka.user.service;

import com.alibaba.fastjson.JSON;
import com.boka.common.dto.ResultTO;
import com.boka.user.model.Designer;
import com.boka.user.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by Admin on 2014/12/31 0031.
 */
@Service
public class DesktopService {

    @Autowired
    private RestTemplate restTemplate;

    public List<Designer> getDesigner(String id) {
        ResultTO result = restTemplate.getForObject("http://192.168.2.65/shop/{id}/emp/list", ResultTO.class, id);
        if (result.getResult() != null)
            return JSON.parseArray(result.getResult().toString(), Designer.class);
        else
            return null;
    }

    public String regUser(Employee emp) {
        ResultTO result = restTemplate.postForObject("http://192.168.2.65/sync/reg/user", emp, ResultTO.class);
        if (result.isSuccess())
            return result.getResult().toString();
        else
            return null;
    }

    public boolean joinShop(Employee emp) {
        ResultTO result = restTemplate.postForObject("http://192.168.2.65/sync/join/shop", emp, ResultTO.class);
        return result.isSuccess();
    }

    public String bindShop(Employee emp) {
        ResultTO result = restTemplate.postForObject("http://192.168.2.65/sync/bind/shop", emp, ResultTO.class);
        if (result.isSuccess())
            return result.getResult().toString();
        else
            return null;
    }

    public String regShop(Employee emp) {
        ResultTO result = restTemplate.postForObject("http://192.168.2.65/sync/reg/shop", emp, ResultTO.class);
        if (result.isSuccess())
            return result.getResult().toString();
        else
            return null;
    }

    public String acceptByShop(Employee emp) {
        ResultTO result = restTemplate.postForObject("http://192.168.2.65/sync/accept/shop", emp, ResultTO.class);
        if (result.isSuccess())
            return result.getResult().toString();
        else
            return null;
    }

    public boolean refuseByShop(Employee emp) {
        ResultTO result = restTemplate.postForObject("http://192.168.2.65/sync/refuse/shop", emp, ResultTO.class);
        return result.isSuccess();
    }

    public Employee addUser(Employee emp) {
        ResultTO result = restTemplate.postForObject("http://192.168.2.65/sync/add/user", emp, ResultTO.class);
        if (result.isSuccess())
            return JSON.parseObject(result.getResult().toString(), Employee.class);
        else
            return null;
    }

    public boolean editUser(Employee emp) {
        ResultTO result = restTemplate.postForObject("http://192.168.2.65/sync/edit/user", emp, ResultTO.class);
        return result.isSuccess();
    }

    public boolean leave(Employee emp) {
        ResultTO result = restTemplate.postForObject("http://192.168.2.65/sync/employee/leave", emp, ResultTO.class);
        return result.isSuccess();
    }

    public boolean resetPassword(Employee emp) {
        ResultTO result = restTemplate.postForObject("http://192.168.2.65/sync/reset/password", emp, ResultTO.class);
        return result.isSuccess();
    }
}
