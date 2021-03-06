package com.better517na.usermanagement.controller;

import com.better517na.usermanagement.Annotation.SysLogger;
import com.better517na.usermanagement.config.RabbitConfig;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.better517na.usermanagement.feignClient.TestFeignClient;

/**
 * @author zhuojiu
 * @description:
 * @date 2019-02-26 17:11
 */
@RestController
public class testController {
    @Autowired
    TestFeignClient feignClient;
    @Autowired
    private AmqpTemplate amqpTemplate;

    @SysLogger
    @HystrixCommand(fallbackMethod = "testFallback")
    @GetMapping("/test")
    public void test(){
        System.out.println("---test执行啦---");

        feignClient.test();
    }

    public void testFallback(){
        System.out.println("---testFallback执行啦---");
    }

}
