package com.boka.user.service;

import com.alibaba.fastjson.JSON;
import com.boka.common.constant.Constant;
import com.boka.common.dto.ResultTO;
import com.boka.user.model.Designer;
import com.boka.user.model.Shop;
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
        ResultTO result = restTemplate.getForObject("http://m.lianglichina.com/shop/{id}/emp/list", ResultTO.class, id);
        if (result.getResult() != null)
            return JSON.parseArray(result.getResult().toString(), Designer.class);
        else
            return null;
    }

}
