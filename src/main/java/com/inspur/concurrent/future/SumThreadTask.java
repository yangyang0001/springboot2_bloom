package com.inspur.concurrent.future;

import jodd.util.concurrent.Task;

import java.util.concurrent.Callable;

/**
 * @program: springboot2_bloom
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-09-20 21:02
 */
public class SumThreadTask implements Callable<Integer> {

    private int[] array;
    private int startIndex;
    private int endIndex;

    public SumThreadTask(int[] array, int startIndex, int endIndex) {
        this.array = array;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public Integer call() throws Exception {
        int result = 0;
        for(int i = startIndex; i < endIndex; i++) {
            result += array[i];
        }
        return result;
    }
}
