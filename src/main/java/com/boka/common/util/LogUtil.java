package com.boka.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.springframework.stereotype.Component;

import com.boka.common.constant.LogType;
import com.boka.common.constant.ServiceType;

/**
 * 
 * Title : 日志操作辅助类
 * 
 * Description:日志操作辅助类
 * 
 * Author :
 * 
 */
@Component("logUtil")
public class LogUtil {
	
	private static final Marker SERVICE_MARKER = MarkerManager.getMarker(ServiceType.USER);

	/**
	 * 动作日志
	 */
	private static final Logger actionlog = LogManager.getLogger(LogType.ACTION_LOG);


	/**
	 * 
	 * 作用描述：调试日志：记录类名、日志信息、数据
	 * 
	 * 
	 * 修改说明：
	 * 
	 * 
	 * @param className
	 *            类名
	 * @param message
	 *            日志信息
	 * @param data
	 *            数据
	 */
	public static void action(String message,Object ... obj) {
		actionlog.debug(SERVICE_MARKER, message, obj);
	}
	
}
