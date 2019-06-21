package com.example.cinema.interceptor;

import com.example.cinema.config.InterceptorConfiguration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception{
        HttpSession session=httpServletRequest.getSession();
        if(null!=session && !session.getAttribute(InterceptorConfiguration.SESSION_KEY).equals("admin")){
            httpServletResponse.sendRedirect("/index");
            return false;
        }
        else
            return true;
    }
}
