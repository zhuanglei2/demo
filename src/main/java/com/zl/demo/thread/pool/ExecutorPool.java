package com.zl.demo.thread.pool;

import org.apache.catalina.Executor;

import java.util.concurrent.*;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/1/14 11:42
 */
public class ExecutorPool {

    public static void main(String[] args) throws InterruptedException {
//        ExecutorService es = Executors.newFixedThreadPool(4);
        ExecutorService es = getScheduleExecutor();
        for (int i = 0; i < 6; i++) {
            es.submit(new Task(""+i));
        }
        Thread.sleep(6000);
        es.shutdown();
    }

    static class Task implements Runnable{
        private final String name;

        public Task(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println("start task " + name);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("end task "+name);
        }
    }

    /**
     * 创建指定动态范围的线程池
     * @return
     */
    public static ExecutorService getCachePool(){
        int min = 4;
        int max = 6;
        return new ThreadPoolExecutor(min,max,60L, TimeUnit.SECONDS,new SynchronousQueue<Runnable>());
    }

    /**
     * 定时反复执行线程池
     * @return
     */
    public static ScheduledExecutorService getScheduleExecutor(){
        ScheduledExecutorService service = Executors.newScheduledThreadPool(4);
        //1s后执行一次性的任务
        service.schedule(new Task("one-time"),1,TimeUnit.SECONDS);
        //2s开始执行定时任务，每隔3s后执行
        service.scheduleAtFixedRate(new TestTask("fixed-rate"),2,3,TimeUnit.SECONDS);
        //2s后开始执行定时任务，上一个任务执行完成后，执行任务
        service.scheduleWithFixedDelay(new TestTask("fixed-delay"),2,3,TimeUnit.SECONDS);
        return service;
    }

    static class TestTask extends Task{
        public TestTask(String name) {
            super(name);
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            super.run();
        }
    }
}
