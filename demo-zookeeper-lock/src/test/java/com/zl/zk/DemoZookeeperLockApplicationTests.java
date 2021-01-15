package com.zl.zk;

import com.zl.zk.annotation.ZooLock;
import com.zl.zk.aspect.ZooLockAspect;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class DemoZookeeperLockApplicationTests {

    private Integer getCount(){
        return count;
    }

    private Integer count = 10000;
    //并发数
    private static final int threadNum = 10000;

    //倒计时数 发令枪 用于制造线程的并发执行
    private static CountDownLatch cdl = new CountDownLatch(10000);

    @Autowired
    private CuratorFramework zkClient;

    private ExecutorService executorService = Executors.newFixedThreadPool(1000);


    /**
     * 不使用分布式锁，程序结束查看count的值是否为0
     */
    @Test
    public void test() throws InterruptedException {
        BuyTest buyTest = new BuyTest(count);
        for (int i=0;i<threadNum;i++){
            new Thread(buyTest).start();
        }
        cdl.await();
        log.info("count最终值为:{}",buyTest.count);
    }

    /**
     * 测试AOP分布式锁
     */
    @Test
    public void testAopLock() throws InterruptedException {
        // 测试类中使用AOP需要手动代理
        DemoZookeeperLockApplicationTests target = new DemoZookeeperLockApplicationTests();
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        ZooLockAspect aspect = new ZooLockAspect(zkClient);
        factory.addAspect(aspect);
        DemoZookeeperLockApplicationTests proxy = factory.getProxy();
        IntStream.range(0, 10000).forEach(i -> executorService.execute(() -> proxy.aopBuy(i)));
        TimeUnit.MINUTES.sleep(1);
        log.error("count最终值为{}", proxy.getCount());
    }



    @Data
    public static class BuyTest implements Runnable{

        BuyTest(int count){
            this.count = count;
        }

        private int count;

        @Override
        public void run() {
            cdl.countDown();
            doBuy();
//            aopBuy(count);
        }

        public void doBuy(){
            count--;
            log.info("count值为{}",count);
        }

    }

    @ZooLock(key = "buy-2020-09-24", timeout = 1, timeUnit = TimeUnit.MINUTES)
    public void aopBuy(int userId) {
        log.info("{} 正在出库。。。", userId);
        doBuy();
        log.info("{} 扣库存成功。。。", userId);
    }


    public void doBuy(){
        count--;
        log.info("count值为{}",count);
    }
}
