package com.zl.demo.pattern.bridge;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/6/10 15:22
 */
public abstract class Shape {
    protected DrawAPI drawAPI;
    protected Shape(DrawAPI drawAPI){
        this.drawAPI = drawAPI;
    }

    public abstract void draw();
}
