package com.zl.limit.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 限流注解，添加了 {@link AliasFor} 必须通过 {@link AnnotationUtils} 获取，才会生效
 * @author zhuangl
 * @version 1.0
 * @date 2020/8/7 9:35
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {
    /**
     * 默认请求
     */
    long DEFAULT_REQUEST = 10;

    /**
     * max 最大请求数
     * @return
     */
    @AliasFor("max")
    long value() default DEFAULT_REQUEST;

    /**
     * max 最大请求数
     * @return
     */
    @AliasFor("value")
    long max() default  DEFAULT_REQUEST;

    /**
     * 限流key
     */
    String key() default "";

    /**
     * 超时时长，默认1分钟
     */
    long timeout() default 1;

    /**
     * 超时时间单位，默认 分钟
     */
    TimeUnit timeUnit() default TimeUnit.MINUTES;
}
