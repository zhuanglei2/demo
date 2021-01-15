package com.zl.demo.thread.twoThread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock版本循环打印
 * @author zhuangl
 * @version 1.0
 * @date 2021/1/14 11:05
 */
public class TwoThreadReentrantLock {
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    public static void main(String[] args) {
        IsNum isNum = new IsNum(false);
        Thread a = new Thread(new A(isNum,"打印数字"));
        Thread b = new Thread(new B(isNum,"打印字母"));
        a.start();
        b.start();
    }

    static class A implements Runnable{
        private IsNum isNum;
        private String name;
        A(IsNum isNum,String name){
            this.isNum = isNum;
            this.name = name;
        }

        @Override
        public void run() {
            while (true){
                lock.lock();
                try {
                    if(!isNum.flag){
                        System.out.println(name+"： "+isNum.numberPrint);
                        isNum.numberPrint++;
                        isNum.flag = true;
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        condition.signalAll();
                    }else {
                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }finally {
                    lock.unlock();
                }
            }
        }
    }

    static class B implements Runnable{
        private IsNum isNum;
        private String name;
        B(IsNum isNum,String name){
            this.isNum = isNum;
            this.name = name;
        }

        @Override
        public void run() {
            while (true){
                lock.lock();
                try {
                    if(isNum.flag){
                        System.out.println(name+"： "+(char)(96+isNum.wordPrin));
                        isNum.wordPrin++;
                        isNum.flag = false;
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        condition.signalAll();
                    }else {
                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }finally {
                    lock.unlock();
                }
            }
        }
    }
}
