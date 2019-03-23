package com.better517na.logservice;

import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class LogserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogserviceApplication.class, args);
    }



}
