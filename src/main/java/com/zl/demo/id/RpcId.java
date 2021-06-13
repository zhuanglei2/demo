package com.zl.demo.id;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 高并发情况下的随机数
 * @Author zhuanglei
 * @Date 2021/6/6 8:51 下午
 * @Version 1.0
 */
public class RpcId {
    public static void main(String[] args) {
        Random random = new Random();
        //线程安全 但并不是高并发
        System.out.println(        random.nextInt(10));
        //引用如下概念
        System.out.println(ThreadLocalRandom.current().nextInt(10));

    }
}
