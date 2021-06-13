package com.zl.demo.list;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 导致数组越界的问题
 * @Author zhuanglei
 * @Date 2021/6/13 9:15 上午
 * @Version 1.0
 */
public class CopyonWriteArrayListTest {

    public static void main(String[] args) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(i+"");
        }
        Thread a = new Thread(new A(list));
        Thread b = new Thread(new B(list));
        a.start();
        b.start();

        Thread.yield();
    }

    public static class A implements Runnable{
        CopyOnWriteArrayList<String> list;
        public A(CopyOnWriteArrayList list){
            this.list = list;
        }

        @Override
        public void run() {
           while (true){
               String content = list.get(list.size()-1);
               System.out.println(content);
           }
        }
    }

    public static class B implements Runnable{


        CopyOnWriteArrayList<String> list;
        public B(CopyOnWriteArrayList list){
            this.list = list;
        }

        @Override
        public void run() {
           while (true){
               list.remove(0);
               try {
                   Thread.sleep(10);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
        }
    }
}
