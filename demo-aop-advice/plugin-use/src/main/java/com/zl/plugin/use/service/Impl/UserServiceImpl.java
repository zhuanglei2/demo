package com.zl.plugin.use.service.Impl;

import com.zl.plugin.use.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/3/19 17:58
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public void getUser() {
        System.out.println("user");
    }
}
