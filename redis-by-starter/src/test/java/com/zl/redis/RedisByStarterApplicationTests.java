package com.zl.redis;

import com.zl.support.component.RedisComponent;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class RedisByStarterApplicationTests {

    @Autowired
    private RedisComponent redisComponent;

    @Test
    void contextLoads() {
        String key = "redisTest";
        String value = "this is a test value";
        boolean success = redisComponent.set(key, value, 3600);
        log.info("set value to redis {}!", success ? "success" : "failed");
        String result = redisComponent.get(key, String.class);
        log.info("get value from redis: [{}]", result);
    }

}
