package com.boka.common.util;

import com.boka.user.constant.PageConstant;

public class PageUtil {
	/**
	  * 当前页起始条数
	  * 
	  * @param value
	  * @return
	  */
	public static int fromPage(int page) {
	  return (page-1)*PageConstant.DEFAULT_LIST_SIZE;
	}
	/**
	  * 当前页结束条数
	  * 
	  * 
	  * @param value
	  * @return
	  */
	public static int toPage(int page) {
	  return page*PageConstant.DEFAULT_LIST_SIZE;
	}
}
