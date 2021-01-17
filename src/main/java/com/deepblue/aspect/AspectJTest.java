package com.deepblue.aspect;

import org.apache.naming.factory.BeanFactory;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

public class AspectJTest {

    public static void main(String[] args) {

        OneLogService service = new OneLogService();

        AspectJProxyFactory proxyFactory = new AspectJProxyFactory();
        proxyFactory.setTarget(service);
        proxyFactory.addAspect(OneLogAspect.class);

        OneLogService proxyService = proxyFactory.getProxy();

        proxyService.first();
        System.out.println("------------------------------------------------------------");
        proxyService.second();
        System.out.println("------------------------------------------------------------");
        proxyService.getResult("zhangsan", "199001");
        System.out.println("------------------------------------------------------------");

    }
}
