package com.zl.demo.datasource.data;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 数据源缓存释放调度器
 * @author zhuangl
 * @version 1.0
 * @date 2021/3/11 10:02
 */
public enum  DatasourceScheduler {

    INSTANCE;

    private AtomicInteger cacheTaskNumber = new AtomicInteger(1);
    private ScheduledExecutorService schedule;

    DatasourceScheduler(){
        create();
    }

    private void create(){
        this.shutdown();
        this.schedule = new ScheduledThreadPoolExecutor(10,r->new Thread(r,String.format("Datasource-Release-Task-%s", cacheTaskNumber.getAndIncrement())));
    }

    private void shutdown(){
        if(null != this.cacheTaskNumber){
            this.schedule.shutdown();
        }
    }

    public void schedule(Runnable task,long delay){
        this.schedule.scheduleAtFixedRate(task,delay,delay,TimeUnit.MILLISECONDS);
    }
}
