package com.zl.demo.pattern.abstractFactory.impl;

import com.zl.demo.pattern.abstractFactory.Color;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/6/5 14:19
 */
public class Black implements Color {
    @Override
    public void fill() {
        System.out.println("Inside Black::fill() method.");
    }
}
