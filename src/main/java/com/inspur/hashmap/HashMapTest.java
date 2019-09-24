package com.inspur.hashmap;

import java.util.HashMap;

/**
 * @program: springboot2_bloom
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-09-19 16:05
 * HashMap 源码解析
 *
 * 1.源码预热篇
 *
 *  Key - Value 使用的数据结构是什么呢?
 *
 *
 */
public class HashMapTest {

    public static void main(String[] args) {

        HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
        hashMap.put(1, "jack");
        System.out.println("1".hashCode() >>> 16);

        System.out.println(hashMap.get(1));
    }
}
