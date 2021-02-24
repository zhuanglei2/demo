package com.zl.demo.thread.exchanger;

import java.util.concurrent.Exchanger;

/**
 * 两个线程在预设点交换变量，先到达的等待对方
 * exchanger测试类
 * @author zhuangl
 * @version 1.0
 * @date 2021/2/24 15:22
 */
public class ExchangerTest {

    static Exchanger<Tool> ex = new Exchanger<>();

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Staff("大哥",new Tool("扫帚","扫地"),ex)).start();
        new Thread(new Staff("小弟",new Tool("啤酒","休息"),ex)).start();
        synchronized (ExchangerTest.class){
            ExchangerTest.class.wait();
        }
    }

    static class Staff implements Runnable{

        Exchanger<Tool> ex;
        Tool tool;
        String name;

        Staff(String name,Tool tool,Exchanger<Tool> ex){
            this.name = name;
            this.tool = tool;
            this.ex = ex;
        }


        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"中"+name+"当前工作为"+tool.work+"工具为"+tool.name);
            doingLongTime();
            System.out.println(Thread.currentThread().getName()+"中"+name+"开始交换工具...");
            try {
                tool = ex.exchange(tool);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"中"+name+"交换工具完成,"+tool.work+"工具为"+tool.name);
        }
    }



    static void doingLongTime(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    static class Tool{
        private String name;
        private String work;

        public Tool(String name, String work) {
            this.name = name;
            this.work = work;
        }
    }

}
