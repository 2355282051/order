package com.boka.common.exception;

/**
 * 
 * Title : 通用自定义异常
 * 
 * Description:通用的自定义异常
 * 
 * Author :韦嵩
 * 
 */
public class CommonException extends Exception {


	private static final long serialVersionUID = 7608330395582067150L;

	/**
	 * @param message
	 *            原异常信息
	 */
	public CommonException(String message) {
		super(message);
	}
	
	/**
	 * @param message
	 *            异常信息
	 */
	public CommonException(String message, Exception e) {
		super(message, e);
	}
	
}
