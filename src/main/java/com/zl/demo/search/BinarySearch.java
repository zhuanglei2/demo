package com.zl.demo.search;

import com.alibaba.fastjson.JSON;

/**
 * @Author zhuanglei
 * @Date 2021/6/2 8:52 下午
 * @Version 1.0
 */
public class BinarySearch {

    public static void main(String[] args) {
        int[] a = {1,2,5,8,11,29};
        System.out.println(test(a,11));
        int[] b = {23,11,44,82,49,2};
        System.out.println(JSON.toJSONString(quickSort(b,0,b.length-1)));
    }

    public static int[] quickSort(int[] arr,int l ,int r){
       if(l<r){
           int i =l,j=r;
           int x = arr[l];
           while (i<j){
               while (i<j&&arr[j]>=x){
                   j--;
               }
               if(i<j){
                   arr[i++] = arr[j];
               }
               while (i<j&&arr[i]<x){
                   i++;
               }
               if(i<j){
                   arr[j--] = arr[i];
               }
           }
           arr[i] = x;
           quickSort(arr,l,i-1);
           quickSort(arr,i+1,r);
       }
       return arr;
    }


    public static int test(int[] arr,int key){
        int l = 0;
        int r = arr.length-1;
        while (l<=r){
            int mid = (l+r)/2;
            if(key==arr[mid]){
                return mid;
            }else if(arr[mid]<key){
                l = mid+1;
            }else if(arr[mid]>key){
                r = mid -1;
            }
        }
        return -1;
    }
}
