package com.boka.user.constant;

import com.boka.common.constant.Constant;
import com.boka.common.util.PropertiesParse;
import org.apache.log4j.Logger;

/**
 * Created by FuZhaohui on 2015/1/23.
 */
public class SystemConstant extends Constant {

    private static Logger logger = Logger.getLogger(SystemConstant.class);

    public static boolean ONS_STARTED = false;

    public static String MESSAGE_URL="http://192.168.2.65/message";


    public static void loadConfig() {
        loadCommonConf();
        Constant.loadConfig();
    }

    private static void loadCommonConf() {
        if(PropertiesParse.getProperty("ONS_STARTED")!=null){
            SystemConstant.ONS_STARTED= Boolean.parseBoolean(PropertiesParse.getProperty("ONS_STARTED"));
            logger.info("*******Constant.ONS_STARTED:" + SystemConstant.ONS_STARTED);
        }

        if(PropertiesParse.getProperty("MESSAGE_URL")!=null){
            SystemConstant.MESSAGE_URL= PropertiesParse.getProperty("MESSAGE_URL");
            logger.info("*******Constant.MESSAGE_URL:" + SystemConstant.MESSAGE_URL);
        }
    }

}
