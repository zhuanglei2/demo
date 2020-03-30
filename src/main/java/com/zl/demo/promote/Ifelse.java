package com.zl.demo.promote;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * if else优化
 * 解决办法
 * 1.表驱动
 * 2.职责链模式
 * 3.注解驱动
 * 4.事件驱动
 * 5.有限状态机
 * 6.Optional
 * 7.Assert
 * 8.多态
 * @author zhuangl
 * @version 1.0
 * @date 2020/3/18 19:22
 */
public class Ifelse {
    public static void main(String[] args) {
        String param = "1";
        String value1= "1";
        String value2= "2";
        String value3= "3";
        String someParams="aa";
        /**避免过多的Ifelse*/
//        if (param.equals(value1)) {
//            doAction1(someParams);
//        } else if (param.equals(value2)) {
//            doAction2(someParams);
//        } else if (param.equals(value3)) {
//            doAction3(someParams);
//        }
//        Map<String, Function<String,String>> actionMappings = new HashMap<>(); // 这里泛型 ? 是为方便演示，实际可替换为你需要的类型
//        actionMappings.put(value1, (someParam) -> { doAction1(someParams);});
//        actionMappings.put(value2, (someParam) -> { doAction2(someParams);});
//        actionMappings.put(value3, (someParam) -> { doAction3(someParams);});
//        actionMappings.get(param).apply(someParams);
    }

    private static void doAction2(String someParams) {
        System.out.println("执行方法doAction2，打印参数"+someParams);
    }

    private static void doAction3(String someParams) {
        System.out.println("执行方法doAction3，打印参数"+someParams);
    }

    private static void doAction1(String someParams) {
        System.out.println("执行方法doAction1，打印参数"+someParams);
    }
}
