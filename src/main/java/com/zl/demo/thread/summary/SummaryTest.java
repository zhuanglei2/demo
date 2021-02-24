package com.zl.demo.thread.summary;

/**
 * 线程总结测试类
 * @author zhuangl
 * @version 1.0
 * @date 2021/2/24 9:22
 */
public class SummaryTest {

    public static void main(String[] args) {
//        stopByFlay();
//        pauseByFalg();
//        jqByJoin();
        stopByInterrupt();
    }

    /**
     * 场景一：停止
     */
    static void stopByFlay(){
        ARunnable aRunnable = new ARunnable();
        new Thread(aRunnable).start();

        aRunnable.tellToSleep();
    }


    /**
     * 场景二：暂停/恢复
     */
    static void pauseByFalg(){
        BRunnable br = new BRunnable();
        new Thread(br).start();
        br.tellToPause();
        sleep(8);
        br.tellToResume();
    }

    /**
     * 场景三：插队
     */
    static void jqByJoin(){
        CRunnable cr = new CRunnable();
        Thread t = new Thread(cr);
        t.start();
        sleep(1);
        try {
            System.out.println("插队开始");
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("终于轮到我了");
    }

    /**
     * 场景四：中断
     */
    static void stopByInterrupt(){
        DRunnable dr = new DRunnable();
        Thread t = new Thread(dr);
        t.start();
        sleep(1);
        t.interrupt();
    }



    static class DRunnable implements Runnable{
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"工作开始");
            try {
                sleep2(5);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName()+"收到中断请求");
            }
            System.out.println(Thread.currentThread().getName()+"工作完成");
        }
    }

    static class CRunnable implements Runnable{
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"进入工作中");
            doingLongTime(5);
            System.out.println(Thread.currentThread().getName()+"工作完成");
        }
    }


    static class BRunnable implements Runnable{

        volatile boolean pause;

        void tellToPause(){
            pause = true;
        }

        void tellToResume(){
            synchronized (this){
                this.notifyAll();
            }
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"进入工作中");
            doingLongTime(5);
            if(pause){
                try{
                    synchronized (this){
                        System.out.println(Thread.currentThread().getName()+"暂停服务");
                        this.wait();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName()+"继续工作中");
        }
    }




    static class ARunnable implements Runnable{

        volatile boolean stop;

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"进入不可停止区域 1。。。");
            doingLongTime(5);
            System.out.println(Thread.currentThread().getName()+"退出不可停止区域 1。。。");
            System.out.println(Thread.currentThread().getName()+"检测标志stop = "+ String.valueOf(stop));
            if (stop) {
                System.out.println(Thread.currentThread().getName()+"停止执行");
                return;
            }
            System.out.println(Thread.currentThread().getName()+"进入不可停止区域 2。。。");
            doingLongTime(5);
            System.out.println(Thread.currentThread().getName()+"退出不可停止区域 2。。。");
        }

        public void tellToSleep() {
            this.stop = true;
        }
    }

    static void sleep(int time){
        doingLongTime(time);
    }

    static void doingLongTime(int time){
        try {
            Thread.sleep(time*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void sleep2(int time) throws InterruptedException {
        Thread.sleep(time*1000);
    }
}
