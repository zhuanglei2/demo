package com.zl.demo.thread.phaser;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.Phaser;

import static jdk.nashorn.internal.objects.NativeMath.random;

/**
 * 某个线程到达预设点后，可以选择等待同伴或自己退出，等大家都到达后，再一起向下一个预设点出发，随时都可以有新的线程加入，退出的也可以再次加入
 * @author zhuangl
 * @version 1.0
 * @date 2021/2/24 15:49
 */
@Slf4j
public class PhaserTest {

    static final int COUNT = 6;

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Challenger("张三")).start();
        new Thread(new Challenger("李四")).start();
        new Thread(new Challenger("王五")).start();
        new Thread(new Challenger("赵六")).start();
        new Thread(new Challenger("大胖")).start();
        new Thread(new Challenger("小白")).start();
        synchronized (PhaserTest.class) {
            PhaserTest.class.wait();
        }
    }

    static Phaser ph = new Phaser(){
        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            log.info("第{}局,剩余{}人",phase,registeredParties);
            return registeredParties==0 ||
                    (phase!=0 && registeredParties==COUNT);
        }
    };

    static class Challenger implements Runnable{

        String name;
        int state;

        Challenger(String name){
            this.name = name;
            this.state = 0;
        }

        @Override
        public void run() {
            log.info("{}开始挑战。。。", name);
            ph.register();
            int phase = 0;
            int h;
            while (!ph.isTerminated() && phase < 100) {
                doingLongTime();
                if (state == 0) {
                    boolean goon = Decide.goon();
                    if (goon) {
                        //方法arriveAndAwaitAdvice()的作用与CountDownLatch中的await()方法大体一样。
                        //另一个作用是计数不足时，线程呈阻塞状态，不能继续向下运行。
                        h = ph.arriveAndAwaitAdvance();
                        if (h < 0)
                            log.info("No{}.[{}]继续，但已胜利。。。", phase, name);
                        else
                            log.info("No{}.[{}]继续at({})。。。", phase, name, h);
                    } else {
                        state = -1;
                        //使当前线程退出，并且是parties值减1
                        h = ph.arriveAndDeregister();
                        log.info("No{}.[{}]退出at({})。。。", phase, name, h);
                    }
                } else {
                    boolean revive = Decide.revive();
                    if (revive) {
                        state = 0;
                        h = ph.register();
                        if (h < 0)
                            log.info("No{}.[{}]复活，但已失败。。。", phase, name);
                        else
                            log.info("No{}.[{}]复活at({})。。。", phase, name, h);
                    } else {
                        log.info("No{}.[{}]没有复活。。。", phase, name);
                    }
                }
                phase++;
            }
            if (state == 0) {
                ph.arriveAndDeregister();
            }
            log.info("[{}]结束。。。", name);
        }
    }

    static void doingLongTime(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class Decide {

        static boolean goon() {
            return RandomUtil.randomInt(9) > 4;
        }

        static boolean revive() {
            return RandomUtil.randomInt(9) < 5;
        }
    }
}
