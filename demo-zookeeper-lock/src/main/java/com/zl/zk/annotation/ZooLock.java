package com.zl.support.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 基于Zookeeper的分布式锁注解
 * @author zhuangl
 * @version 1.0
 * @date 2020/9/11 11:29
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ZooLock {

    /**
     * 分布式锁的键
     * @return
     */
    String key();

    /**
     * 锁释放时间，默认5秒
     * @return
     */
    long timeout() default 5*1000;

    /**
     * 时间格式，默认毫秒
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}
