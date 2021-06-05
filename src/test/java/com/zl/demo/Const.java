package com.zl.demo;

/**
 * @Author zhuanglei
 * @Date 2021/4/19 8:35 下午
 * @Version 1.0
 */
public class Const {
//    final static int a =1;
    static {
        //常量在编译阶段会存入调用类的常量池中，本质上并没有直接引用到定义常量的类，因此不会触发定义常量的类的初始化
        System.out.println("Const init..");
//        System.out.println(a);
    }

    public static final String HELLO_WORLD="hello world";



}
