package com.zl.demo.datasource.data;

import com.zl.demo.datasource.model.DatasourceConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Datasource
 * @author zhuangl
 * @version 1.0
 * @date 2021/3/11 15:54
 */
public enum DataSourceConfigCache {
    INSTANCE;

    private static final Map<Long,DatasourceConfig> CONFIG_CACHE = new ConcurrentHashMap<>();


    /**
     * 添加数据源配置
     * @param id
     * @param datasourceConfig
     */
    private synchronized void addConfig(Long id,DatasourceConfig datasourceConfig){
        CONFIG_CACHE.put(id, datasourceConfig);
    }

    /**
     * 查找数据源配置
     * @param id
     * @return
     */
    public synchronized DatasourceConfig getConfig(Long id){
        if(CONFIG_CACHE.containsKey(id)){
            return CONFIG_CACHE.get(id);
        }
        return null;
    }


    /**
     * 删除数据源配置
     * @param id
     */
    public synchronized void removeConfig(Long id){
        CONFIG_CACHE.remove(id);
        //同步清除 DatasourceHolder 对应的数据源
        DatasourceHolder.INSTANCE.removeDatasource(id);
    }
}
