package com.boka.user.service;

import com.alibaba.fastjson.JSON;
import com.boka.common.constant.Constant;
import com.boka.common.dto.ResultTO;
import com.boka.user.dto.CommentTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boka on 15-2-6.
 */
@Service
public class CommentService {

    private static Logger logger = Logger.getLogger(CommentService.class);

    @Autowired
    private RestTemplate restTemplate;

    public int getReserveCommentCnt(String designerId, String accessToken, String deviceId) {
        ResultTO result = restTemplate.exchange(Constant.RESERVE_URL + "/comment/count/designer/{designerId}",
                HttpMethod.GET,
                new HttpEntity<String>(getHttpHeaders(accessToken, deviceId)),
                ResultTO.class, designerId).getBody();
        logger.info(result);
        if(result != null && result.getCode() == 200) {
            return Integer.valueOf(result.getResult().toString());
        }
        return 0;
    }

    private HttpHeaders getHttpHeaders(String accessToken, String deviceId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("access_token", accessToken);
        headers.set("device_id", deviceId);
        return headers;
    }


}
