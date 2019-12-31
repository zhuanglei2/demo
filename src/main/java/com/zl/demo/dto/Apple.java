package com.zl.demo.dto;

import com.zl.demo.aspect.Decrypt;
import com.zl.demo.aspect.Encrypt;
import lombok.Data;
import lombok.ToString;

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
}
