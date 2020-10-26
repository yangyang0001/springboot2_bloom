package com.deepblue.concurrent.fork_join_merge;

import java.util.concurrent.RecursiveAction;

/**
 * @program: springboot2_bloom
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-09-22 21:27
 * 归并排序算法: 用两部分的 Fork Join 来进行
 */
public class MergeSortTask extends RecursiveAction {

    private int startIndex;
    private int endIndex;
    private int[] array;


    @Override
    protected void compute() {

    }
}
