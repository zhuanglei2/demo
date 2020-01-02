package com.zl.demo.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/1/2 14:24
 */
@Data
@ToString
public class MailRequest {

    private String mail;

    private String content;
}
