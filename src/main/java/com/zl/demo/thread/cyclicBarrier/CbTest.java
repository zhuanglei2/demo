package com.zl.demo.thread.cyclicBarrier;

import java.util.concurrent.CyclicBarrier;

/**
 * 某个线程到达预设点时就在此等待，等所有的线程都到达时，大家再一起向下个预设点出发。如此循环反复下去。
 * CyclicBarrier 测试类 问题：CyclicBarrier和CountDownLatch区别
 * @author zhuangl
 * @version 1.0
 * @date 2021/2/24 11:57
 */
public class CbTest {
    static final int COUNT = 5;
    static CyclicBarrier cb = new CyclicBarrier(COUNT,new Singer());

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < COUNT; i++) {
            new Thread(new Staff(i, cb)).start();
        }
        synchronized (CbTest.class) {
            CbTest.class.wait();
        }
    }

    static class Singer implements Runnable{

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"开始休息");
        }
    }

    static class Staff implements Runnable{

        CyclicBarrier cb;
        int num;

        Staff(int num,CyclicBarrier cb){
            this.num = num;
            this.cb = cb;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"员工"+num+"出发");
            doingLongTime();
            System.out.println(Thread.currentThread().getName()+"员工"+num+"到达地点一");
            try{
                cb.await();
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"员工"+num+"从地点一出发");
            doingLongTime();
            System.out.println(Thread.currentThread().getName()+"员工"+num+"到达地点二");
            try{
                cb.await();
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"员工"+num+"从地点二出发");
            doingLongTime();
            System.out.println(Thread.currentThread().getName()+"员工"+num+"到达地点三");
            try{
                cb.await();
            }catch (Exception e){
                e.printStackTrace();
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
