package com.zl.demo.datasource.annotation;

import java.lang.annotation.*;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/3/11 9:57
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DefaultDataSource {
}
