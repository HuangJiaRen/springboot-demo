package com.example.demo.study.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2020-04-18 16:48
 */
public class MeiPo implements InvocationHandler {
    /**
     * 被代理的对象作为一个成员变量被保存
     */
    private Person target;

    /**
     * 获取被代理人的个人资料
     */
    public Object getInstance(Person target) throws Exception{
        this.target = target;
        Class clazz = target.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("开始寻找");
//        this.target.findLove();
        Object invoke = method.invoke(target, args);
//        System.out.println(invoke.getClass());
        System.out.println("寻找结束");
        return invoke;
    }
}
