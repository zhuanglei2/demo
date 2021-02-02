package com.zl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zl.model.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/2/1 16:21
 */
@Component
public interface RoleMapper extends BaseMapper<Role> {
    @Select("SELECT t1.* FROM sec_role t1 LEFT JOIN sec_user_role t2 on t1.id = t2.role_id LEFT JOIN sec_user t3 on t3.id = t2.user_id where t3.id = #{userId}")
    List<Role> selectByUserId(@Param("userId") Long userId);
}
