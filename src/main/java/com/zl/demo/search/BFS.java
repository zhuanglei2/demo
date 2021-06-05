package com.zl.demo.search;

import com.alibaba.fastjson.JSON;

import java.util.*;

/**
 * @Author zhuanglei
 * @Date 2021/5/7 10:43 上午
 * @Version 1.0
 */
public class BFS {
    public static void main(String[] args) {
        numSquares(12);
    }
    public static int num(int n){
        //计算平方数
        ArrayList<Integer> squares = new ArrayList<>();
        for (int i = 1;i<=n;i++){
            squares.add(i*i);
        }
        Set<Integer> queues = new HashSet<>();
        queues.add(n);

        int level = 0;
        while (!queues.isEmpty()){
            Set<Integer> nextQueue = new HashSet<>();
            for (Integer queue:queues){
                for (Integer square:squares){
                    if(queue.equals(square)){
                        return level;
                    }else if(queue<square){
                        break;
                    }else {
                        nextQueue.add(queue-square);
                    }
                }
            }
            queues = nextQueue;
        }
        return level;
    }

    public static int numSquares(int n) {
        ArrayList<Integer> square_nums = new ArrayList<Integer>();
        for (int i = 1; i * i <= n; ++i) {
            square_nums.add(i * i);
        }

        Set<Integer> queue = new HashSet<Integer>();
        queue.add(n);

        int level = 0;
        while (queue.size() > 0) {
            level += 1;
            Set<Integer> next_queue = new HashSet<Integer>();
            System.out.println("queue:"+JSON.toJSON(queue));
            for (Integer remainder : queue) {
                for (Integer square : square_nums) {
                    if (remainder.equals(square)) {
                        return level;
                    } else if (remainder < square) {
                        break;
                    } else {
                        System.out.println("remainder - square:"+(remainder - square));
                        next_queue.add(remainder - square);
                    }
                }
            }
            System.out.println("next_queue:"+JSON.toJSON(next_queue));
            queue = next_queue;
        }
        return level;
    }


}

