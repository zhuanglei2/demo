package com.zl.demo.datasource.config;

import com.zl.demo.datasource.annotation.DefaultDataSource;
import com.zl.demo.datasource.data.DynamicDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/3/11 9:47
 */
@Configuration
public class DataSourceConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(){
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.type(DynamicDataSource.class);
        return dataSourceBuilder.build();
    }
}
