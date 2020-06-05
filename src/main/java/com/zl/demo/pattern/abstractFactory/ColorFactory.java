package com.zl.demo.pattern.abstractFactory;

import com.zl.demo.pattern.abstractFactory.impl.Black;
import com.zl.demo.pattern.abstractFactory.impl.Green;
import com.zl.demo.pattern.abstractFactory.impl.Red;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/6/5 14:25
 */
public class ColorFactory extends AbstractFactory {
    @Override
    public Color getColor(String color) {
        if(StringUtils.isBlank(color)){
            return null;
        }
        if("BLACK".equalsIgnoreCase(color)){
            return new Black();
        }
        if("GREEN".equalsIgnoreCase(color)){
            return new Green();
        }
        if("RED".equalsIgnoreCase(color)){
            return new Red();
        }

        return null;
    }

    @Override
    public Shape getShape(String shape) {
        return null;
    }
}
