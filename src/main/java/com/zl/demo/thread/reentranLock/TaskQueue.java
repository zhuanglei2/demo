package com.zl.demo.thread.reentranLock;

import lombok.var;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 用ReentrantLock编写wait和notify的功能
 * @author zhuangl
 * @version 1.0
 * @date 2021/1/13 20:15
 */
public class TaskQueue {
    private final Lock lock = new ReentrantLock();
    /**
     * 引用的Condition对象必须从Lock实例的newCondition()返回，这样才能获得一个绑定了Lock实例的Condition实例
     */
    private final Condition condition = lock.newCondition();
    private Queue<String> queue = new LinkedList<>();

    public void addTask(String s){
        lock.lock();
        try {
            queue.add(s);
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }

    public String getTask(){
        lock.lock();
        try {
            while (queue.isEmpty()){
                condition.await();
            }
            return queue.remove();
//            if(condition.await(1,TimeUnit.SECONDS)){
//                System.out.println("被其他线程唤醒");
//            }else {
//                System.out.println("超时");
//            }
        }catch (Exception e){
            System.out.println(e);
        }finally {
            lock.unlock();
        }
        return null;
    }

    public static void main(String[] args) throws InterruptedException {
        var r = new TaskQueue();
        var ts = new LinkedList<Thread>();
        for (int i = 0; i < 5; i++) {
            var t = new Thread(()->{
                while (true){
                    final String task = r.getTask();
                    System.out.println("taskQueue task : "+task);
                }
            });
            t.start();
            ts.add(t);
        }

        var t = new Thread(()->{
            for (int i = 0; i < 10; i++) {
                String s = "t-"+UUID.randomUUID().toString();
                r.addTask(s);
                System.out.println("taskQueue add task : "+s);
            }
        });
        t.start();
        t.join();
        Thread.sleep(100);
        for (Thread a:ts){
            a.interrupt();
        }
    }
}
