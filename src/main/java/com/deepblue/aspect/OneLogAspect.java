package com.deepblue.aspect;


import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect
public class OneLogAspect {

    @Around("@annotation(com.deepblue.aspect.OneLog)")
    public Object doOneLogAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        Class<?> targetClazz = proceedingJoinPoint.getTarget().getClass();
        String methodName = proceedingJoinPoint.getSignature().getName();
        Class<?>[] signatures = ((MethodSignature) proceedingJoinPoint.getSignature()).getParameterTypes();
        Method targetMethod = targetClazz.getMethod(methodName, signatures);
        Object[] paramValues = proceedingJoinPoint.getArgs();
        Object[] paramNames = ((CodeSignature) proceedingJoinPoint.getSignature()).getParameterNames();

        System.out.println(targetMethod.getName() + " invoked before");
        if(paramNames != null && paramNames.length > 0) {
            for(int i = 0; i < paramNames.length; i++) {
                System.out.println(paramNames[i] + " : " + paramValues[i]);
            }
        }

        Object result = proceedingJoinPoint.proceed();
        System.out.println("result  :" + JSON.toJSONString(result));
        /**
         * proceed  和 proceed(Object[] args) 二者执行之一就OK了
         */
//        Object proceed = proceedingJoinPoint.proceed(paramValues);
//        System.out.println("proceed :" + proceed);

        System.out.println(targetMethod.getName() + " invoked after");

        return result;
    }


}
