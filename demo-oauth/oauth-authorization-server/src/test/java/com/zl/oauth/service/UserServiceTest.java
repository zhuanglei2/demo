package com.zl.oauth.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.zl.oauth.OauthAuthorizationServerApplicationTests;
import com.zl.oauth.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/1/28 20:46
 */
@Slf4j
public class UserServiceTest extends OauthAuthorizationServerApplicationTests {
    @Autowired
    private UserService userService;

    @Test
    public void testSave(){
        String salt = IdUtil.fastSimpleUUID();
        User testSave3 = User.builder().name("testSave3").password(SecureUtil.md5("123456"+salt)).salt(salt).email("zhuanglei@wayyue.con").phoneNumber("13222222222").status(1).lastLoginTime(new DateTime()).build();
        boolean save = userService.save(testSave3);
        log.debug("【测试id回显#testSave3.getId()】= {}", testSave3.getId());
    }
}
