package com.deepblue.concurrent.fork_join_merge;

/**
 * @program: springboot2_bloom
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-09-22 21:25
 */
public class TestMethod {

    public static void main(String[] args) {
        int[] a = {1, 3, 4};
        sort(a);
        for (int i : a) {
            System.out.println(i);
        }
    }

    public static int[] sort(int[] a) {
        a[1] =100;
        return a;
    }
}
