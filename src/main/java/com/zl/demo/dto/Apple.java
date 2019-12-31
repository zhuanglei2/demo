package com.zl.demo.dto;

import com.zl.demo.aspect.Decrypt;
import com.zl.demo.aspect.Encrypt;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2019/12/31 14:56
 */
@Data
@ToString
public class Apple {

    @Encrypt
    @Decrypt
    @NotBlank(message = "姓名不能为空")
    private String name;
    @Encrypt
    @Decrypt
    private String price;
    @Encrypt
    @Decrypt
    private String amount;
    @Encrypt
    @Decrypt
    private String size;

    @Email
    private String email;
}
