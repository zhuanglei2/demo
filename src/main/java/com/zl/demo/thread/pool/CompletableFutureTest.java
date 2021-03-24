package com.zl.demo.thread.pool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author zhuanglei
 * @Date 2021/1/17 3:50 下午
 * @Version 1.0
 */
public class CompletableFutureTest {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(4);
        //异步执行任务
//        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(CompletableFutureTest::fetchPrice,es);
//        //如果执行成功
//        cf.thenAccept(result->{
//            System.out.println(" result: "+result);
//        });
//        // 如果执行异常:
//        cf.exceptionally((e) -> {
//            e.printStackTrace();
//            return null;
//        });
        //第一个任务获得股票名称
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(()->{
            return "中国石油";
        });
        //第二个任务获得价格
        CompletableFuture<Double> cfFetch = cf.thenApplyAsync((code)->{
            return fetchPrice(code);
        });
        cfFetch.thenAccept((result)->{
            System.out.println("price: " + result);
        });
        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
        Thread.sleep(200);
    }

    static String queryCode(String name) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        return "601857";
    }
    static Double fetchPrice() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        if (Math.random() < 0.3) {
            throw new RuntimeException("fetch price failed!");
        }
        return 5 + Math.random() * 20;
    }
    static Double fetchPrice(String code) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        return 5 + Math.random() * 20;
    }
}
