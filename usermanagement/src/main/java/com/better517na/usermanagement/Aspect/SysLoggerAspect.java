package com.better517na.usermanagement.Aspect;

import com.better517na.usermanagement.Annotation.SysLogger;
import com.better517na.usermanagement.model.SysLog;
import com.better517na.usermanagement.utils.HttpUtils;
import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

@Aspect
@Component
public class SysLoggerAspect {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Pointcut("@annotation(com.better517na.usermanagement.Annotation.SysLogger)")
    public void loggerPointCut(){
        System.out.println("---loggerPointCut---");
    }
    @Before("loggerPointCut()")
    public void printSysLog(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLog sysLog = new SysLog();
        SysLogger sysLogger = method.getAnnotation(SysLogger.class);
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        if(sysLogger!=null){
            String operation =sysLogger.value();
            if(operation != null&&!"".equals(operation)){
                sysLog.setOperation(sysLogger.value());
            }else {
                sysLog.setOperation(methodName);
            }
        }

        sysLog.setMethod(className+"."+methodName);
        //请求参数
        Object[] args = joinPoint.getArgs();
        String params = "";
        for (Object o : args){
            params += new Gson().toJson(o);
        }
        if(params == null || "".equals(params)){
            System.out.println("参数为空。");
        }else {
            sysLog.setParams(params);
        }
        sysLog.setIp(HttpUtils.getIpAddress());
        Date time = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        sysLog.setCreateDate(format.format(time));
        System.out.println(new Gson().toJson(sysLog));
        sysLog.log(amqpTemplate);
    }
}
