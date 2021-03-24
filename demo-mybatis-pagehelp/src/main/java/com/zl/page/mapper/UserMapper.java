package com.zl.page.mapper;

import com.zl.page.entity.User;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/3/16 9:59
 */
@Component
public interface UserMapper extends Mapper<User>,MySqlMapper<User> {
}
