package com.zl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zl.model.Permission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/2/1 16:23
 */
@Component
public interface PermissionMapper extends BaseMapper<Permission> {
    @Select("<script> select t1.* from sec_permission t1 left join sec_role_permission t2 on t1.id = t2.permission_id left join sec_role t3 on t2.role_id = t3.id where t3.id in " +
            "<foreach item='item' index='index' collection='roleIds' open='(' separator=',' close=')'> #{item} </foreach> </script>")
    List<Permission> selectByRoleIdList(@Param("roleIds") List<Long> roleIds);
}
