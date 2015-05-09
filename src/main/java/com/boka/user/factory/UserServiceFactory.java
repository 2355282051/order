package com.boka.user.factory;

import com.boka.user.service.UserService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户服务工厂
 * Created by Admin on 2015/5/5 0005.
 */
public class UserServiceFactory {

    public static UserService getService(HttpServletRequest request, String product) {
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
        return context.getBean(product+"UserService", UserService.class);
    }
}
