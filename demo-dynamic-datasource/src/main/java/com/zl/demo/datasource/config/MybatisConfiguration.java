package com.zl.demo.datasource.config;

import lombok.SneakyThrows;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/3/10 11:56
 */
@Configuration
@MapperScan(basePackages = "com.zl.demo.datasource.mapper",sqlSessionFactoryRef = "sqlSessionFactory")
public class MybatisConfiguration {


    @Bean(name = "sqlSessionFactory")
    @SneakyThrows
    public SqlSessionFactory getSqlSessionFactory(@Qualifier("dataSource")DataSource dataSource){
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

}
