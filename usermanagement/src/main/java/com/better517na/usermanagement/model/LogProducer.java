package com.better517na.usermanagement.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class LogProducer {
    @Autowired
    private KafkaTemplate kafkaTemplate;
    public void sendLog(String topicName,String msg){
        kafkaTemplate.send(topicName,msg);
    }
}
