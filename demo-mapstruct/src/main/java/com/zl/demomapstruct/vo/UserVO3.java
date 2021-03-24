package com.zl.demomapstruct.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/3/9 10:44
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO3 {
    private String id;
    private String name;
    // 实体类该属性是String
    private LocalDateTime createTime;
    // 实体类该属性是LocalDateTime
    private String updateTime;
}
