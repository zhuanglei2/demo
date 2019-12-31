package com.zl.demo.controller;

import com.rabbitmq.tools.json.JSONUtil;
import com.zl.demo.producer.MsgProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2019/11/22 15:28
 */
@RestController
@RequestMapping("/msg")
public class MsgControlller {

    @Autowired
    private MsgProducer msgProducer;


    @RequestMapping("/send")
    public String sendMsg(@RequestParam String content){
        msgProducer.sendMsg(content);
        String msg = "消息发送成功！";
        return msg;
    }
}
