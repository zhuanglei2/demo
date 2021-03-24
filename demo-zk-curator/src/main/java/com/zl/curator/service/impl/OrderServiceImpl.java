package com.zl.curator.service.impl;

import com.zl.curator.service.OrderService;
import com.zl.curator.utils.ZkUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/3/17 19:41
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ZkUtils zkUtils;

    @Override
    public void placeOrder() {
        final InterProcessMutex lock = zkUtils.getLock("/zk/lock");
        try {
            if(lock.acquire(1,TimeUnit.MILLISECONDS)){
                log.info(Thread.currentThread().getName()+"获得了锁");
                Thread.sleep(1000);
                lock.release();
            }else {
                log.error(Thread.currentThread().getName()+"没有拿到锁");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(lock.isOwnedByCurrentThread()){
                try {
                    lock.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
