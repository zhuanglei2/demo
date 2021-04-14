package com.zl.demo.design.lfu;

import com.alibaba.fastjson.JSON;

import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * LFU 空间复杂度0(1)
 * @Author zhuanglei
 * @Date 2021/4/14 4:49 下午
 * @Version 1.0
 */
public class MyLfuCacheTreeSet<K,V> {

    private int max;
    private Map<K,CacheObject<K,V>> cache;
    //记录访问顺序
    private TreeSet<CacheObject<K,V>> keys;

    MyLfuCacheTreeSet(int max){
        this.max = max;
        cache = new ConcurrentHashMap<>(max);
        keys = new TreeSet<>();
    }

    public static void main(String[] args) {
        MyLfuCacheTreeSet<String,String> myLfuCache = new MyLfuCacheTreeSet(4);
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


    public V getValue(K key){
        CacheObject<K,V> cacheObject = cache.get(key);
        if(cacheObject==null){
            return null;
        }
        //重新放到尾部
        keys.remove(cacheObject);
        keys.add(cacheObject);
        return cacheObject.getValue();
    }

    public void put(K key,V value){
        if(max==0){
            return;
        }
        CacheObject<K,V> cacheObject = cache.get(key);
        if (cacheObject!=null){
            //重新放到尾部
            keys.remove(cacheObject);
            cacheObject.setValue(value);
        }else {
            if(cache.size() >= max){
                cache.remove(keys.first().key);
                keys.pollFirst();
            }
            cacheObject = new CacheObject<>(key,value);
        }
        keys.add(cacheObject);
        cache.put(key,cacheObject);
    }

    static class CacheObject<K,V> implements Comparable<CacheObject<K,V>>{


        private K key;
        private V value;
        private long lastAccessTime;
        private int accessCount;

        CacheObject(K key, V value) {
            this.key = key;
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
        public int compareTo(CacheObject<K,V> o) {
            //先比较访问次数
            int res = Integer.compare(this.accessCount, o.accessCount);
            //如果访问次数一致，判断最近访问时间最长的
            return res != 0 ? res : Long.compare(this.lastAccessTime, o.lastAccessTime);
        }
    }
}
