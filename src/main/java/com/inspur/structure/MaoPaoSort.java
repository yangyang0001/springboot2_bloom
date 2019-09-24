package com.inspur.structure;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: springboot2_bloom
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-09-20 12:52
 * 算法核心:
 * 相邻的比较,大的上移
 * 时间复杂度: 1 + 2 + ... + n
 * O[n(n+1)/2]
 */
public class MaoPaoSort {

    public static void main(String[] args) {

        int[] array = {10, 3, 5, 2, 100, 88, 8};

        for(int i = 1; i < array.length; i++) {
            for(int j = 0; j < array.length - i; j++) {
                if(array[j] > array[j+1]) {
                    int temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                }
            }
        }

        for (int i : array) {
            System.out.println(i);
        }

    }
}
