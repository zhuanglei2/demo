package com.zl.demo.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/6/12 10:36
 */
public class CombineUtils {
    public CombineUtils() {
    }

    public static <E> void combination(List<E> data, List<String> result) {
        int n = data.size();
        int nbit = 1 << n;

        for(int i = 0; i < nbit; ++i) {
            List<E> tmpList = new ArrayList();
            StringBuffer sb = new StringBuffer();

            for(int j = 0; j < n; ++j) {
                int tmp = 1 << j;
                if ((tmp & i) != 0) {
                    tmpList.add(data.get(j));
                    sb.append(data.get(j)).append(",");
                }
            }

            if (StringUtils.isNotEmpty(sb)) {
                sb.deleteCharAt(sb.length() - 1);
                result.add(sb.toString());
            }
        }

    }

    public static void main(String[] args) {
        String string = "GDS309054348,GDS575315529,GDS110010001";
        List<String> list = Arrays.asList(string.split(","));
        Collections.sort(list);
        List<String> result = new ArrayList();
        combination(list, result);
        Iterator var4 = result.iterator();

        while(var4.hasNext()) {
            String tmp = (String)var4.next();
            System.out.print(tmp);
            System.out.println();
        }

    }
}
