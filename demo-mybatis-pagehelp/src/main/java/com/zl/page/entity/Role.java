package com.zl.page.entity;

import lombok.Data;

import javax.persistence.Table;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/3/16 10:22
 */
@Data
@Table(name = "sec_role")
public class Role {
    private Long id;

    private String name;

    private String description;

    private Long createTime;

    private Long updateTime;
}
