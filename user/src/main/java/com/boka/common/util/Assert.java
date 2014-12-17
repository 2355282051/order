package com.boka.common.util;

public class Assert {
	/**
	  * 是否为空
	  * 
	  * @param value
	  * @return
	  */
	public static boolean isNull(String value) {
	  return value == null || value.trim().length() == 0;
	}
	/**
	  * 是否不为空
	  * 
	  * 
	  * @param value
	  * @return
	  */
	public static boolean isNotNull(String value) {
	  return !isNull(value);
	}
}
