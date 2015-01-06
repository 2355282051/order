package com.boka.common.constant;

import com.boka.common.util.PropertiesParse;
import org.apache.log4j.Logger;

/**
 * Created by boka on 14-12-17.
 */
public class Constant {

    private static Logger logger = Logger.getLogger(Constant.class);

    public static String  SHOW_USER_SYNC_URL;


    public static void loadConfig() {
        loadConf();
    }

    private static void loadConf() {
        if(PropertiesParse.getProperty("SHOW_USER_SYNC_URL")!=null){
            Constant.SHOW_USER_SYNC_URL = PropertiesParse.getProperty("SHOW_USER_SYNC_URL");
            logger.info("*******Constant.SHOW_USER_SYNC_URL:" + Constant.SHOW_USER_SYNC_URL);
        }

    }

}
