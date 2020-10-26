package com.deepblue.structure;

/**
 * @program: springboot2_bloom
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-09-20 13:11
 * 算法核心:
 * 每次选择最大或最小的出来, 每轮循环放在第i的位置
 * 时间复杂度: n-1 + n-2 + ... + 1 = n (n-1) / 2  为 O[n(n-1)/2]
 */
public class XuanZeSort {

    public static void main(String[] args) {
        int[] array  = {7, 200, 2, 33, 3, 5, 11, 100, 22};

        for (int i = 0; i < array.length-1; i++) { //每次循环确定一个最大的值,放在最后
            for(int j = i+1; j < array.length; j++) {
                if(array[i] < array[j]) {
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }

        for (int i : array) {
            System.out.println(i);
        }

    }
}
