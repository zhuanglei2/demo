package com.zl.demo.proxy;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/5/17 17:09
 */
public class HelloServiceImpl implements IHello {
    @Override
    public String say(String aa) {
        return this.getClass().getName();
    }
}
