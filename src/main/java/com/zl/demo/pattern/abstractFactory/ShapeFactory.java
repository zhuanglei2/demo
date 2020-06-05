package com.zl.demo.pattern.abstractFactory;

import com.zl.demo.pattern.abstractFactory.impl.Circle;
import com.zl.demo.pattern.abstractFactory.impl.Rectangle;
import com.zl.demo.pattern.abstractFactory.impl.Square;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/6/5 14:22
 */
public class ShapeFactory extends AbstractFactory {
    @Override
    public Color getColor(String color) {
        return null;
    }

    @Override
    public Shape getShape(String shape) {
        if(StringUtils.isBlank(shape)){
            return null;
        }
        if("CIRCLE".equalsIgnoreCase(shape)){
            return new Circle();
        }
        if("RECTANGLE".equalsIgnoreCase(shape)){
            return new Rectangle();
        }
        if("SQUARE".equalsIgnoreCase(shape)){
            return new Square();
        }

        return null;
    }
}
