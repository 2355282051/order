package com.boka.user.controller;

import com.boka.common.constant.ServiceType;
import com.boka.common.dto.ResultTO;
import com.boka.common.exception.AuthException;
import com.boka.common.exception.CommonException;
import com.boka.common.exception.ExceptionCode;
import com.boka.common.util.Assert;
import com.boka.common.util.AuthUtil;
import com.boka.common.util.LogUtil;
import com.boka.user.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private AuthUtil authUtil;

    @RequestMapping(value = "/designer/{designerId}/like", method = RequestMethod.POST)
    public ResultTO like(HttpServletRequest request, @PathVariable String designerId) {
        ResultTO result = new ResultTO();
        String userId = null;
        String deviceId = null;
        try {
            if (Assert.isNull(designerId)) {
                throw new CommonException(ExceptionCode.PARAM_NULL);
            }
            Map<String, String> map = authUtil.auth(request);
            userId = map.get("userId");
            deviceId = map.get("deviceId");
            likeService.like(designerId, userId);
        } catch (AuthException ae) {
            result.setCode(403);
            result.setSuccess(false);
            result.setMsg(ae.getMessage());
        } catch (CommonException ce) {
            result.setCode(400);
            result.setSuccess(false);
            result.setMsg(ce.getMessage());
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action(ServiceType.DESIGNER, "点赞,{},{},{}", userId, deviceId, designerId);
        return result;
    }

    @RequestMapping(value = "/designer/{designerId}/like/cancel", method = RequestMethod.POST)
    public ResultTO cancelLike(HttpServletRequest request, @PathVariable String designerId) {
        ResultTO result = new ResultTO();
        String userId = null;
        String deviceId = null;
        try {
            if (Assert.isNull(designerId)) {
                throw new CommonException(ExceptionCode.PARAM_NULL);
            }
            Map<String, String> map = authUtil.auth(request);
            userId = map.get("userId");
            deviceId = map.get("deviceId");
            likeService.cancelLike(designerId, userId);
        } catch (AuthException ae) {
            result.setCode(403);
            result.setSuccess(false);
            result.setMsg(ae.getMessage());
        } catch (CommonException ce) {
            result.setCode(400);
            result.setSuccess(false);
            result.setMsg(ce.getMessage());
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            e.printStackTrace();
        }
        LogUtil.action(ServiceType.DESIGNER, "取消点赞,{},{},{}", userId, deviceId, designerId
        );
        return result;
    }

}
