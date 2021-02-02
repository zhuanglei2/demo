package com.zl.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * <p>
 * 角色-权限
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-10 13:46
 */
@Data
@TableName("sec_role_permission")
public class RolePermission {
    /**
     * 主键
     */
    private Long roleId;

    private Long permissionId;
}
