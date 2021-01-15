package com.zl.demo.thread.reentranLock.readWriteLock;


import com.alibaba.fastjson.JSON;
import lombok.var;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/1/13 20:41
 */
public class Counter {
    private final ReadWriteLock rwlock = new ReentrantReadWriteLock();
    private final Lock rLock = rwlock.readLock();
    private final Lock wLock = rwlock.writeLock();
    private int[] counts = new int[10];


    public void inc(int index) {
        //加写锁
        wLock.lock();
        try {
            counts[index] += 1;
        } finally {
            wLock.unlock();
        }
    }

    public int[] get() {
        //加读锁
        rLock.lock();
        try {
            return Arrays.copyOf(counts, counts.length);
        } finally {
            rLock.unlock();
        }
    }

    public static void main(String[] args) {
        var c = new Counter();
        //多线程写
        var t = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("写入数据,i: "+i);
                c.inc(i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        //多线程读
        for (int i = 0; i < 10; i++) {
            var a = new Thread(() -> {
                int[] ints = c.get();
                System.out.println(Thread.currentThread().getName()+"counts:" + JSON.toJSONString(ints));
            });
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            a.start();
        }
    }
}
