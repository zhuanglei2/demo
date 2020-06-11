package com.zl.demo.pattern.prototype;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/6/11 16:53
 */
public class Circle extends Shape {


    public Circle(){
        type = "Circle";
    }

    @Override
    void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}
