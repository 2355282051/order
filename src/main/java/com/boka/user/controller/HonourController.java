package com.boka.user.controller;

import com.boka.common.constant.ServiceType;
import com.boka.common.dto.ResultTO;
import com.boka.common.exception.AuthException;
import com.boka.common.exception.CommonException;
import com.boka.common.util.AuthUtil;
import com.boka.common.util.LogUtil;
import com.boka.user.service.HonourService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class HonourController {

	private static Logger logger = Logger.getLogger(HonourController.class);

	@Autowired
	private HonourService honourService;

	@Autowired
	private AuthUtil authUtil;


	@RequestMapping(value="/{designerId}/honour",method= RequestMethod.GET)
	public ResultTO getDesignerHonours(HttpServletRequest request, @PathVariable String designerId, int page) {
		ResultTO result = new ResultTO();
		String userId = null;
		String deviceId = null;
		try {
			Map<String, String> map = authUtil.preAuth(request);
			userId = map.get("userId");
			deviceId = map.get("deviceId");
			result.setResult(honourService.getHonours(designerId, page));
			LogUtil.action(ServiceType.USER, "获取发型师荣誉,{},{},{}", userId, deviceId, designerId);
		} catch (AuthException le) {
			result.setCode(403);
			result.setSuccess(false);
			result.setMsg(le.getMessage());
		} catch (CommonException ce) {
			result.setCode(400);
			result.setSuccess(false);
			result.setMsg(ce.getMessage());
		} catch (Exception e) {
			result.setCode(500);
			result.setSuccess(false);
			e.printStackTrace();
		}
		return result;
	}


}
