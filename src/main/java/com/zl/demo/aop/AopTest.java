package com.zl.demo.aop;

/**
 * @Author zhuanglei
 * @Date 2021/6/1 5:17 下午
 * @Version 1.0
 */
public class AopTest {

    public static void main(String[] args) {
        MethodInvocationHandler methodInvocationHandler = ()->{
            System.out.println("记录日志");
        };
        HellowServiceImpl hellowService = new HellowServiceImpl();
        BeforeAdvice beforeAdvice = new BeforeAdvice(hellowService,methodInvocationHandler);
        HellowService o = (HellowService) JDKProxy.newInstance(hellowService, beforeAdvice);
        o.say();
    }
}
