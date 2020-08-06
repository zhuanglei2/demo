package com.zl.demo;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/6/24 17:27
 */
@Data
public class UserLoginCheckResponse {
    private Set<String> validUserRegistSourceIdSet = new HashSet();
}
