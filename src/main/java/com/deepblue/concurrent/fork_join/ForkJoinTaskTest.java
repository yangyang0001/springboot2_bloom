package com.deepblue.concurrent.fork_join;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @program: springboot2_bloom
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-09-22 18:46
 */
public class ForkJoinTaskTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //1.首先构造 int 类型的数组 长度为 2000000
        int arrayLength = 3;
        int poolSize = 4;
        int[] array = new int[arrayLength];
        Random random = new Random();

        //2.为数组中赋值
        for(int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(10);
        }

        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println("\n---------------------------------------");
        //3.查分然后计算
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        SumForkJoinTask sumForkJoinTask = new SumForkJoinTask(array, 0, arrayLength);
        ForkJoinTask<Integer> forkJoinTask = forkJoinPool.submit(sumForkJoinTask);
        System.out.println("lastResult is :" + forkJoinTask.get());
    }
}
