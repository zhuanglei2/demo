package com.zl.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * <p>
 * 角色
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-07 15:45
 */
@Data
@TableName("sec_role")
public class Role {
    /**
     * 主键
     */
    private Long id;

    /**
     * 角色名
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新时间
     */
    private Long updateTime;
}
