package com.zl.page.mapper;

import com.zl.page.entity.Role;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/3/16 10:24
 */
@Component
public interface RoleMapper extends Mapper<Role>,MySqlMapper<Role> {
}
