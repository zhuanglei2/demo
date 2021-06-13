package com.zl.demo.thread.parent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1.父线程能控制子线程吗
 * @Author zhuanglei
 * @Date 2021/6/13 10:13 上午
 * @Version 1.0
 */
public class ParentThreadKidsTest {

    public static void main(String[] args) {
        Thread thread = new Thread(new Parent());
        thread.start();
    }

}

class Parent implements Runnable{


    @Override
    public void run() {
        Thread kid = new Thread(new Kid());
        kid.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        kid.interrupt();
    }
}

class Kid implements Runnable{



    @Override
    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
        while (true){
            if(Thread.currentThread().isInterrupted()){
                System.out.println("线程中断了");
                break;
            }
            System.out.println(sdf.format(new Date())+":"
                    +Thread.currentThread().getName()+"正在运行！");
        }
    }
}
