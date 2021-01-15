package com.zl.demo.thread.forkJoin;

import com.zl.demo.thread.forkJoin.task.SumTask;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/1/13 11:51
 */
public class ForkJoinTest {
    public static void main(String[] args) {
        long[] array = new long[2000];
        long expectedSum = 0;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < array.length; i++) {
            array[i] = random();
            expectedSum += array[i];
        }
        System.out.println("Expect sum:"+expectedSum+" time:"+(System.currentTimeMillis()-startTime));
        //fork join
        ForkJoinTask<Long> task = new SumTask(array,0,array.length);
        startTime = System.currentTimeMillis();
        Long result = ForkJoinPool.commonPool().invoke(task);
        Long endTime = System.currentTimeMillis();
        System.out.println("fork join time"+(endTime-startTime));
    }

    static Random random = new Random(0);

    static long random() {
        return random.nextInt(10000);
    }

}
