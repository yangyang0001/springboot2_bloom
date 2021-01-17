package com.deepblue.aspect;

import java.lang.reflect.Method;

public class AnnotationTest {

    public static void main(String[] args) {

        Class<?> clazz = OneLogService.class;
        Method[] methods = clazz.getDeclaredMethods();

        for(Method method : methods){
            OneLog annotation = method.getAnnotation(OneLog.class);
            System.out.println(annotation.openFlag());
        }



    }
}
