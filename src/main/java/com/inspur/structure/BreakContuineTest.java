package com.inspur.structure;

/**
 * @program: springboot2_bloom
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-09-20 16:36
 * break : 结束本层循环
 * continue : 结束层中的 一次循环 进入本层中的下一次循环
 */
public class BreakContuineTest {

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(j == 4) {
                    continue;
                }
                System.out.print(j);
            }
            System.out.println("--------------------------------");
            System.out.println(i);
        }

    }
}
