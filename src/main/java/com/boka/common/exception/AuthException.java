package com.boka.common.exception;

/**
 * 
 * Title : 用户验证异常
 * 
 * Description:用户验证异常
 * 
 * Author :韦嵩
 * 
 */
public class AuthException extends Exception {


	private static final long serialVersionUID = 5694699188175334854L;

	/**
	 * @param message
	 *            原异常信息
	 */
	public AuthException(String message) {
		super(message);
	}
	
}
