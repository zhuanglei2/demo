package com.zl.demo.thread.forkJoin.task;

import java.util.concurrent.RecursiveTask;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/1/13 11:59
 */
public class SumTask extends RecursiveTask<Long> {
    //批次数
    static final int THRESHOLD = 500;
    long[] array;
    int start;
    int end;

    public SumTask(long[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }


    @Override
    protected Long compute() {
        if(end - start <= THRESHOLD){
            //如果任务足够小，直接计算
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum;
        }
        //任务太大,一分为2
        int middle = (end+start)/2;
        System.out.println(String.format("split %d~%d ==> %d~%d, %d~%d", start, end, start, middle, middle, end));
        SumTask subtask1 = new SumTask(this.array,start,middle);
        SumTask subtask2 = new SumTask(this.array,middle,end);
        //invokeAll会并行运行两个子任务
        invokeAll(subtask1,subtask2);
        Long subresult1 = subtask1.join();
        Long subresult2 = subtask2.join();
        //获得子任务的结果
        Long result = subresult1 + subresult2;
        System.out.println("result = " + subresult1 + " + " + subresult2 + " ==> " + result);
        return result;
    }
}
