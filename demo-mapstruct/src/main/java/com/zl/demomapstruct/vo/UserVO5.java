package com.zl.demomapstruct.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/3/9 15:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVO5 {
    private Integer id;
    private String name;
    private String type;
}