package com.zl.demomapstruct.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/3/9 15:29
 */
@Getter
@AllArgsConstructor
public enum  UserTypeEnum {
    Java("000", "Java开发工程师"),
    DB("001", "数据库管理员"),
    LINUX("002", "Linux运维员");

    private String value;
    private String title;
}
