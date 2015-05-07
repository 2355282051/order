package com.boka.user.factory;

import com.boka.user.service.UserService;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Admin on 2015/5/5 0005.
 */
public class UserServiceFactory {

    public static UserService getService(HttpServletRequest request, String product) {
        WebApplicationContext context = (WebApplicationContext)request.getSession().getServletContext().getAttribute("WEBAPPLICATIONCONTEXT");
        return context.getBean(product, UserService.class);
    }
}
