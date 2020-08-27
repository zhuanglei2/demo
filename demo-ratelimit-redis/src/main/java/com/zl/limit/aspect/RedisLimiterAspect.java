package com.zl.limit.aspect;

import cn.hutool.core.util.StrUtil;
import com.zl.limit.annotation.RateLimit;
import com.zl.limit.util.IpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * 限流切面
 * @author zhuangl
 * @version 1.0
 * @date 2020/8/27 9:51
 */
@Slf4j
@Aspect
@Component
//利用Lombok编写优雅的spring依赖注入代码,去掉繁人的@Autowired 这里必须是final,若不使用final,用@NotNull注解也是可以的
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RedisLimiterAspect {
    private final static String SEPARATOR = ":";
    private final static String REDIS_LIMIT_KEY_PREFIX = "limit:";
    private final StringRedisTemplate stringRedisTemplate;
    private final RedisScript<Long> limitRedisScript;

    @Pointcut("@annotation(com.zl.limit.annotation.RateLimit)")
    public void rateLimit(){

    }

    @Around("rateLimit()")
    public Object pointcut(ProceedingJoinPoint point) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        // 通过 AnnotationUtils.findAnnotation 获取 RateLimiter 注解
        RateLimit rateLimit = AnnotationUtils.findAnnotation(method, RateLimit.class);
//        if(method.isAnnotationPresent(RateLimit.class)){
//            final RateLimit rateLimit = method.getAnnotation(RateLimit.class);
            if(rateLimit!=null){
                String key = rateLimit.key();
                //默认方法用类+方法名做限流 key前缀
                if(StrUtil.isBlank(key)){
                    key = method.getDeclaringClass().getName()+StrUtil.DOT+method.getName();
                }
                //最终key 为前缀+地址
                // TODO: 此时需要考虑局域网多用户访问的情况，因此 key 后续需要加上方法参数更加合理
                key = key + SEPARATOR + IpUtil.getIpAddr();
                long max = rateLimit.max();
                long timeout = rateLimit.timeout();
                TimeUnit timeUnit =rateLimit.timeUnit();
                boolean limited = shouldLimited(key, max, timeout, timeUnit);
                if (limited) {
                    throw new RuntimeException("手速太快了，慢点儿吧~");
                }
            }
//        }
        return point.proceed();
    }

    private boolean shouldLimited(String key, long max, long timeout, TimeUnit timeUnit) {
        // 最终的 key 格式为：
        // limit:自定义key:IP
        // limit:类名.方法名:IP
        key = REDIS_LIMIT_KEY_PREFIX + key;
        //统一使用单位毫秒
        long ttl = timeUnit.toMillis(timeout);
        //当前时间的毫秒数
        long now = Instant.now().toEpochMilli();
        long expired = now - ttl;
        Long executeTimes = stringRedisTemplate.execute(limitRedisScript, Collections.singletonList(key), now + "", ttl + "", expired + "", max + "");
        if (executeTimes != null) {
            if (executeTimes == 0) {
                log.error("【{}】在单位时间 {} 毫秒内已达到访问上限，当前接口上限 {}", key, ttl, max);
                return true;
            } else {
                log.info("【{}】在单位时间 {} 毫秒内访问 {} 次", key, ttl, executeTimes);
                return false;
            }
        }
        return false;
    }
}
