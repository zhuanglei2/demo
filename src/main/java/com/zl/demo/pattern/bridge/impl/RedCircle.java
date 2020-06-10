package com.zl.demo.pattern.bridge.impl;

import com.zl.demo.pattern.bridge.DrawAPI;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/6/10 15:21
 */
public class RedCircle implements DrawAPI {
    @Override
    public void drawCircle(int radius, int x, int y) {
        System.out.println("Drawing Circle[ color: red, radius: "
                + radius +", x: " +x+", "+ y +"]");
    }
}
