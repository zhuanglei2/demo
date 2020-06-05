package com.zl.demo;

import com.zl.demo.pattern.abstractFactory.AbstractFactory;
import com.zl.demo.pattern.abstractFactory.Color;
import com.zl.demo.pattern.abstractFactory.FactoryProducer;
import com.zl.demo.pattern.abstractFactory.Shape;
import com.zl.demo.pattern.strategy.StrategyContext;
import com.zl.demo.pattern.strategy.impl.OperationAddByStrategy;
import com.zl.demo.pattern.strategy.impl.OperationMultiplyByStrategy;
import com.zl.demo.pattern.strategy.impl.OperationSubtractByStrategy;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/6/5 14:02
 */
@Slf4j
@SpringBootTest
public class PatternTest {

    /**
     * 策略模式
     */
    @Test
    public void testStrategy(){
        int numb1 = 10;
        int numb2 = 2;
        StrategyContext strategyContext = new StrategyContext(new OperationAddByStrategy());
        strategyContext.executeStrategy(numb1,numb2);
        strategyContext = new StrategyContext(new OperationSubtractByStrategy());
        strategyContext.executeStrategy(numb1,numb2);
        strategyContext = new StrategyContext(new OperationMultiplyByStrategy());
        strategyContext.executeStrategy(numb1,numb2);
    }

    /**
     * 抽象工厂模式
     */
    @Test
    public void testAbstactFactory(){
        //获取形状工厂
        AbstractFactory shapeFactory = FactoryProducer.getFactory("SHAPE");
        //获取形状为 Circle 的对象
        Shape shape1 = shapeFactory.getShape("CIRCLE");
        //调用 Circle 的 draw 方法
        shape1.draw();
        //获取形状为 Rectangle 的对象
        Shape shape2 = shapeFactory.getShape("RECTANGLE");
        //调用 Rectangle 的 draw 方法
        shape2.draw();
        //获取形状为 Square 的对象
        Shape shape3 = shapeFactory.getShape("SQUARE");
        //调用 Square 的 draw 方法
        shape3.draw();
        //获取颜色工厂
        AbstractFactory colorFactory = FactoryProducer.getFactory("COLOR");
        //获取颜色为 Red 的对象
        Color color1 = colorFactory.getColor("RED");
        //调用 Red 的 fill 方法
        color1.fill();
        //获取颜色为 Green 的对象
        Color color2 = colorFactory.getColor("Green");
        //调用 Green 的 fill 方法
        color2.fill();
        //获取颜色为 BLACK 的对象
        Color color3 = colorFactory.getColor("BLACK");
        //调用 Blue 的 fill 方法
        color3.fill();
    }
}
