package com.boka.user.service;

import com.alibaba.fastjson.JSON;
import com.boka.user.dto.S3DesignerTO;
import com.boka.user.model.Designer;
import com.boka.user.model.ReserveInfo;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2015/1/12 0012.
 */
@Service
public class S3UserService {

    public List<Designer> getDesigner(String chainUrl, String custId, String compId) throws IOException {
        String result;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet("http://" + chainUrl + "/loadHam01.action?compId=" + compId);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
            httpGet.setConfig(requestConfig);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            try {
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity);
            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
        List<Designer> designers = new ArrayList<>();

        if (("NODATA").equals(result))
            return null;

        List<S3DesignerTO> list = JSON.parseArray(result, S3DesignerTO.class);
        for (S3DesignerTO item : list) {
            if (("1").equals(item.getHaa46i())) {
                Designer designer = new Designer();
                designer.setName(item.getHaa02c());
                designer.setEmpId(item.getHaa01c());
                designer.setSex(1);
                ReserveInfo reserveInfo = new ReserveInfo();
                reserveInfo.setStatus(1);
                designer.setReserveInfo(reserveInfo);
                designer.setAvatar("http://" + chainUrl + "/downloadPic.action?compid=" + compId + "&stype=3&wpid=" + item.getHaa01c());
                //TODO 获取发界头像覆盖
                designers.add(designer);
            }
        }
        return designers;
    }
}
