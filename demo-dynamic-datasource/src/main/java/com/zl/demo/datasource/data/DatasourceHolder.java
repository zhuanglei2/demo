package com.zl.demo.datasource.data;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/3/11 10:01
 */
public enum DatasourceHolder {

    INSTANCE;

    /**
     * 默认数据源的id
     */
    public static final Long DEFAULT_ID = -1L;

    /**
     * 管理动态数据源列表。
     */
    private static final Map<Long, DatasourceManager> DATASOURCE_CACHE = new ConcurrentHashMap<>();


    /**
     * 启动执行，定时5分钟清理一次
     */
    DatasourceHolder() {
        DatasourceScheduler.INSTANCE.schedule(this::clearExpiredDatasource, 5 * 60 * 1000);
    }

    /**
     * 清理过期的数据源
     */
    private void clearExpiredDatasource() {
        if(CollectionUtils.isEmpty(DATASOURCE_CACHE)){
            return;
        }
        DATASOURCE_CACHE.forEach((k,v)->{
            if(!DEFAULT_ID.equals(k)){
                if(v.isExpired()){
                    DATASOURCE_CACHE.remove(k);
                }
            }
        });
    }



    /**
     * 获得数据源
     * @param id
     * @return
     */
    public synchronized HikariDataSource getDatasource(Long id){
        if(DATASOURCE_CACHE.containsKey(id)){
            DatasourceManager datasourceManager = DATASOURCE_CACHE.get(id);
            datasourceManager.refreshTime();
            return datasourceManager.getDataSource();
        }
        return null;
    }


    /**
     * 删除数据源
     * @param id
     */
    public synchronized void removeDatasource(Long id){
        if(DATASOURCE_CACHE.containsKey(id)){
            DatasourceManager datasourceManager = DATASOURCE_CACHE.get(id);
            datasourceManager.getDataSource().close();
            DATASOURCE_CACHE.remove(id);
        }
    }

    /**
     * 添加动态数据源
     *
     * @param id         数据源id
     * @param dataSource 数据源
     */
    public synchronized void addDatasource(Long id, HikariDataSource dataSource) {
        DatasourceManager datasourceManager = new DatasourceManager(dataSource);
        DATASOURCE_CACHE.put(id, datasourceManager);
    }

}
