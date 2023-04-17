package com.self.codemaker.mq.kafka.producer;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author huangchangjun
 * @date 2023/4/16
 */
@Slf4j
@Service
public class KafkaProducer implements ListenableFutureCallback<SendResult> {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    private static String TOPIC = "codemaker";

    /**
     * 使用kafka async异步发送，实现自动重发
     *
     * @param msg
     */
    public void sendMessage(String msg) {
        log.info("kafka topic = {} , msg = {}", TOPIC, msg);
        kafkaTemplate.send(TOPIC, JSON.toJSONString(msg)).addCallback(new KafkaProducer());
    }

    @Override
    public void onFailure(Throwable ex) {
        log.error("send kafka failed: {}", ex);
    }

    @Override
    public void onSuccess(SendResult result) {
        log.info("send kafka success: {}", result.getProducerRecord());
    }
}
