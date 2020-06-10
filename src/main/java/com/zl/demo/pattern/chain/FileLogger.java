package com.zl.demo.pattern.chain;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/6/5 15:23
 */
public class FileLogger extends AbstractLogger {

    public FileLogger(int level){
        this.level = level;
    }

    @Override
    protected void write(String message) {
        System.out.println("File::Logger: " + message);
    }
}
