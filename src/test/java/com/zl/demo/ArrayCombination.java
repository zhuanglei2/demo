package com.zl.demo;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/2/11 12:20
 */
public class ArrayCombination {
    public static List<String[]> combination(List<String[]> dataList, int index, List<String[]> resultList) {
        if (index == dataList.size()) {
            return resultList;
        }

        List<String[]> resultList0 = new ArrayList<String[]>();
        if (index == 0) {
            String[] objArr = dataList.get(0);
            for (String obj : objArr) {
                resultList0.add(new String[]{obj});

            }
        } else {
            String[] objArr = dataList.get(index);
            for (String[] objArr0 : resultList) {
                for (String obj : objArr) {
                    //复制数组并扩充新元素
                    String[] objArrCopy = new String[objArr0.length + 1];
                    System.arraycopy(objArr0, 0, objArrCopy, 0, objArr0.length);
                    objArrCopy[objArrCopy.length - 1] = obj;

                    //追加到结果集
                    resultList0.add(objArrCopy);
                }
            }
        }
        return combination(dataList, ++index, resultList0);
    }


    public static void main(String[] args) {
        String[] arr1 = new String[]{"4", "5", "6"};
        String[] arr2 = new String[]{"1,2", "1,3", "1,4", "2,3","2,4","3,4"};
        String[] arr3 = new String[]{"7", "8"};

        List<String[]> dataList = new ArrayList<String[]>();
        dataList.add(arr1);
        dataList.add(arr2);
        dataList.add(arr3);
        List<String[]> resultList = combination(dataList, 0, null);
        List<String> result = new ArrayList<>();

        //打印组合结果
        for (int i = 0; i < resultList.size(); i++) {
            String[] objArr = resultList.get(i);
            String str = StringUtils.join(objArr, ",");
            result.add(str);
        }
        result.forEach(s->{
            System.out.println(s);
        });

        /**
         输出结果：
         组合1---A1 B1 C1
         组合2---A1 B1 C2
         组合3---A1 B1 C3
         组合4---A1 B1 C4
         组合5---A1 B1 C5
         组合6---A1 B2 C1
         组合7---A1 B2 C2
         。。。

         */

    }
}
