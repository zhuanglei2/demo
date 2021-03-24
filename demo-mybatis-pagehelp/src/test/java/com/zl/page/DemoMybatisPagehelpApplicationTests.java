package com.zl.page;

import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.zl.page.entity.Role;
import com.zl.page.entity.User;
import com.zl.page.mapper.RoleMapper;
import com.zl.page.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class DemoMybatisPagehelpApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    private static final Gson gson = new Gson();

    @Test
    void contextLoads() {
      final List<User> users = userMapper.selectAll();
      log.info(gson.toJson(users));
    }

    @Test
    public void pageHelper(){
        PageHelper.startPage(1,5,"last_update_time desc");
        final List<User> users = userMapper.selectAll();
        PageHelper.startPage(1,5,"update_time desc");
        final List<Role> roles = roleMapper.selectAll();
        log.info(gson.toJson(users));
        log.info(gson.toJson(roles));
    }

}
