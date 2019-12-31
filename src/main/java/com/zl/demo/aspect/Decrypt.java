package com.zl.demo.aspect;

import java.lang.annotation.*;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2019/12/31 14:48
 */
@Target({ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Decrypt {

}
