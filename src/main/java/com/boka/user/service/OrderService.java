package com.boka.user.service;

import com.boka.common.constant.Constant;
import com.boka.common.dto.ResultTO;
import com.boka.user.dto.OrderTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by boka on 15-1-14.
 */
@Service
public class OrderService {

    private static Logger logger = Logger.getLogger(OrderService.class);

    @Autowired
    private RestTemplate restTemplate;

    public ResultTO generateOrder(OrderTO order, String accessToken, String deviceId) {
        return restTemplate.exchange(Constant.ORDER_URL + "/add",
                HttpMethod.POST,
                new HttpEntity<OrderTO>(order, getHttpHeaders(accessToken, deviceId)),
                ResultTO.class).getBody();//restTemplate.postForObject(Constant.ORDER_URL + "/add", order, ResultTO.class);
    }

    private HttpHeaders getHttpHeaders(String accessToken, String deviceId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("access_token", accessToken);
        headers.set("device_id", deviceId);
        return headers;
    }
}
