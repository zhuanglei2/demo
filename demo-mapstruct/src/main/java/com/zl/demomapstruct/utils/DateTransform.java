package com.zl.demomapstruct.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/3/9 11:50
 */
public class DateTransform {
    public static LocalDateTime strToDate(String str){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse("2018-01-12 17:07:05",df);
    }
}
