package com.zl.curator.utils;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/3/17 17:28
 */
@Component
public class ZkUtils {

    @Autowired
    private CuratorFramework curatorFramework;

    /**
     * 获取互斥锁
     * @param path
     * @return
     */
    public InterProcessMutex getLock(String path){
        return new InterProcessMutex(curatorFramework,path);
    }

}
