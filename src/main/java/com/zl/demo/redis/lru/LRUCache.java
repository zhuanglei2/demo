package com.zl.demo.redis.lru;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LUR算法
 * @author zhuangl
 * @version 1.0
 * @date 2021/2/24 20:19
 */
public class LRUCache<K,V> extends LinkedHashMap<K,V> {
    private final int CACHE_SIZE;

    public LRUCache(int cacheSize){
        super((int)Math.ceil(cacheSize/0.75)+1,0.75f,true);
        CACHE_SIZE = cacheSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size()>CACHE_SIZE;
    }

    public static void main(String[] args) {
        LRUCache lru = new LRUCache(6);
        String str = "abcdefghijklmn";
        for (int i = 0; i < str.length(); i++) {
            lru.put(str.charAt(i),i);
        }
        System.out.println("LRU的大小 ：" + lru.size());
        System.out.println("LRU ：" + lru);
    }
}
