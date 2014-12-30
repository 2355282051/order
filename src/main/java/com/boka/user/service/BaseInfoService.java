package com.boka.user.service;

import com.boka.common.constant.ProductType;
import com.boka.common.exception.CommonException;
import com.boka.common.exception.ExceptionCode;
import com.boka.common.exception.LoginException;
import com.boka.common.util.Assert;
import com.boka.common.util.AuthUtil;
import com.boka.common.util.RandomUtil;
import com.boka.user.constant.StatusConstant;
import com.boka.user.dto.UserTO;
import com.boka.user.model.User;
import com.boka.user.repository.BaseInfoRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class BaseInfoService {

	@Autowired
	private BaseInfoRepository baseInfoRepository;
	
	@Autowired
	private AuthUtil authUtil;
	
	public String reg(UserTO user) throws CommonException {
		//验证码检验
		if(!authUtil.authMobile(user.getMobile(), user.getAuthcode(), ProductType.BEAUTY))
		{
			throw new CommonException(ExceptionCode.AUTH_FAILD);
		}
		if(Assert.isNull(user.getPassword())) {
			throw new CommonException(ExceptionCode.PARAM_NULL);
		}
		User bean = new User();
		bean.setCreateDate(Calendar.getInstance().getTime());
		bean.setMobile(user.getMobile());
		bean.setSalt(RandomUtil.randomSalt());
		//MD5加盐
		bean.setPassword(DigestUtils.md5Hex(bean.getSalt()+user.getPassword()));
		bean = baseInfoRepository.save(bean);
		//生成token
		return authUtil.getToken(bean.getId());
	}

	public void activate(UserTO user) throws CommonException {

		User bean = baseInfoRepository.findOne(user.getId());
		bean.setName(user.getName());
		bean.setAvatar(user.getAvatar());
		bean.setSex(user.getSex());
		bean.setUpdateDate(Calendar.getInstance().getTime());
		bean.setLastLoginDate(bean.getCreateDate());
		bean.setLoc(user.getLoc());
		bean.setActivatedStatus(StatusConstant.activated);
		bean.setProduct(user.getProduct());
		baseInfoRepository.save(bean);
	}

	public UserTO login(UserTO user) throws LoginException, CommonException {
		User bean = baseInfoRepository.findByMobile(user.getMobile());
		if(bean ==null) {
			throw new LoginException(ExceptionCode.USER_NOT_EXISTS);
		}
		else if(!DigestUtils.md5Hex(bean.getSalt()+user.getPassword()).equals(bean.getPassword())) {
			throw new LoginException(ExceptionCode.PASSWORD_ERROR);
		}
		UserTO result = new UserTO();
		result.setAvatar(bean.getAvatar());
		result.setActivatedStatus(bean.getActivatedStatus());
		result.setMobile(bean.getMobile());
		result.setName(bean.getName());
		result.setSex(bean.getSex());
		result.setAccess_token(authUtil.getToken(bean.getId()));
		return result;
	}

	public void openAuth(UserTO user) {

		User bean = baseInfoRepository.findOne(user.getQqId());
		if(bean == null) {
			bean = new User();
			bean.setCreateDate(Calendar.getInstance().getTime());
			bean.setSalt(RandomUtil.randomSalt());
			bean.setAvatar(user.getAvatar());
			bean.setId(user.getQqId());
			bean.setSex(user.getSex());
			bean.setLoc(user.getLoc());
			bean.setName(user.getName());
			bean.setProduct(user.getProduct());
			bean.setActivatedStatus(2);
		} else {
			bean.setAvatar(user.getAvatar());
			bean.setLoc(user.getLoc());
			bean.setName(user.getName());
		}
		baseInfoRepository.save(bean);
	}

	public void bindMobile(UserTO user) throws CommonException {
		//验证码检验
		if(!authUtil.authMobile(user.getMobile(), user.getAuthcode(), ProductType.BEAUTY))
		{
			throw new CommonException(ExceptionCode.AUTH_FAILD);
		}
		if(Assert.isNull(user.getPassword())) {
			throw new CommonException(ExceptionCode.PARAM_NULL);
		}
		User bean = baseInfoRepository.findOne(user.getId());
		bean.setMobile(user.getMobile());
		bean.setSalt(RandomUtil.randomSalt());
		bean.setActivatedStatus(1);
		//MD5加盐
		bean.setPassword(DigestUtils.md5Hex(bean.getSalt()+ user.getPassword()));
		baseInfoRepository.save(bean);
	}
}
