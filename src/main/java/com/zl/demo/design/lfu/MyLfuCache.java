package com.zl.demo.design.lfu;


import com.alibaba.fastjson.JSON;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 最不经常使用
 * 即最不经常使用，淘汰一定时期内访问次数最少的元素，如果访问次数相同，则比较最新一次的访问时间
 * @Author zhuanglei
 * @Date 2021/4/14 9:58 上午
 * @Version 1.0
 */
public class MyLfuCache<K,V> {

    /**
     * 最大容量
     */
    private int max;

    private Map<K,CacheObject<V>> cache;

    public static void main(String[] args) {
        MyLfuCache<String,String> myLfuCache = new MyLfuCache(4);
        myLfuCache.put("1","java");
        myLfuCache.put("2","c");
        myLfuCache.put("3","php");
        myLfuCache.put("4","python");
        myLfuCache.put("1","java1");
        System.out.println(JSON.toJSON(myLfuCache.cache));
        myLfuCache.put("5","c#");
        System.out.println(JSON.toJSON(myLfuCache.cache));
        myLfuCache.put("1","java2");
        System.out.println(JSON.toJSON(myLfuCache.cache));
    }


    public MyLfuCache(int max){
        this.max = max;
        cache = new ConcurrentHashMap<>(max);
    }

    public V get(K key){
        CacheObject<V> vCacheObject = cache.get(key);
        if(vCacheObject==null){
            return null;
        }
        return vCacheObject.getValue();
    }
    public void put(K key,V value){
        if(max==0){
            return;
        }
        CacheObject<V> cacheObject = cache.get(key);
        if(cacheObject!=null){
            cacheObject.setValue(value);
        }else {
            if(cache.size()>=max){
                //删除最少使用的键，使用次数相等，删除最久未使用的
                cache.entrySet()
                        .stream()
                        .min(Comparator.comparing(Map.Entry::getValue))
                        .ifPresent(e->cache.remove(e.getKey()));
            }
            cacheObject = new CacheObject<>(value);
        }
        cache.put(key,cacheObject);
    }

    static class CacheObject<V> implements Comparable<CacheObject<V>>{

        private V value;
        /**
         * 最近访问时间
         */
        private long lastAccessTime;
        /**
         * 访问次数
         */
        private int accessCount;

        CacheObject(V value){
            setValue(value);
        }

        public void setValue(V value) {
            this.value = value;
            this.lastAccessTime = System.nanoTime();
            accessCount++;
        }

        public V getValue(){
            this.lastAccessTime = System.nanoTime();
            accessCount++;
            return this.value;
        }

        @Override
        public int compareTo(CacheObject<V> o) {
            //先比较访问次数
            int res = Integer.compare(this.accessCount, o.accessCount);
            //如果访问次数一致，判断最近访问时间最长的
            return res != 0 ? res : Long.compare(this.lastAccessTime, o.lastAccessTime);
        }
    }
}
