package com.zl.demo.pattern.abstractFactory.impl;

import com.zl.demo.pattern.abstractFactory.Shape;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/6/5 14:16
 */
public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}
