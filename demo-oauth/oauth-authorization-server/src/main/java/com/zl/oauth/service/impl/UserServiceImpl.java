package com.zl.oauth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.oauth.entity.User;
import com.zl.oauth.mapper.UserMapper;
import com.zl.oauth.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/1/28 21:01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
}
