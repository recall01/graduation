package com.better517na.usermanagement.Aspect;

import com.better517na.usermanagement.Annotation.ExceptionLogger;
import com.better517na.usermanagement.Annotation.SysLogger;
import com.better517na.usermanagement.model.Response;
import com.better517na.usermanagement.model.SysLog;
import com.better517na.usermanagement.utils.HttpUtils;
import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
@Component
public class ReturnAspect {
    @Autowired
    private AmqpTemplate amqpTemplate;
    //切点
    @Pointcut("execution(* com.better517na.usermanagement.controller.*.*(..))")
    public void loggerPointCut(){ }

    //returning:定义一个返参名用来获取返参
    @AfterReturning(returning = "r",pointcut = "loggerPointCut()")
    public void printAfterThrowingLog(Response r){
        System.out.println("返参:"+new Gson().toJson(r));
    }
}
