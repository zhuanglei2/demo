package com.zl.demo.service;

import com.zl.demo.dto.MailRequest;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/1/2 14:30
 */
public interface MailService {
    void sendMail(MailRequest req);
}
