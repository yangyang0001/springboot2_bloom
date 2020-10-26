package com.deepblue.structure;

/**
 * @program: springboot2_bloom
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-09-22 19:58
 * 归并排序是对两个 排好序的 数组进行排序的过程!
 */
public class MergeSortTest {

    public static void main(String[] args) {

        int[] a = {1, 3, 4, 6, 10};
        int[] b = {3, 5, 7, 10, 11, 22, 33};
        int[] result = merge(a, b);
        for (int i : result) {
            System.out.println(i);
        }
    }

    public static int[] merge(int[] a, int[] b) {
        int ai = 0;
        int bi = 0;
        int ci = 0;
        int[] c = new int[a.length + b.length];

        while(ai < a.length && bi < b.length) {
            if(a[ai] < b[bi]) {
                c[ci++] = a[ai++];
            } else {
                c[ci++] = b[bi++];
            }
        }

        while (ai < a.length) {
            c[ci++] = a[ai++];
        }

        while (bi < b.length) {
            c[ci++] = b[bi++];
        }

        return  c;
    }
}
