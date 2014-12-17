package com.boka.user.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.boka.common.constant.ProductType;
import com.boka.common.exception.AuthException;
import com.boka.common.exception.CommonException;
import com.boka.common.exception.LoginException;
import com.boka.common.util.AuthUtil;
import com.boka.common.util.LogUtil;
import com.boka.user.dto.ResultTO;
import com.boka.user.dto.UserTO;
import com.boka.user.service.BaseInfoService;

@RestController
public class BaseInfoController {

	@Autowired
	private BaseInfoService baseInfoServie;
	
	@Autowired
	private AuthUtil authUtil;
	
	@RequestMapping(value="/beauty/reg",method=RequestMethod.POST)
	public ResultTO addUser (HttpServletRequest requset, @RequestBody UserTO user) {
		ResultTO result = new ResultTO();
		String deviceId = null;
		try {
			Map<String, String> map = authUtil.auth(requset);
			deviceId = map.get("deviceId");
			user.setProduct(ProductType.BEAUTY);
			result.setResult(baseInfoServie.reg(user));
		} catch (CommonException ce) {
			result.setCode(500);
			result.setSuccess(false);
			result.setMsg(ce.getMessage());
		} catch (Exception e) {
			result.setCode(500);
			result.setSuccess(false);
			e.printStackTrace();
		}
		LogUtil.action("用户注册,{},{},{}", user.getId(), deviceId, ProductType.BEAUTY);
		return result;
	}
	
	@RequestMapping(value="/beauty/activate",method=RequestMethod.POST)
	public ResultTO activateUser (HttpServletRequest requset, @RequestBody UserTO user) {
		ResultTO result = new ResultTO();
		String userId = null;
		String deviceId = null;
		try {
			Map<String, String> map = authUtil.auth(requset);
			userId = map.get("userId");
			deviceId = map.get("deviceId");
			user.setProduct(ProductType.BEAUTY);
			baseInfoServie.activate(user);
		} catch (AuthException ae) {
			result.setCode(403);
			result.setSuccess(false);
			result.setMsg(ae.getMessage());
		} catch (Exception e) {
			result.setCode(500);
			result.setSuccess(false);
			e.printStackTrace();
		}
		LogUtil.action("用户激活,{},{},{}", userId, deviceId, ProductType.BEAUTY);
		return result;
	}
	
	@RequestMapping(value="/beauty/login",method=RequestMethod.POST)
	public ResultTO loginUser (@RequestBody UserTO user) {
		ResultTO result = new ResultTO();
		try {
			baseInfoServie.login(user);
		} catch (LoginException le) {
			result.setCode(401);
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
