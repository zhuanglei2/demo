package com.zl.demo.pattern.chain;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/6/5 15:22
 */
public class ErrorLogger extends AbstractLogger {

    public ErrorLogger(int level){
        this.level = level;
    }

    @Override
    protected void write(String message) {
        System.out.println("Error Console::Logger: " + message);
    }
}
