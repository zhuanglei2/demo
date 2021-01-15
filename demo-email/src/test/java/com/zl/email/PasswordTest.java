package com.zl.email;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/9/9 15:49
 */
public class PasswordTest extends DemoEmailApplicationTests {

    @Autowired
    private StringEncryptor encryptor;

    @Test
    public void testGeneratePassword(){
        //邮箱密码
        String password=  "123456a";
        //加密后的密码(注意：配置上去的时候需要加 ENC(加密密码))
        String encryptPassword = encryptor.encrypt(password);
        String decryptPassword = encryptor.decrypt(encryptPassword);
        System.out.println("password = "+password);
        System.out.println("encryptPassword = "+encryptPassword);
        System.out.println("decryptPassword = "+decryptPassword);
    }
}
