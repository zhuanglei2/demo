package com.zl.demo.thread.cyclicBarrier;

import ch.qos.logback.core.encoder.EchoEncoder;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author zhuanglei
 * @Date 2021/3/7 2:09 下午
 * @Version 1.0
 */
public class UseCyclicBarrier {

    private static CyclicBarrier barrier = new CyclicBarrier(5,new CollectThread());


    private static ConcurrentHashMap<String,Long> resultMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        for (int i = 0; i <= 4; i++) {
            Thread thread = new Thread(new SubThread());
            thread.start();
        }
    }

    /**
     * 负责屏障开发以后的工作
     */
    private static class CollectThread implements Runnable{

        @Override
        public void run() {
            StringBuffer result = new StringBuffer();
            for (Map.Entry<String,Long> workResult:resultMap.entrySet()){
                result.append("["+workResult.getValue()+"]");
            }
            System.out.println(" the result " + result);
            System.out.println("do other business......");
        }
    }

    /**
     * 工作线程
     */
    private static class SubThread implements Runnable{

        @Override
        public void run() {
            long id = Thread.currentThread().getId();
            resultMap.put(Thread.currentThread().getId()+"",id);
            Random r = new Random();
            try {
                if(r.nextBoolean()){
                    Thread.sleep(1000+id);
                    System.out.println("Thread_"+id+"... do something");
                }
                barrier.await();
                Thread.sleep(1000+id);
                System.out.println("Thread_"+id+"..... do its business ");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
