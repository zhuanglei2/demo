package com.zl.demo.thread.countDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/2/24 10:23
 */
public class CdlTest {

    static final int COUNT = 20;
    static CountDownLatch cdl = new CountDownLatch(COUNT);

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Teacher(cdl)).start();
        doingLongTime(1);
        for (int i = 0; i < COUNT; i++) {
            new Thread(new Student(i,cdl)).start();
        }
        synchronized (CdlTest.class){
            CdlTest.class.wait();
        }
    }



    static class Teacher implements Runnable{

        CountDownLatch cdl;

        Teacher(CountDownLatch cdl){
            this.cdl = cdl;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"老师发卷子");
            try {
                cdl.await();
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"老师收卷子。。。");
        }
    }

    static class Student implements Runnable{

        int number;
        CountDownLatch cdl;

        Student(int number,CountDownLatch cdl){
            this.number = number;
            this.cdl = cdl;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"学生"+number+"做试卷");
            doingLongTime(1);
            cdl.countDown();
            System.out.println(Thread.currentThread().getName()+"学生"+number+"做完了试卷");
        }
    }

    static void doingLongTime(int time){
        try {
            Thread.sleep(time*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
