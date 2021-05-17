package com.zl.demo.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/5/17 17:05
 */
public class CGlibAgent implements MethodInterceptor {

    private Object proxy;

    public Object getInstance(Object o){
        this.proxy = o;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.proxy.getClass());
        //回调方法
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println(">>>>> before invoking");

        Object invoke = methodProxy.invokeSuper(o, objects);

        System.out.println(">>>>> after invoking");
        return invoke;
    }

    public static void main(String[] args) {
        CGlibAgent cGlibAgent = new CGlibAgent();
        IHello instance = (IHello) cGlibAgent.getInstance(new HelloServiceImpl());
        System.out.println(instance.say("123"));
    }
}
