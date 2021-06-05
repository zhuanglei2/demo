package com.zl.demo.sort;

import com.alibaba.fastjson.JSON;

/**
 * @Author zhuanglei
 * @Date 2021/5/28 3:08 下午
 * @Version 1.0
 */
public class QuickSortTest {

    public static void main(String[] args) {
        int s[] = {3,-1,2,77,88,48,93,12};
        quickSort(s,0,s.length-1);
        System.out.println(JSON.toJSON(s));
    }

    public static void quickSort(int[] nums, int l, int r){
        if(l<r){
            int i = l,j = r;
            int x = nums[l];
            while (i<j){
                while(i<j&&nums[j]>=x){
                    j--;
                }
                if(i<j){
                    nums[i++] = nums[j];
                }
                while (i<j&&nums[i]<x){
                    i++;
                }
                if(i<j){
                    nums[j--] = nums[i];
                }
            }
            nums[i] = x;
            quickSort(nums,l,i-1);
            quickSort(nums,i+1,r);
        }
    }
}
