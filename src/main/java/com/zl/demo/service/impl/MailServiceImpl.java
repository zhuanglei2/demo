package com.zl.demo.service.impl;

import com.zl.demo.dto.MailRequest;
import com.zl.demo.service.MailService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/1/2 14:31
 */
@Service
public class MailServiceImpl implements MailService {


    /**
     * 异步发送邮件
     * @param req
     */
    @Override
    @Async
    public void sendMail(MailRequest req) {
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("发送邮件成功");
        System.out.println("耗时："+(System.currentTimeMillis()-start)+"秒");
    }
}
