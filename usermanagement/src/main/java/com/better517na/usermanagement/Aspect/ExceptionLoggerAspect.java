package com.better517na.usermanagement.Aspect;

import com.better517na.usermanagement.Annotation.ExceptionLogger;
import com.better517na.usermanagement.Annotation.SysLogger;
import com.better517na.usermanagement.model.SysLog;
import com.better517na.usermanagement.utils.HttpUtils;
import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
@Component
public class ExceptionLoggerAspect {
/*    @Autowired
    private AmqpTemplate amqpTemplate;
    //切点
    @Pointcut("execution(* com.better517na.usermanagement.business.*.*(..))")
    public void loggerPointCut(){ }
    @Before("loggerPointCut()")
    public void printBeforeLog(){
        System.out.println("ExceptionLoggerAspect-printBeforeLog执行啦");
    }
    @AfterThrowing("loggerPointCut()")
    public void printAfterThrowingLog(){
        System.out.println("ExceptionLoggerAspect-printAfterThrowingLog执行啦");
    }  */
}
