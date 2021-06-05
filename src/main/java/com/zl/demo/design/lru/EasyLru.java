package com.zl.demo.design.lru;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 简易版lru
 * @Author zhuanglei
 * @Date 2021/5/8 3:31 下午
 * @Version 1.0
 */
public class EasyLru<K,V> extends LinkedHashMap<K,V> {
    private int CACHE_SIZE;

    public EasyLru(int max){
        super((int)Math.ceil(max/0.75)+1,0.75f,true);
        CACHE_SIZE = max;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size()>CACHE_SIZE;
    }
}
