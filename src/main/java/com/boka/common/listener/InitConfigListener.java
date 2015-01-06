package com.boka.common.listener;


import com.boka.common.constant.Constant;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitConfigListener implements ServletContextListener {
	
	private static Logger logger = Logger.getLogger(InitConfigListener.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("======loadConfig start==========");
		Constant.loadConfig();
		logger.info("======loadConfig end============");
	}

}
