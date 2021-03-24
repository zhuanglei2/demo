package com.zl.curator;

import com.zl.curator.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;

@Slf4j
@SpringBootTest
public class DemoZkCuratorApplicationTests {

    @Autowired
    private OrderService orderService;

    private CountDownLatch countDownLatch = new CountDownLatch(10);

    @Test
    public void contextLoads(){
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                orderService.placeOrder();
                countDownLatch.countDown();
            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
