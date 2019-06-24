package com.zzs.springboot.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class MyHandlerInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(MyHandlerInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        logger.info("自定义拦截器-->MyHandlerInterceptor.preHandle() 拦截调用Controller之前：   ");
        String name = httpServletRequest.getParameter("name");
        if ("zzs".equals(name)) {

            return true;
        }
        logger.info("自定义拦截器-->MyHandlerInterceptor.preHandle() 拦截调用Controller之前：  拦截请求 ");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        logger.info("自定义拦截器-->MyHandlerInterceptor.postHandle() 拦截调用Controller之后：   ");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
