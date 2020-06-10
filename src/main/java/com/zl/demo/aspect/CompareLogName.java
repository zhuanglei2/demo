package com.zl.demo.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志对比注解
 * @author zhuangl
 * @version 1.0
 * @date 2020/5/14 9:04
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CompareLogName {
    /**
     * 对比字段日志名称
     * @return
     */
    String logName() default "";
}
