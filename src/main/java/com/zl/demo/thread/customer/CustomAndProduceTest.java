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
        new Thread(new Consumer(queue)).start();
    }

    static class Producer implements Runnable {

        Queue queue;

        Producer(Queue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 10000; i++) {
                    doingLongTime();
                    queue.putEle(RandomUtil.randomInt(10000));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    static class Consumer implements Runnable {

        Queue queue;

        Consumer(Queue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 10000; i++) {
                    doingLongTime();
                    queue.takeEle();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    static class Queue {
        Lock lock = new ReentrantLock();
        Condition prodCond  = lock.newCondition();
        Condition consCond = lock.newCondition();

        final int CAPACITY = 10;
        Object[] container = new Object[CAPACITY];
        int count = 0;
        int putIndex = 0;
        int takeIndex = 0;

        public void putEle(Object ele) throws InterruptedException {
            try {
                lock.lock();
                while (count == CAPACITY) {
                    log.info("队列已满：{}，生产者开始睡大觉。。。", count);
                    prodCond.await();
                }
                container[putIndex] = ele;
                log.info("生产元素：{}", ele);
                putIndex++;
                if (putIndex >= CAPACITY) {
                    putIndex = 0;
                }
                count++;
                log.info("通知消费者去消费。。。");
                consCond.signalAll();
            } finally {
                lock.unlock();
            }
        }

        public Object takeEle() throws InterruptedException {
            try {
                lock.lock();
                while (count == 0) {
                    log.info("队列已空：{}，消费者开始睡大觉。。。", count);
                    consCond.await();
                }
                Object ele = container[takeIndex];
                log.info("消费元素：{}", ele);
                takeIndex++;
                if (takeIndex >= CAPACITY) {
                    takeIndex = 0;
                }
                count--;
                log.info("通知生产者去生产。。。");
                prodCond.signalAll();
                return ele;
            } finally {
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
