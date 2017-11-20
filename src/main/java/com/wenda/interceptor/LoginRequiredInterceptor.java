package com.wenda.interceptor;

import com.wenda.dao.LoginTicketDao;
import com.wenda.dao.UserDao;
import com.wenda.model.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wanganyu on 2017/11/12.
 */
@Component
public class LoginRequiredInterceptor implements HandlerInterceptor{
    @Autowired
    LoginTicketDao loginTicketDao;
    @Autowired
    UserDao userDao;

    @Autowired
    HostHolder hostHolder;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
       if(hostHolder.getUser()==null){
           httpServletResponse.sendRedirect("/reglogin?next="+httpServletRequest.getRequestURL());
       }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
