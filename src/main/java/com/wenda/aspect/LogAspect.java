package com.wenda.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by wanganyu on 2017/11/09.
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger logger= LoggerFactory.getLogger(LogAspect.class);
    @Before("execution(* com.wenda.controller.IndexController.*(..))")
    public void beforeMethod(JoinPoint joinPoint){
       StringBuilder sb=new StringBuilder();
        for(Object arg:joinPoint.getArgs()){
            sb.append("arg:"+arg.toString()+"|");
        }
        logger.info("before method"+new Date()+sb.toString());
    }
    @After("execution(* com.wenda.controller.FollowController.*(..))")
    public void afterMethod(){
      logger.info("after method");
    }
}
