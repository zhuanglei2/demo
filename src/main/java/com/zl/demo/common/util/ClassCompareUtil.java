package com.zl.demo.common.util;

import com.zl.demo.aspect.CompareLogName;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhuangl
 * @date 2020年5月14日09:32:04
 */
@Slf4j
public class ClassCompareUtil {
    /**
     * 比较两个实体属性值，返回一个boolean,true则表时两个对象中的属性值无差异
     * @param oldObject 进行属性比较的对象1
     * @param newObject 进行属性比较的对象2
     * @return 属性差异比较结果boolean
     */
    public static Map<String, Map<String,Object>> compareObject(Object oldObject, Object newObject) {
        Map<String, Map<String,Object>> resultMap=compareFields(oldObject,newObject);

        return resultMap;
    }
 
    /**
     * 比较两个实体属性值，返回一个map以有差异的属性名为key，value为一个Map分别存oldObject,newObject此属性名的值
     * @param oldObject 进行属性比较的对象1
     * @param newObject 进行属性比较的对象2
     * @return 属性差异比较结果map
     */
    public static Map<String, Map<String,Object>> compareFields(Object oldObject, Object newObject) {
        Map<String, Map<String, Object>> map = null;
 
        try{
            /**
             * 只有两个对象都是同一类型的才有可比性
             */
            if (oldObject.getClass() == newObject.getClass()) {
                map = new HashMap<String, Map<String,Object>>();
 
                Class clazz = oldObject.getClass();
                //获取object的所有属性
                Field[] fields = oldObject.getClass().getDeclaredFields();
 
                for (Field field : fields) {
                    CompareLogName anno = field.getDeclaredAnnotation(CompareLogName.class);
                    if(anno==null){
                        continue;
                    }
                    //遍历获取属性名
                    String name = field.getName();
                    String logName = anno.logName();

                    // 在oldObject上调用get方法等同于获得oldObject的属性值
                    Object oldValue = getFieldValueByName(name,oldObject);
                    // 在newObject上调用get方法等同于获得newObject的属性值
                    Object newValue = getFieldValueByName(name,newObject);
 
                    if(oldValue instanceof List){
                        continue;
                    }
 
                    if(newValue instanceof List){
                        continue;
                    }
 
                    if(oldValue instanceof Timestamp){
                        oldValue = new Date(((Timestamp) oldValue).getTime());
                    }
 
                    if(newValue instanceof Timestamp){
                        newValue = new Date(((Timestamp) newValue).getTime());
                    }
 
                    if(oldValue == null && newValue == null){
                        continue;
                    }else if(oldValue == null && newValue != null){
                        Map<String,Object> valueMap = new HashMap<String,Object>();
                        if(StringUtils.isBlank(newValue.toString())){
                            continue;
                        }
                        valueMap.put("",newValue.toString());

                        map.put(logName, valueMap);
 
                        continue;
                    }
 
                    if (!oldValue.equals(newValue)) {// 比较这两个值是否相等,不等就可以放入map了
                        Map<String,Object> valueMap = new HashMap<String,Object>();
                        if(StringUtils.isBlank(newValue.toString())){
                            valueMap.put(oldValue.toString(),"");
                        }else {
                            valueMap.put(oldValue.toString(),newValue.toString());
                        }
 
                        map.put(logName, valueMap);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
 
        return map;
    }

    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return null;
        }
    }

}