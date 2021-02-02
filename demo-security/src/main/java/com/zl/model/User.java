package com.zl.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * <p>
 * 用户
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-07 16:00
 */
@Data
@TableName("sec_user")
public class User {

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 生日
     */
    private Long birthday;

    /**
     * 性别，男-1，女-2
     */
    private Integer sex;

    /**
     * 状态，启用-1，禁用-0
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新时间
     */
    private Long updateTime;
}
