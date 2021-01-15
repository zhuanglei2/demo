package com.zl.demo.thread.waitnotify;

import lombok.var;

import java.util.ArrayList;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/1/13 19:42
 */
public class TaskQueueTest {

    public static void main(String[] args) throws InterruptedException {
        var q = new TaskQueue();
        var ts = new ArrayList<Thread>();
        for (int i = 0; i < 5; i++) {
            //从队列中取
            var t = new Thread(()->{
                //执行task
                while (true){
                    try {
                        String s = q.getTask();
                        System.out.println("execute task: "+s);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            });
            t.start();
            ts.add(t);
        }
        var add = new Thread(() -> {
            for (int i=0; i<10; i++) {
                // 放入task:
                String s = "t-" + Math.random();
                System.out.println("add task: " + s);
                q.addTask(s);
                try { Thread.sleep(100); } catch(InterruptedException e) {}
            }
        });
        add.start();
        add.join();
        Thread.sleep(100);
        for (var t : ts) {
            t.interrupt();
        }
    }
}
