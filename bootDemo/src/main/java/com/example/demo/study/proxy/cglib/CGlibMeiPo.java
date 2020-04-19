package com.example.demo.study.proxy.cglib;


import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2020-04-18 23:38
 */
public class CGlibMeiPo implements MethodInterceptor {

    /**
     * 创建代理对象
     * @param clazz
     * @return
     */
    public Object getInstance(Class<?> clazz){
        Enhancer enhancer = new Enhancer();

        // 设置父类
        enhancer.setSuperclass(clazz);

        // 设置回调函数
        enhancer.setCallback(this);

        // 创建子类（代理对象）
        return enhancer.create();
    }

    /**
     * target	代理对象
     * method	被代理对象的方法
     * args		被代理对象的参数
     */
    @Override
    public Object intercept(Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("开始寻找");
        Object invoke = methodProxy.invokeSuper(target, args);
        System.out.println("寻找结束");
        return  invoke;
    }
}
