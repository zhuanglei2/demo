package com.zl.demo.pattern.chain;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/6/5 15:21
 */
public class ConsoleLogger extends AbstractLogger {

    public ConsoleLogger(int level){
        this.level = level;
    }

    @Override
    protected void write(String message) {
        System.out.println("Standard Console::Logger: " + message);
    }
}
