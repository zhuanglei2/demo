package com.zl.demo.datasource.mapper;

import com.zl.demo.datasource.config.MyMapper;
import com.zl.demo.datasource.model.DatasourceConfig;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据源Do
 * @author zhuangl
 * @version 1.0
 * @date 2021/3/11 20:26
 */
@Mapper
public interface DatasourceMapper extends MyMapper<DatasourceConfig> {

}
