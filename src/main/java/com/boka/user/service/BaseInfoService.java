package com.boka.user.service;

import java.util.Calendar;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boka.common.exception.CommonException;
import com.boka.common.exception.ExceptionCode;
import com.boka.common.exception.LoginException;
import com.boka.common.util.AuthUtil;
import com.boka.common.util.RandomUtil;
import com.boka.user.dto.UserTO;
import com.boka.user.model.User;
import com.boka.user.repository.BaseInfoRepository;

@Service
public class BaseInfoService {

	@Autowired
	private BaseInfoRepository baseInfoRepository;
	
	@Autowired
	private AuthUtil authUtil;
	
	public void reg(UserTO user) throws CommonException {
		//验证码检验
		if(!authUtil.authMobile(user.getMobile(), user.getAuthcode()))
		{
			throw new CommonException(ExceptionCode.AUTH_FAILD);
		}
		User bean = new User();
		bean.setCreateDate(Calendar.getInstance().getTime());
		bean.setMobile(user.getMobile());
		bean.setSalt(RandomUtil.randomSalt());
		//MD5加盐
		bean.setPassword(DigestUtils.md5Hex(bean.getSalt()+user.getPassword()));
		bean = baseInfoRepository.save(bean);
	}

	public void activate(UserTO user) {
		User bean = baseInfoRepository.findOne(user.getId());
		bean.setName(user.getName());
		bean.setHeadImage(user.getHeadImage());
		bean.setSex(user.getSex());
		bean.setUpdateDate(Calendar.getInstance().getTime());
		bean.setLastLoginDate(bean.getCreateDate());
		bean.setLoc(user.getLoc());
		baseInfoRepository.save(bean);
	}

	public String login(UserTO user) throws LoginException, CommonException {
		User bean = baseInfoRepository.findByMobile(user.getMobile());
		if(bean ==null) {
			throw new LoginException(ExceptionCode.USER_NOT_EXISTS);
		}
		else if(!DigestUtils.md5Hex(bean.getSalt()+user.getPassword()).equals(bean.getPassword())) {
			throw new LoginException(ExceptionCode.PASSWORD_ERROR);
		}
		String access_token = authUtil.getToken(bean.getId());
		return access_token;
	}
	
}
