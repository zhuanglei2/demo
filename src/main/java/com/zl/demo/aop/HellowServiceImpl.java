package com.zl.demo.aop;

/**
 * @Author zhuanglei
 * @Date 2021/6/1 5:16 下午
 * @Version 1.0
 */
public class HellowServiceImpl implements HellowService{
    @Override
    public void say() {
        System.out.println("HellowServiceImpl : say");
    }
}
