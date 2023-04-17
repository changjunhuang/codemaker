package com.self.codemaker.controller;

import com.self.codemaker.annotation.MethodLog;
import com.self.codemaker.mq.kafka.producer.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huangchangjun
 * @date 2023/4/16
 */
@Slf4j
@RestController
public class KafkaController {

    @Autowired
    KafkaProducer kafkaProducer;

    @MethodLog
    @PostMapping("/sendMsgByKafka")
    public void putStrToRedis(String val) {
        log.info("kafka controller start , info : {}",val);
        //  kafka消息生产
        kafkaProducer.sendMessage(val);
        log.info("kafka controller end");
    }
}
