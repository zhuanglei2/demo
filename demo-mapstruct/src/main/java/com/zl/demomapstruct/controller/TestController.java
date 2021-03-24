package com.zl.demomapstruct.controller;

import com.zl.demomapstruct.dto.User;
import com.zl.demomapstruct.enums.UserTypeEnum;
import com.zl.demomapstruct.transfer.UserTransfer;
import com.zl.demomapstruct.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/3/9 10:51
 */
@RestController
public class TestController {

    @GetMapping("convert")
    public Object convertEntity() {
        User user = User.builder()
                .id(1)
                .name("张三")
                .createTime("2020-04-01 11:05:07")
                .updateTime(LocalDateTime.now())
                .build();
        List<Object> objectList = new ArrayList<>();

        objectList.add(user);

        // 使用mapstruct
        UserVO1 userVO1 = UserTransfer.INSTANCE.toConvertVO1(user);
        objectList.add("userVO1:" + UserTransfer.INSTANCE.toConvertVO1(user));
        objectList.add("userVO1转换回实体类user:" + UserTransfer.INSTANCE.fromConvertEntity1(userVO1));
        // 输出转换结果
        objectList.add("userVO2:" + " | " + UserTransfer.INSTANCE.toConvertVO2(user));
        // 使用BeanUtils
        UserVO2 userVO22 = new UserVO2();
        BeanUtils.copyProperties(user, userVO22);
        objectList.add("userVO22:" + " | " + userVO22);
        UserVO3 userVO3 = new UserVO3();
        objectList.add("userVO3:" + UserTransfer.INSTANCE.toConvertVO3(user));
        objectList.add("userVO3 convert:" + UserTransfer.INSTANCE.fromConvertEntity3(userVO3));
        UserVO4 userVO4 = UserTransfer.INSTANCE.toConvertVO4(user);
        objectList.add("userVO4:" + userVO4);
        objectList.add("userVO4 convert:" + UserTransfer.INSTANCE.fromConvertEntity4(userVO4));
        UserVO5 userVO5 = UserTransfer.INSTANCE.toConvertVO5(new UserEnum());
        objectList.add("userVO5:" + userVO5);
        objectList.add("userVO5 convert:" + UserTransfer.INSTANCE.fromConvertEntity5(userVO5));


        return objectList;
    }
}
