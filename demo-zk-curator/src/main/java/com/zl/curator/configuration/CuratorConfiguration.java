package com.zl.curator.configuration;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/3/17 17:22
 */
@Configuration
public class CuratorConfiguration {
    final String connectString = "127.0.0.1:2181";

    @Bean
    public CuratorFramework curatorFramework(){
        // 重试策略，初始化每次重试之间需要等待的时间，基准等待时间为1秒。
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory
                .builder()
                .connectString(connectString)
                .connectionTimeoutMs(15*1000)
                .sessionTimeoutMs(60 * 100)
                .retryPolicy(retryPolicy)
                .build();
        client.start();
        return client;
    }
}
