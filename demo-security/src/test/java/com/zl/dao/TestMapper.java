package com.zl.dao;

import com.alibaba.fastjson.JSON;
import com.zl.DemoSecurityApplication;
import com.zl.DemoSecurityApplicationTests;
import com.zl.mapper.PermissionMapper;
import com.zl.mapper.RoleMapper;
import com.zl.model.Permission;
import com.zl.model.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/2/1 16:46
 */
@Slf4j
public class TestMapper extends DemoSecurityApplicationTests {

    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private RoleMapper roleMapper;

    @Test
    public void testPermission(){
        List<Permission> permissions = permissionMapper.selectByRoleIdList(Arrays.asList(1072806379208708096L));
        System.out.println(JSON.toJSONString(permissions));
    }

    @Test
    public void testRole(){
        final List<Role> roles = roleMapper.selectByUserId(1072806379208708096L);
        System.out.println(JSON.toJSONString(roles));
    }

}
