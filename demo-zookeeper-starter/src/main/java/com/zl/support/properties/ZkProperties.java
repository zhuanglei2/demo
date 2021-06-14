package com.zl.support.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author zhuanglei
 * @Date 2021/6/13 10:02 下午
 * @Version 1.0
 */
@Data
@ConfigurationProperties("com.zl.zk")
public class ZkProperties {

    /**
     * 地址
     */
    private String address;

    /**
     * 超时时间
     */
    private int timeout;

    /**
     * 重试间隔时间
     */
    private int baseSleepMs;

    /**
     * 重试次数
     */
    private int retryCount;
}
