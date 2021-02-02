package com.zl.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * <p>
 * 用户角色关联
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-10 11:18
 */
@Data
@TableName("sec_user_role")
public class UserRole {
    /**
     * 主键
     */
    private Long userId;

    private Long roleId;
}
