package com.example.demo.study.factory.func;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2020-04-19 17:54
 */
public class FuncFactoryTest {
    public static void main(String[] args) {
        Factory factory = new AudiFactory();
        System.out.println(factory.getCar().getName());

        factory = new BmwFactory();
        System.out.println(factory.getCar().getName());
    }
}
