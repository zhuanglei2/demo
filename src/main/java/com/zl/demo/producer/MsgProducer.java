package com.zl.demo.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MsgProducer implements RabbitTemplate.ConfirmCallback {

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public MsgProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this);
    }


    public void sendMsg(String content){
        rabbitTemplate.convertAndSend("szRabbitExchange","sz.queues.sms.server.sendQueue.key",content);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("回掉Id:"+correlationData);
        if(ack){
            log.info("消息成功消费");
        }else{
            log.info("消息消费失败:"+cause);
        }
    }
}
