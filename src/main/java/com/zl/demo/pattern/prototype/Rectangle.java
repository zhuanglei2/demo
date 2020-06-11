package com.zl.demo.pattern.prototype;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/6/11 16:51
 */
public class Rectangle extends Shape {

    public Rectangle(){
        type = "Rectangle";
    }

    @Override
    void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }
}
