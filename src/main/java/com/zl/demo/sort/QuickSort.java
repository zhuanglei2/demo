package com.zl.demo.sort;

import com.alibaba.fastjson.JSON;

/**
 * 快排
 * @Author zhuanglei
 * @Date 2021/5/6 8:14 下午
 * @Version 1.0
 */
public class QuickSort {

    public static void main(String[] args) {
        int s[] = {3,-1,2,77,88,48,93,12};
        sort(s,0,s.length-1);
        System.out.println(JSON.toJSON(s));
    }

    public static void sort(int s[],int l ,int r){
        if(l<r){
            int i = l,j=r,x= s[l];
            while (i<j){
                while(i<j&&s[j]>=x){
                    j--;
                }
                if(i<j){
                    s[i++] = s[j];
                }
                while (i<j&&s[i]<x){
                    i++;
                }
                if(i<j){
                    s[j--] = s[i];
                }
            }
            s[i] =x;
            sort(s,l,i-1);
            sort(s,i+1,r);
        }
    }

}
