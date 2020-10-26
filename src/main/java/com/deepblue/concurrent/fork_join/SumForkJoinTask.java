package com.deepblue.concurrent.fork_join;

import java.util.concurrent.RecursiveTask;

/**
 * @program: springboot2_bloom
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-09-20 22:52
 * 这里可以继承 两种类
 * 1.有返回值的情况   继承 RecursiveTask<V>
 * 2.没有返回值的情况 继承 RecursiveAction<V> 就 可以了
 */
public class SumForkJoinTask extends RecursiveTask<Integer> {

    private int startIndex;
    private int endIndex;
    private int[] array;
    private int threshold = 5;//超过当前的阈值,拆分计算


    public SumForkJoinTask(int[] array, int startIndex, int endIndex) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.array = array;
    }

    @Override
    protected Integer compute() {
        int result = 0;
        int leftResult = 0;
        int rightResult = 0;

        if(endIndex - startIndex <= threshold) {
            for (int i = startIndex; i < endIndex; i++) {
                result += array[i];
            }
        } else {
            SumForkJoinTask left = new SumForkJoinTask(array, startIndex, (startIndex + endIndex) / 2);
            SumForkJoinTask right = new SumForkJoinTask(array, (startIndex + endIndex) / 2 , endIndex);
            left.fork();
            right.fork();
            leftResult = left.join();
            rightResult = right.join();
            result = leftResult + rightResult;
        }

        return result;
    }
}
