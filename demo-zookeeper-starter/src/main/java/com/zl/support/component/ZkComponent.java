package com.zl.support.component;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * @Author zhuanglei
 * @Date 2021/6/13 10:16 下午
 * @Version 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class ZkComponent {

    private final CuratorFramework client;


    /**
     * 创建新节点
     * @param path
     * @param value
     * @return
     */
    public String createNode(String path,String value) throws Exception {
        String node = client
                .create()
                .creatingParentsIfNeeded()
                //临时节点
                .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                .forPath(path,value.getBytes());
        return node;
    }

    /**
     * 删除节点
     * @param path
     */
    public void removeNode(String path) throws Exception {
        client.delete().deletingChildrenIfNeeded().forPath(path);
    }

    /**
     * 获取节点数据
     * @param path
     * @return
     */
    public String getNodeData(String path) throws Exception {
        Stat stat = new Stat();
        byte[] bytes = client.getData().storingStatIn(stat).forPath(path);
        return new String(bytes);
    }

    /**
     * 设置节点数据
     * @param path
     * @param value
     * @param <T>
     */
    public <T> void setNodeData(String path,T value) throws Exception {
        Stat stat = client.checkExists().forPath(path);
        if(null == stat){
            log.info(String.format("{} Znode is not exists",path));
            throw new RuntimeException(String.format("{} Znode is not exists",path));
        }
        String nodeData = getNodeData(path);
        client.setData().withVersion(stat.getVersion()).forPath(path, beanToString(value).getBytes());
    }

    /**
     * 节点监听事件 监听一个节点的更新和创建事件(不包括删除)
     * @param path
     */
    public void addWatcherWithNodeCache(String path) throws Exception {
        NodeCache nodeCache = new NodeCache(client,path,false);
        NodeCacheListener listener = ()->{
            ChildData currentData = nodeCache.getCurrentData();
            log.info("{} Znode data is change,new data is ---  {}", currentData.getPath(), new String(currentData.getData()));
        };
        nodeCache.getListenable().addListener(listener);
        nodeCache.start();
    }

    /**
     * @Description: 监听给定节点下的子节点的创建、删除、更新
     * @param path 给定节点
     * @Date: 2020-08-22 17:14
     */
    public void addWatcherWithChildCache(String path) throws Exception {
        if (null == client) {
            throw new RuntimeException("there is not connect to zkServer...");
        }
        //cacheData if true, node contents are cached in addition to the stat
        PathChildrenCache pathChildrenCache = new PathChildrenCache(client,path,false);
        PathChildrenCacheListener listener = (client, event) -> {
            log.info("event path is --{} ,event type is {}" , event.getData().getPath(), event.getType());
        };
        pathChildrenCache.getListenable().addListener(listener);
        pathChildrenCache.start(PathChildrenCache.StartMode.NORMAL);
    }

    private <T> T stringToBean(String str, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(str, clazz);
    }

    private <T> String beanToString(T value) {
        Gson gson = new Gson();
        return gson.toJson(value);
    }
}
