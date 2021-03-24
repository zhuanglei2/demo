package com.zl.demo.datasource.data;

import com.zaxxer.hikari.HikariDataSource;
import com.zl.demo.datasource.model.DatasourceConfig;
import com.zl.demo.datasource.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/3/11 10:00
 */
@Slf4j
public class DynamicDataSource extends HikariDataSource {

    @Override
    public Connection getConnection() throws SQLException {
        //获取当前数据源的id
        Long id = DatasourceConfigContextHolder.getCurrentDatasourceConfig();
        HikariDataSource datasource = DatasourceHolder.INSTANCE.getDatasource(id);
        if(datasource==null){
            datasource = initDatasource(id);
        }
        return super.getConnection();
    }

    /**
     * 初始化数据源
     * @param id
     * @return
     */
    private HikariDataSource initDatasource(Long id) {
        HikariDataSource dataSource = new HikariDataSource();

        //判断是否是默认数据源
        if(DatasourceHolder.DEFAULT_ID.equals(id)){
            // 默认数据源根据 application.yml 配置的生成
            DataSourceProperties properties = SpringUtil.getBean(DataSourceProperties.class);
            dataSource.setJdbcUrl(properties.getUrl());
            dataSource.setUsername(properties.getUsername());
            dataSource.setPassword(properties.getPassword());
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        }else {
            DatasourceConfig datasourceConfig = DataSourceConfigCache.INSTANCE.getConfig(id);
            if (datasourceConfig == null) {
                throw new RuntimeException("无此数据源");
            }

            dataSource.setJdbcUrl(datasourceConfig.buildJdbcUrl());
            dataSource.setUsername(datasourceConfig.getUsername());
            dataSource.setPassword(datasourceConfig.getPassword());
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        }
        DatasourceHolder.INSTANCE.addDatasource(id,dataSource);
        return dataSource;
    }


}
