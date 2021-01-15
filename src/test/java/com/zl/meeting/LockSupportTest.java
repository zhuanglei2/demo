package com.zl.meeting;

import com.zl.demo.DemoApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.locks.LockSupport;

import static java.lang.System.out;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/11/17 17:57
 */
@Slf4j
@SpringBootTest(classes = DemoApplication.class)
public class LockSupportTest {

    Thread t1 = null;
    Thread t2 = null;


    /**
     * 2个线程循环打印1,A,2,B 使用wait notify
     */
    @Test
    public void testNotify(){
        Object o = new Object();
        char[] aI = "1234567".toCharArray();
        char[] aC = "ABCDEFG".toCharArray();

        t1 = new Thread(()->{
            synchronized (o){
                for (char c : aI){
                    System.out.println(c);
                    try{
                        o.notify();
                        o.wait();
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                o.notify();
            }

        },"t1");

        t2 = new Thread(()->{
            synchronized (o){
                for (char c : aC){
                    System.out.println(c);
                    try{
                        o.notify();
                        o.wait();
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                o.notify();
            }
        },"t2");

        t1.start();
        t2.start();
    }


    /**
     * 2个线程A,B依次打印奇数 偶数
     */
    @Test
    public void testOddEven(){
        t1 = new Thread(()->{
            for (int i=0;i<10;i+=2){
                System.out.println("t1:"+i);
                LockSupport.unpark(t2);//叫醒T2
                LockSupport.park();//T1阻塞 当前线程阻塞
            }
        },"t1");

        t2 = new Thread(()->{
            for (int i=1;i<10;i+=2){
                LockSupport.park();
                System.out.println("t2:"+i);
                LockSupport.unpark(t1);
            }
        },"t2");

        t1.start();
        t2.start();
    }

    /**
     * 2个线程循环打印1,A,2,B ,使用park和unpark
     */
    @Test
    public void testThread(){
        char[] aI = "1234567".toCharArray();
        char[] aC = "ABCDEFG".toCharArray();

        t1 = new Thread(()->{
            for (char c : aI){
                System.out.println(c);
                LockSupport.unpark(t2);//叫醒T2
                LockSupport.park();//T1阻塞 当前线程阻塞
            }
        },"t1");

        t2 = new Thread(()->{
            for (char c: aC){
                LockSupport.park();
                System.out.println(c);
                LockSupport.unpark(t1);
            }
        },"t2");

        t1.start();
        t2.start();
    }
}
