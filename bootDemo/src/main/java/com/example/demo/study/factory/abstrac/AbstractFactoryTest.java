package com.example.demo.study.factory.abstrac;


/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2020-04-19 19:59
 */
public abstract class AbstractFactoryTest {
    public static void main(String[] args) {
        DefaultFactory defaultFactory = new DefaultFactory() ;
        System.out.println(defaultFactory.getCar().getName());
        System.out.println(new BmwFactory().getCar().getName());
    }
}
