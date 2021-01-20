package com.zl.demo.thread.completableFuture;

import java.util.concurrent.CompletableFuture;

/**
 * 同时从新浪和网易查询证券代码，只要任意一个返回结果，就进行下一步查询价格，查询价格也同时从新浪和网易查询，只要任意一个返回结果，就完成操作：
 * @author zhuangl
 * @version 1.0
 * @date 2021/1/20 11:23
 */
public class AnyOf {
    public static void main(String[] args) throws InterruptedException {
        CompletableFuture<String> cfQueryFromSina = CompletableFuture.supplyAsync(()->{
            return queryCode("中国石油", "https://finance.sina.com.cn/code/");
        });

        CompletableFuture<String> cfQueryFrom163 = CompletableFuture.supplyAsync(()->{
            return queryCode("中国石油", "https://money.163.com/code/");
        });
        //用anyOf合并
        CompletableFuture<Object> cfQuery = CompletableFuture.anyOf(cfQueryFrom163,cfQueryFromSina);

        //两个CompletableFuture异步查询
        CompletableFuture<Double> cfFetchFromSina = cfQuery.thenApplyAsync((code)->{
            return fetchPrice((String) code, "https://finance.sina.com.cn/price/");
        });

        CompletableFuture<Double> cfFetchFrom163 = cfQuery.thenApplyAsync((code) -> {
            return fetchPrice((String) code, "https://money.163.com/price/");
        });

        CompletableFuture<Object> cfFetch = CompletableFuture.anyOf(cfFetchFrom163,cfFetchFromSina);
        //最终结果
        cfFetch.thenAccept((result)->{
            System.out.println("price: "+ result);
        });
        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
        Thread.sleep(200);
    }

    static String queryCode(String name, String url) {
        System.out.println("query code from " + url + "...");
        try {
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
        }
        return "601857";
    }

    static Double fetchPrice(String code, String url) {
        System.out.println("query price from " + url + "...");
        try {
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
        }
        return 5 + Math.random() * 20;
    }
}
