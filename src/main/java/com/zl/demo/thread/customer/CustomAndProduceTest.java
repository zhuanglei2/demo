package com.zl.demo.thread.customer;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者消费者问题
 * @author zhuangl
 * @version 1.0
 * @date 2021/2/24 16:21
 */
@Slf4j
public class CustomAndProduceTest {


    public static void main(String[] args) {
        Queue queue = new Queue();
        new Thread(new Producer(queue)).start();
        new Thread(new Producer(queue)).start();
        new Thread(new Customer(queue)).start();
    }

    /**
     * 生产者
     */
    static class Producer implements Runnable{

        Queue queue;

        Producer(Queue queue){
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 10000; i++) {
                    doingLongTime();
                    queue.putEle(RandomUtil.randomInt(10000));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 消费者
     */
    static class Customer implements Runnable{

        Queue queue;

        Customer(Queue queue){
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 10000; i++) {
                    doingLongTime();
                    queue.takeEle();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    static class Queue{

        Lock lock = new ReentrantLock();
        Condition prodCond = lock.newCondition();
        Condition customCond = lock.newCondition();

        int CAP = 10;
        Object[] container = new Object[CAP];
        int count = 0;
        int takeIndex = 0;
        int putIndex = 0;

        public void putEle(Object obj) throws InterruptedException {
            try {
                lock.lock();
                while (count == CAP){
                    log.info("生产队列已满,生产者挂起");
                    prodCond.await();
                }
                container[putIndex] = obj;
                log.info("生产商品：{}",container[putIndex]);
                putIndex++;
                if(putIndex>=CAP){
                    putIndex = 0;
                }
                count++;
                customCond.signalAll();
            }finally {
                lock.unlock();
            }
        }

        public Object takeEle() throws InterruptedException {
            try {
                lock.lock();
                while (count == 0){
                    log.info("生产队列为空,消费者挂起，等待生产");
                    customCond.await();
                }
                Object obj = container[takeIndex];
                log.info("消费商品：{}",obj);
                takeIndex++;
                if(takeIndex>=CAP){
                    takeIndex = 0;
                }
                count--;
                prodCond.signalAll();
                return obj;
            }finally {
                lock.unlock();
            }
        }
    }


    static void doingLongTime(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
