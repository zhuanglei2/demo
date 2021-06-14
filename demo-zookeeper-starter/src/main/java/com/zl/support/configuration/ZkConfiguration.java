package com.zl.support.configuration;

import com.zl.support.component.ZkComponent;
import com.zl.support.properties.ZkProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author zhuanglei
 * @Date 2021/6/13 10:02 下午
 * @Version 1.0
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(ZkProperties.class)
public class ZkConfiguration {

    private final ZkProperties zkProperties;

    @Bean
    @ConditionalOnMissingBean(CuratorFramework.class)
    public CuratorFramework initClient(){
        log.info("zk客户端启动开始...");
        CuratorFramework client = CuratorFrameworkFactory
                .builder()
                .connectString(zkProperties.getAddress())
                .sessionTimeoutMs(zkProperties.getTimeout())
                .retryPolicy(new ExponentialBackoffRetry(zkProperties.getBaseSleepMs(), zkProperties.getRetryCount()))
                .build();
        log.info("zk客户端启动完成...");
        client.start();
        return client;
    }

    @Bean
    @ConditionalOnMissingBean(ZkComponent.class)
    public ZkComponent zkComponent(CuratorFramework client){
        return new ZkComponent(client);
    }
}
