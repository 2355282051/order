package com.boka.user.service;

import com.alibaba.fastjson.JSON;
import com.boka.common.constant.Constant;
import com.boka.common.constant.ProductType;
import com.boka.common.dto.ResultTO;
import com.boka.user.constant.SystemConstant;
import com.boka.user.dto.Device;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by boka on 15-2-6.
 */
@Service
public class DeviceService {

    private static Logger logger = Logger.getLogger(DeviceService.class);

    @Autowired
    private RestTemplate restTemplate;

    public Device getDeviceInfo(String deviceId) {
        ResultTO result = restTemplate.getForObject(SystemConstant.MESSAGE_URL + "/app/{appName}/d/{deviceId}", ResultTO.class, ProductType.BEAUTY, deviceId);
        if(result != null && result.getCode() == 200) {
            return JSON.parseObject(result.getResult().toString(), Device.class);
        }
        return null;
    }

}
