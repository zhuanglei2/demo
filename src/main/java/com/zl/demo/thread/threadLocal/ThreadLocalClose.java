package com.zl.demo.thread.threadLocal;

/**
 * 为了保证能释放ThreadLocal关联的实例，我们可以通过AutoCloseable接口配合try (resource) {...}结构，让编译器自动为我们关闭
 * @author zhuangl
 * @version 1.0
 * @date 2021/1/20 14:37
 */
public class ThreadLocalClose implements AutoCloseable {

    static final ThreadLocal<String> ctx = new ThreadLocal<>();

    public ThreadLocalClose(String user){
        ctx.set(user);
    }

    public static String currentUser(){
        return ctx.get();
    }


    @Override
    public void close() throws Exception {
        ctx.remove();
    }
}
