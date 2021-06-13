package com.zl.support.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/3/4 14:13
 */
@Data
@ConfigurationProperties("com.zl.redis")
public class RedisProperties {
    private String host = "127.0.0.1";

    private int port = 6379;

    private int timeout = 2000;

    private int maxIdle = 5;

    private int maxTotal = 10;

    private long maxWaitMills = 10000;

    private String password;
}
