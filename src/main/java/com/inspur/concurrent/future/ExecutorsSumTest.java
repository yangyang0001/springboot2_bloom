package com.inspur.concurrent.future;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @program: springboot2_bloom
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-09-20 21:17
 */
public class ExecutorsSumTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //1.首先构造 int 类型的数组 长度为 2000000
        int arrayLength = 2000000;
        int poolSize = 4;
        int[] array = new int[arrayLength];
        Random random = new Random();

        //2.为数组中赋值
        for(int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(10);
        }

//        for (int i : array) {
//            System.out.print(i + " ");
//        }
//        System.out.println("\n---------------------------------------");
        //3.查分然后计算
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        int lastResult = 0;
        for (int i = 1; i <= 4; i++) {
            Future<Integer> result = executorService.submit(new SumThreadTask(array, arrayLength/poolSize * (i-1), arrayLength/poolSize * i));
            lastResult += result.get();
        }

        executorService.shutdown();
        System.out.println(lastResult);
    }
}
