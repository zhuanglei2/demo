package com.zl.demo.aop;

import java.lang.reflect.Method;

/**
 * @Author zhuanglei
 * @Date 2021/6/1 5:12 下午
 * @Version 1.0
 */
public class BeforeAdvice implements Advice {

    private Object obj;
    private MethodInvocationHandler methodInvocationHandler;

    BeforeAdvice(Object obj,MethodInvocationHandler methodInvocationHandler){
        this.obj = obj;
        this.methodInvocationHandler = methodInvocationHandler;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        methodInvocationHandler.invoke();
        return method.invoke(obj,args);
    }
}
