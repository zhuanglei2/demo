package com.zl.demo.datasource.config;

import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/3/11 9:46
 */
@RegisterMapper
public interface MyMapper<T> extends Mapper<T>,MySqlMapper<T> {
}
