package com.zl.demo.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁的实现
 * @author zhuangl
 * @version 1.0
 * @date 2020/1/9 19:27
 */
public class MyFairLock {

    /** ture是公平锁 不填参数是非公平锁 */
    private ReentrantLock lock = new ReentrantLock(true);
    public void fairLock(){
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName()+"正在持有锁");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println(Thread.currentThread().getName()+"释放了锁");
            lock.unlock();
        }
    }


    public static void main(String[] args) {
        MyFairLock myFairLock = new MyFairLock();
        Runnable runnable = () ->{
            System.out.println(Thread.currentThread().getName()+"启动");
            myFairLock.fairLock();
        };

        Thread[] threads = new Thread[10];
        for (int i=0;i<10;i++){
            threads[i] = new Thread(runnable);
        }

        for(int i = 0;i < 10;i++) {
            threads[i].start();
        }
    }
}
