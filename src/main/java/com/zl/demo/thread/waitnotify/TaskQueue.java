package com.zl.demo.thread.waitnotify;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/1/13 17:46
 */
public class TaskQueue {
    Queue<String> queue = new LinkedList<>();


    public synchronized void addTask(String s){
        this.queue.add(s);
        this.notifyAll();
    }
    public synchronized String getTask() throws InterruptedException {
        while (queue.isEmpty()) {
            //如果不wait,就不会释放锁,synchronized方法锁的是this,其他线程无法调用addTask,因为addTask也是获取this锁,进而导致死锁
            this.wait();//释放this锁
            //重新获取this
        }
        return queue.remove();
    }

}
