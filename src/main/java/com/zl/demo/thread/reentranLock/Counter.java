package com.zl.demo.thread.reentranLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/1/13 20:05
 */
public class Counter {
    /**
     * Java代码实现的锁，我们就必须先获取锁，然后在finally中正确释放锁
     */
    private final Lock lock = new ReentrantLock();

    private int count;

    public void add(int n) throws InterruptedException {
        lock.lock();
        try{
            count+=n;
        }catch (Exception e){

        }finally {
            lock.unlock();
        }

        //在尝试获取锁的时候，最多等待1秒。如果1秒后仍未获取到锁，tryLock()返回false，程序就可以做一些额外处理，而不是无限等待下去
//        if (lock.tryLock(1, TimeUnit.SECONDS)) {
//            try {
//            } finally {
//                lock.unlock();
//            }
//        }
    }
}
