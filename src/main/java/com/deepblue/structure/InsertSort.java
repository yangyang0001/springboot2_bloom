package com.deepblue.structure;

/**
 * @program: springboot2_bloom
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-09-20 14:12
 * 插入排序:
 * 核心算法: 从第二个元素开始往后一个一个的排, 每执行一次就往前插入就OK了
 */
public class InsertSort {

    public static void main(String[] args) {

        int[] array = {33, 22, 11, 23, 1, 3, 2, 11};

        for (int i = 1; i < array.length; i++) {
            int wsNum = array[i];   //将要进行插入的数据
            for (int j = 0; j < i; j++) {
                if (wsNum < array[j]) {
                    //进行插入排序
                    for (int k = i; k > j; k--) {
                        array[k] = array[k - 1];
                    }
                    array[j] = wsNum;
                    break;
                }
                System.out.print(j);
            }
            for (int a : array) {
                System.out.println(a);
            }
            System.out.println("----------------------------------------");
        }


    }
}
