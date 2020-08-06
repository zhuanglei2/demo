package com.zl.demo.proxy;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/8/5 20:50
 */
public class Test {
    public static void main(String[] args) {
        IHello hello = FacadeProxy.newMapperProxy(IHello.class);
        System.out.println(hello.say("hello world"));
    }
}
