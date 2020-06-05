package com.zl.demo.pattern.impl;

import com.zl.demo.pattern.Strategy;

/**
 * 减法
 * @author zhuangl
 * @version 1.0
 * @date 2020/6/5 11:52
 */
public class OperationSubtractByStrategy implements Strategy {
    @Override
    public int doOperation(int num1, int numb2) {
        return num1-numb2;
    }
}
