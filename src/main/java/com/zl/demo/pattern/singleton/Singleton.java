package com.zl.demo.pattern.singleton;

/**
 * 单例模式
 * 双检锁/双重校验锁（DCL，即 double-checked locking）
 * @author zhuangl
 * @version 1.0
 * @date 2020/6/5 14:47
 */
public class Singleton {
    private volatile static Singleton singleton;
    private Singleton(){};
    public static Singleton getSingleton(){
        if(singleton==null){
            synchronized (Singleton.class){
                singleton = new Singleton();
            }
        }
        return singleton;
    }
}
