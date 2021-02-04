package com.zl.demo;

import com.zl.demo.chat.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 生成二维码
 * @author zhuangl
 * @version 1.0
 * @date 2020/11/6 15:43
 */
@Slf4j
@SpringBootTest
public class ChatTest {
    @Autowired
    private ChatService chatService;

    /**
     * 获取token
     */
    @Test
    public void getToken(){
        log.info("{}",chatService.getToken("wxe2118bb21023ae39","ebb2af9fe8d21c869e8bafae88cd9f23"));
    }

    /**
     * 根据token获取ticket
     */
    @Test
    public void create(){
        chatService.create("39_sVxy5aF7XwfaI3xnTmUFQEyU8nU-55hRoj46bHBeqJgw1KDsPIcIeH95qPq5ATTCXtjqBM3pYDs0qkxtZy_Bj7jbsyzAwRWuFu9-Quy1tfXoMLOw3MTixmZaKlYOlaMrRLj6ox-3_uNA8RvCIKJhAIABDC");
    }

    /**
     * 根据ticket生成二维码
     */
    @Test
    public void getImg(){
        chatService.getImg("gQEV8TwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyR0xZczlqOTRkVUUxUjlNZ2h2MUEAAgRJ46hfAwQAjScA");
    }

    /**
     * 自定义二维码生成
     */
    @Test
    public void createQrImg(){
        chatService.createQrImg("https://m.shanzhen.me/ess/wap/index");
    }
}
