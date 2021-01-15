package com.zl.demo.thread.twoThread;

/**
 * wait/notify版本
 * @author zhuangl
 * @version 1.0
 * @date 2021/1/14 10:33
 */
public class TwoThread {

    public static void main(String[] args) {
        IsNum item = new IsNum(false);
        Thread a1 = new Thread(new A("打印数字",item));
        Thread a2 = new Thread(new B("打印字母",item));
        a1.start();
        a2.start();
    }

    /**
     * 打印数字
     */
    static class A implements Runnable{

        private IsNum item;
        private String name;

        A(String name,IsNum item){
            this.name = name;
            this.item = item;
        }

        @Override
        public void run() {
            synchronized (item){
                while(true){
                    if(!item.flag){
                        System.out.println(name+" : "+item.numberPrint);
                        item.numberPrint++;
                        item.flag = true;
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        item.notifyAll();
                    }else {
                        try {
                            item.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    static class B implements Runnable{

        private IsNum item;
        private String name;

        B(String name,IsNum item){
            this.name = name;
            this.item = item;
        }

        @Override
        public void run() {
            synchronized (item){
                while(true){
                    if(item.flag){
                        System.out.println(name+" : "+(char)(96+item.wordPrin));
                        item.wordPrin++;
                        item.flag = false;
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        item.notifyAll();
                    }else {
                        try {
                            item.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

}
