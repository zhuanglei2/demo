package com.zl.demo.thread.threadLocal;

/**
 * 父子线程共享
 * @Author zhuanglei
 * @Date 2021/6/1 11:42 上午
 * @Version 1.0
 */
public class InheritThreadLocalTest {
    public static void main(String[] args) {
        ThreadLocal threadLocal = new InheritableThreadLocal();
        threadLocal.set("加油");
        Thread t = new Thread(){
            @Override
            public void run() {
                super.run();
                System.out.println("threadLocal:"+threadLocal.get());
            }
        };
        t.start();
    }
}
