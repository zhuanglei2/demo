package com.zl.demo.pattern.bridge;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/6/10 15:25
 */
public class Circle extends Shape {

    private int x,y,radius;
    public Circle(int x,int y,int radius,DrawAPI drawAPI){
        super(drawAPI);
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public void draw() {
        drawAPI.drawCircle(radius,x,y);
    }
}
