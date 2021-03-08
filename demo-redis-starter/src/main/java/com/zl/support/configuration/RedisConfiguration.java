package com.zl.support.configuration;

import com.zl.support.component.RedisComponent;
import com.zl.support.properties.RedisProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/3/4 14:22
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfiguration {
    private final RedisProperties properties;


    @Bean
    // 表示当Spring容器中没有JedisPool类的对象时，才调用该方法
    @ConditionalOnMissingBean(JedisPool.class)
    public JedisPool jedisPool(){
        log.info("redis connect string:{}:{}",properties.getHost(),properties.getPort());
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(properties.getMaxIdle());
        jedisPoolConfig.setMaxTotal(properties.getMaxTotal());
        jedisPoolConfig.setMaxWaitMillis(properties.getMaxWaitMills());

        String password = properties.getPassword();
        if (password == null || password.length() == 0) {
            return new JedisPool(jedisPoolConfig, properties.getHost(),
                    properties.getPort(), properties.getTimeout());
        }

        return new JedisPool(jedisPoolConfig, properties.getHost(),
                properties.getPort(), properties.getTimeout(), properties.getPassword());
    }

    @Bean
    @ConditionalOnMissingBean(RedisComponent.class)
    public RedisComponent redisComponent(JedisPool jedisPool){
        return new RedisComponent(jedisPool);
    }
}
