package com.zl.log.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/8/6 20:34
 */
@RestController
public class TestController {
    /**
     * 测试方法
     *
     * @param who 测试参数
     * @return {@link String}
     */
    @GetMapping("/test")
    public String test(String who) {
        return who;
    }
}
