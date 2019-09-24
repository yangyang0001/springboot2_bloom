package com.inspur.hashmap;

import java.util.TreeMap;

/**
 * @program: springboot2_bloom
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-09-19 19:39
 */
public class TreeMapTest {

    public static void main(String[] args) {
        TreeMap<String, String> treeMap = new TreeMap<>();

        treeMap.put("1", "1");
        treeMap.put(null, "2");

        treeMap.forEach((key, value) -> {
            System.out.println(key + " : " + value);
        });


    }
}
