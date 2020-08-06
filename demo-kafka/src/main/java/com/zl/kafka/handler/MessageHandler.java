package com.zl.kafka.handler;

import com.zl.kafka.constants.KafkaConsts;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/8/6 19:44
 */
@Component
@Slf4j
public class MessageHandler {

    /**
     * topics为主题列表
     * @param record
     * @param acknowledgment
     */
    @KafkaListener(topics = KafkaConsts.TOPIC_TEST,containerFactory = "ackContainerFactory")
    public void handleMessage(ConsumerRecord record, Acknowledgment acknowledgment){
        try {
            String message = (String) record.value();
            log.info("收到消息: {}", message);
        }catch (Exception e){
            log.error(e.getMessage(), e);
        } finally {
            // 手动提交 offset
            acknowledgment.acknowledge();
        }
    }
}
