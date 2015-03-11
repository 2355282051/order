package com.boka.user.listener;

import com.alibaba.fastjson.JSON;
import com.aliyun.openservices.ons.api.*;
import com.boka.user.model.Designer;
import com.boka.user.service.DesignerService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Properties;

/**
 * Created by Admin on 2015/2/12 0012.
 */
public class SyncListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        syncDesigner(servletContextEvent);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    public static void syncDesigner(ServletContextEvent event) {

        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
        final DesignerService designerService = context.getBean("designerService", DesignerService.class);

        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ConsumerId, "CID_1772604614-114");
        properties.put(PropertyKeyConst.AccessKey, "YXax9hijbGKnkmAx");
        properties.put(PropertyKeyConst.SecretKey, "KHL4Gir3e6lnild7SUpJAJtqvZEVXA");
        Consumer consumer = ONSFactory.createConsumer(properties);
        consumer.subscribe("designer_sync_test", "desktop2new", new MessageListener() {
            @Override
            public Action consume(Message message, ConsumeContext consumeContext) {
                try {
                    Designer designer = null;
                    designer = JSON.parseObject(new String(message.getBody(), "utf-8"), Designer.class);
                    System.out.println(new String(message.getBody(), "utf-8"));
                    designerService.syncDesktopDesigner(designer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return Action.CommitMessage;
            }
        });
        consumer.start();
    }
}
