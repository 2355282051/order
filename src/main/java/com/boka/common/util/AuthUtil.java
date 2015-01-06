package com.boka.common.util;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Component;

import com.boka.common.exception.AuthException;
import com.boka.common.exception.CommonException;
import com.boka.common.exception.ExceptionCode;

@Component
public class AuthUtil {
	
	@Resource(name="redisTemplate")
	private HashOperations<String, String, String> hashOps;


	
	/**
	  * 验证用户返回用户ID
	  * 
	  * @param request
	  * @return
	 * @throws AuthException 
	  */
	public Map<String, String> auth(HttpServletRequest request) throws AuthException {
		String access_token = request.getHeader("access_token");
		String deviceId = request.getHeader("device_id");
		if(Assert.isNotNull(access_token) && Assert.isNotNull(deviceId))
		{
			Map<String, String> result = new HashMap<>();
			result.put("deviceId", deviceId);
			result.put("userId", hashOps.get(access_token, "userId"));
			if(Assert.isNotNull(result.get("userId")))
				return result;
		}
		throw new AuthException(ExceptionCode.TOKEN_NOT_EXISTS);
	}
	
	/**
	  * 不需要登录,只需要获取信息
	  * 
	  * @param request
	  * @return
	 * @throws AuthException 
	  */
	public Map<String, String> preAuth(HttpServletRequest request) throws AuthException {
		String access_token = request.getHeader("access_token");
		String deviceId = request.getHeader("device_id");
		if(Assert.isNotNull(deviceId))
		{
			Map<String, String> result = new HashMap<>();
			result.put("deviceId", deviceId);
			if(Assert.isNotNull(access_token))
				result.put("userId", hashOps.get(access_token, "userId"));
			
			return result;
		}
		throw new AuthException(ExceptionCode.AUTH_FAILD);
	}

	/**
	 *  用户登出，直接删除Redis数据
	 * @param request
	 * @throws AuthException
	 */
	public Map<String, String> removeAuth(HttpServletRequest request) throws AuthException {
		String access_token = request.getHeader("access_token");
		String deviceId = request.getHeader("device_id");
		if(Assert.isNotNull(access_token)){
			Map<String, String> result = new HashMap<>();
			result.put("deviceId", deviceId);
			result.put("userId", hashOps.get(access_token, "userId"));
			hashOps.delete(access_token, "userId");
			return result;
		}
		throw new AuthException(ExceptionCode.AUTH_FAILD);
	}

	/**
	 * 手机验证码验证
	 * @param mobile
	 * @param code
	 * @param product
	 * @return
	 */
	public boolean authMobile(String mobile, String code, String product) {
		if(Assert.isNotNull(mobile) && Assert.isNotNull(code))
		{
			String authcode = hashOps.get(RedisNsUtil.authName(mobile), product);
			if(code.equals(authcode))
				return true;
		}
		return false;
	}
	
	/**
	  * 生成access_token
	  * 
	  * @param id
	  * @return
	 * @throws CommonException 
	  */
	public String getToken(String id) throws CommonException {
		String token = null;
		if(Assert.isNotNull(id))
		{
			token = TokenUtil.getAccessToken();
			hashOps.put(token, "userId", id);

		}
		return token;
	}

	/**
	 * 第三方OpenID检测
	 * @return
	 * @throws AuthException
	 * @throws CommonException
	 */
	public Map<String, String> openAuth(HttpServletRequest request)throws AuthException, CommonException {
		String access_token = request.getHeader("access_token");
		String deviceId = request.getHeader("device_id");
		System.out.println("access_token"+access_token);
		System.out.println("deviceId"+deviceId);
		if(Assert.isNotNull(access_token) && Assert.isNotNull(deviceId))
		{
			Map<String, String> result = new HashMap<String, String>();
			result.put("deviceId", deviceId);
			result.put("access_token", access_token);
			return result;
		}
		throw new AuthException(ExceptionCode.AUTH_FAILD);
	}

	/**
	 * 第三方OpenID缓存token
	 * @return
	 * @throws AuthException
	 * @throws CommonException
	 */
	public void saveOpenAuthToken(String access_token,String userId)throws AuthException, CommonException {
		if(Assert.isNotNull(access_token) && Assert.isNotNull(userId))
		{
			hashOps.put(access_token, "userId", userId);
			return;
		}
		throw new AuthException(ExceptionCode.AUTH_FAILD);
	}
	
}
