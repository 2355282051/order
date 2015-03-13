package com.boka.user.listener;


import com.boka.user.constant.SystemConstant;
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
		SystemConstant.loadConfig();
		logger.info("======loadConfig end============");
	}

}
