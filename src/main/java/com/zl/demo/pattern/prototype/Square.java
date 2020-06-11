package com.zl.demo.pattern.prototype;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/6/11 16:53
 */
public class Square extends Shape {


    public Square(){
        type = "Square";
    }

    @Override
    void draw() {
        System.out.println("Inside Square::draw() method.");
    }
}
