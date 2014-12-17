package com.boka.common.util;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.boka.common.exception.AuthException;
import com.boka.common.exception.CommonException;
import com.boka.common.exception.ExceptionCode;

@Component
public class AuthUtil {
	
	@Resource(name="redisTemplate")
	private ValueOperations<String, String> authOps;
	
	/**
	  * 验证用户返回用户ID
	  * 
	  * @param requset
	  * @return
	 * @throws AuthException 
	  */
	public Map<String, String> auth(HttpServletRequest requset) throws AuthException {
		String access_token = requset.getHeader("access_token");
		String deviceId = requset.getHeader("deviceId");
		if(Assert.isNotNull(access_token) && Assert.isNotNull(deviceId))
		{
			Map<String, String> result = new HashMap<String, String>();
			result.put("deviceId", deviceId);
			result.put("userId", authOps.get(access_token));
			if(Assert.isNotNull(result.get("userId")))
				return result;
		}
		throw new AuthException(ExceptionCode.TOKEN_NOT_EXISTS);
	}
	
	/**
	  * 不需要登录,只需要获取信息
	  * 
	  * @param requset
	  * @return
	 * @throws AuthException 
	  */
	public Map<String, String> preAuth(HttpServletRequest requset) throws AuthException {
		String access_token = requset.getHeader("access_token");
		String deviceId = requset.getHeader("deviceId");
		if(Assert.isNotNull(deviceId))
		{
			Map<String, String> result = new HashMap<String, String>();
			result.put("deviceId", deviceId);
			if(Assert.isNotNull(access_token))
				result.put("userId", authOps.get(access_token));
			
			return result;
		}
		throw new AuthException(ExceptionCode.AUTH_FAILD);
	}
	
	/**
	  * 手机验证码验证
	  * 
	  * @param phone
	  * @param code
	  * @return
	  */
	public boolean authMobile(String mobile, String code) {
		if(Assert.isNotNull(mobile) && Assert.isNotNull(code))
		{
			String authcode = authOps.get(RedisNsUtil.authName(mobile));
			if(code.equals(authcode))
				return true;
		}
		return false;
	}
	
	/**
	  * 生成access_token
	  * 
	  * @param phone
	  * @param code
	  * @return
	 * @throws CommonException 
	  */
	public String getToken(String id) throws CommonException {
		String token = null;
		if(Assert.isNotNull(id))
		{
			token = TokenUtil.getAccessToken();
			authOps.set(RedisNsUtil.tokenName(token), id);
		}
		return token;
	}
}
