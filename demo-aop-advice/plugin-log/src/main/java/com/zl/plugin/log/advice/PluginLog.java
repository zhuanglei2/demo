package com.zl.plugin.log.advice;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/3/19 15:45
 */
public class PluginLog implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        System.out.println("log");
    }

    public static void main(String[] args) {
        System.out.println("日志");
    }

    public PluginLog(){

    }
}
