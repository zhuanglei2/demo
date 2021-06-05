package com.zl.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * hashmap  学习测试类
 * @Author zhuanglei
 * @Date 2021/5/17 9:55 上午
 * @Version 1.0
 */
@Slf4j
@SpringBootTest
public class HashMapTest {

    @Test
    public void putTest(){
        HashMap<String,Object> map = new HashMap();
        map.put("1","test1");
        map.put("1","test1");
        for (int i = 2; i < 20; i++) {
            map.put(""+i,"test"+i);
        }

        List<Integer> list = new ArrayList<>();
        list.sort(((o1, o2) -> o1.compareTo(o2)));
    }
}
