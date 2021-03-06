package com.zl.support.component;

import com.google.gson.Gson;
import com.zl.support.properties.RedisProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/3/4 14:19
 */
@Slf4j
@RequiredArgsConstructor
public class RedisComponent {
    private final JedisPool jedisPool;

    /**
     * get value with key
     */
    public <T> T get(String key, Class<T> clazz) {
        try (Jedis resource = jedisPool.getResource()) {
            String str = resource.get(key);

            return stringToBean(str, clazz);
        }
    }

    /**
     * set value with key
     */
    public <T> boolean set(String key, T value, int expireSeconds) {
        try (Jedis resource = jedisPool.getResource()) {
            String valueStr = beanToString(value);
            if (valueStr == null || valueStr.length() == 0) {
                return false;
            }

            if (expireSeconds <= 0) {
                resource.set(key, valueStr);
            } else {
                resource.setex(key, expireSeconds, valueStr);
            }

            return true;
        }
    }

    /**
     * bitmap set
     * @param key
     * @param offset
     * @param flag
     * @return
     */
    public boolean setBit(String key,Long offset,Boolean flag){
        try(Jedis jedis = jedisPool.getResource()){
            jedis.setbit(key,offset,flag);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean getBit(String key,Long offset){
        try(Jedis jedis = jedisPool.getResource()){
            Boolean flag = jedis.getbit(key, offset);
            return flag;
        }
    }

    /**
     * 查询位图数量
     * @param key
     * @return
     */
    public Long getBitCount(String key){
        try(Jedis jedis = jedisPool.getResource()){
            return jedis.bitcount(key);
        }
    }

    private <T> T stringToBean(String str, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(str, clazz);
    }

    private <T> String beanToString(T value) {
        Gson gson = new Gson();
        return gson.toJson(value);
    }
}
