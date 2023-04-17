package com.self.codemaker.mq.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author huangchangjun
 * @date 2023/4/16
 */
@Slf4j
@Component
public class KafkaConsumer {

    //  不指定group，默认取yml里配置的
    @KafkaListener(topics = {"codemaker"})
    public void onMessage1(ConsumerRecord<?, ?> consumerRecord) {
        Optional<?> optional = Optional.ofNullable(consumerRecord.value());
        if (optional.isPresent()) {
            Object msg = optional.get();
            log.info("message:{}", msg);
        }
    }
}
