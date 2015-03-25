package com.boka.user.service;

import com.alibaba.fastjson.JSON;
import com.boka.common.constant.Constant;
import com.boka.common.dto.ResultTO;
import com.boka.common.util.Assert;
import com.boka.user.model.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Admin on 2014/12/31 0031.
 */
@Service
public class ShopService {

    @Autowired
    private RestTemplate restTemplate;

    public Shop getShop(String id) {
        ResultTO result = restTemplate.getForObject(Constant.GET_SHOP_INFO_URL, ResultTO.class, id);
        if (result.getResult() != null)
            return JSON.parseObject(result.getResult().toString(), Shop.class);
        else
            return null;
    }

    public boolean updateShopAdmin(Shop shop) {
        ResultTO result = restTemplate.postForObject("http://192.168.2.65/shop/update/admin", shop, ResultTO.class);
        if (result != null)
            return result.isSuccess();
        else
            return false;
    }

    public boolean addShop(Shop shop) {
        ResultTO result = restTemplate.postForObject("http://192.168.2.65/shop/add", shop, ResultTO.class);
        if (result != null)
            return result.isSuccess();
        else
            return false;
    }
}
