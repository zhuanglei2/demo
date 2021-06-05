package com.zl.demo.aop;

import java.lang.reflect.Proxy;

/**
 * @Author zhuanglei
 * @Date 2021/6/1 5:15 下午
 * @Version 1.0
 */
public class JDKProxy {

    public static Object newInstance(Object object,Advice advice){
        return Proxy.newProxyInstance(JDKProxy.class.getClassLoader(),object.getClass().getInterfaces(),advice);
    }
}
