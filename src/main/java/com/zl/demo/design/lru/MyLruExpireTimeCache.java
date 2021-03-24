package com.zl.demo.design.lru;

import com.alibaba.fastjson.JSON;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 使用concurrentHashMap（缓存）+concurrentLinkedQueue（队列）+readWriteLock（锁）实现LRU（最近最少使用）缓存
 * 1.思路
 * put时：如果存在key值，更新value，且key值放置到队列的末尾, 不存在判断是否超出队列长度,如果超出将队列头删除
 * get时：将key值放置到队列的末尾
 * @author zhuangl
 * @version 1.0
 * @date 2021/3/24 15:14
 */
public class MyLruExpireTimeCache<K,V> {

    /**
     * 缓存的最大容量
     */
    private final int maxCapacity;

    /**
     * 本地缓存
     */
    private ConcurrentHashMap<K,V> cacheMap;

    private ConcurrentLinkedDeque<K> keys;

    /**
     * 读写锁（读写锁可以保证多个线程和同时读取，但是只有一个线程可以写入）
     */
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Lock writeLock = readWriteLock.writeLock();
    private Lock readLock = readWriteLock.readLock();

    /**
     * 定时任务将过期的key删除
     */
    private ScheduledExecutorService scheduledExecutorService;

    public MyLruExpireTimeCache(int maxCapacity) {
        if(maxCapacity<0){
            throw new IllegalArgumentException("Illegal max capacity: "+maxCapacity);
        }
        this.maxCapacity = maxCapacity;
        cacheMap = new ConcurrentHashMap<>(maxCapacity);
        keys = new ConcurrentLinkedDeque<>();
        scheduledExecutorService = Executors.newScheduledThreadPool(3);
    }

    /**
     * 入队
     * @param key
     * @param value
     * @return
     */
    public V put(K key,V value,long expireTime){
        writeLock.lock();
        try{
            //1.key是否存在于缓存
            if(cacheMap.containsKey(key)){
                //将key移动到对尾
                moveToTailOfQueue(key);
                cacheMap.put(key,value);
                return value;
            }
            //2.是否超出缓存容量，超出的话删除队列中的头部元素及其对应的缓存
            if(cacheMap.size() == maxCapacity){
                System.out.println("maxCapacity of cache reached");
                removeOldestKey();
            }
            //3.key不存在与当前缓存,将key添加到队列的尾部并且缓存key及其对应的元素
            keys.add(key);
            cacheMap.put(key,value);
            //增加过期逻辑
            removeAfterExpireTime(key,expireTime);
            return value;
        }finally {
            writeLock.unlock();
        }
    }

    /**
     * 获取value
     * @param key
     * @return
     */
    public V get(K key){
        readLock.lock();
        try{
            if(cacheMap.containsKey(key)){
                moveToTailOfQueue(key);
                return cacheMap.get(key);
            }
            return null;
        }finally {
            readLock.unlock();
        }
    }


    /**
     * 移除队列头部元素以及缓存中对应的元素
     */
    private void removeOldestKey() {
        K poll = keys.poll();
        if(poll!=null){
            cacheMap.remove(poll);
        }
    }

    /**
     * 将key值移动到队列的尾部
     * @param key
     */
    private void moveToTailOfQueue(K key) {
        keys.remove(key);
        keys.add(key);
    }

    /**
     * 删除元素
     * @param key
     * @return
     */
    public V remove(K key){
        writeLock.lock();
        try {
            if(cacheMap.containsKey(key)){
                keys.remove(key);
                return cacheMap.remove(key);
            }
            return null;
        }finally {
            writeLock.unlock();
        }
    }

    /**
     * 过期清除key
     * @param key
     * @param expireTime
     */
    private void removeAfterExpireTime(K key,long expireTime){
        scheduledExecutorService.schedule(()->{
            keys.remove(key);
            cacheMap.remove(key);
        },expireTime,TimeUnit.MILLISECONDS);
    }

    public int size(){
        return cacheMap.size();
    }

    /**
     * 非并发环境下尝试
     */
    public static void testNoConcurrent() throws InterruptedException {
        MyLruExpireTimeCache<Integer,String> myLruCache = new MyLruExpireTimeCache<>(3);
        myLruCache.put(1,"Java",3000);
        myLruCache.put(2,"C++",3000);
        myLruCache.put(3,"Python",1500);
        System.out.println(myLruCache.size());//3
        Thread.sleep(2000);
        System.out.println(myLruCache.size());//2
    }



    public static void main(String[] args) throws InterruptedException {
        testNoConcurrent();
    }
}
