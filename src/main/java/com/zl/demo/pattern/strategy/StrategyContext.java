package com.zl.demo.pattern.strategy;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/6/5 11:56
 */
public class StrategyContext {
    private Strategy strategy;

    public StrategyContext(Strategy strategy){
        this.strategy = strategy;
    }

    public int executeStrategy(int numb1,int numb2){
        System.out.println("请求numb1:"+numb1+",numb2:"+numb2);
        int i = strategy.doOperation(numb1,numb2);
        System.out.println("数据返回,i:"+i);
        return i;
    }
}
