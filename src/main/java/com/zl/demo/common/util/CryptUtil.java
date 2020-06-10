package com.zl.demo.common.util;

import com.zl.demo.aspect.Decrypt;
import com.zl.demo.aspect.Encrypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2019/12/31 14:53
 */
@Slf4j
public class CryptUtil {
    /**
     * 解密
     */
    public static boolean deCrypt(Object o,String encryptKey){
        boolean result = true;
        try{

            Field[] fields = o.getClass().getDeclaredFields();

            for(Field field : fields){

                Decrypt decrypt = field.getAnnotation(Decrypt.class);

                if(null != decrypt){

                    field.setAccessible(true);

                    Object var = field.get(o);

                    if(var == null) continue;

                    if(StringUtils.isEmpty(var.toString()))continue;

                    String deVar = var.toString().replaceAll("#","");
                    if (deVar == null) result = false;

                    field.set(o,deVar);
                }
            }

        }catch (Exception e){

            log.info("解密失败",e);
            return false;
        }
        return result;
    }


    /**
     * 解密
     */
    public static boolean enCrypt(Object o,String encryptKey) {
        boolean result = true;
        try {

            Field[] fields = o.getClass().getDeclaredFields();

            for (Field field : fields) {

                Encrypt enCrypt = field.getAnnotation(Encrypt.class);

                if (null != enCrypt) {

                    field.setAccessible(true);

                    Object var = field.get(o);

                    String EnVar = var==null?"":var+"#";
                    if (EnVar == null) result = false;

                    field.set(o, EnVar);
                }
            }

        } catch (Exception e) {

            log.info("加密失败", e);
            return false;
        }
        return result;
    }
}
