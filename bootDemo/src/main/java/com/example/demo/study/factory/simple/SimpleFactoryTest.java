package com.example.demo.study.factory.simple;

import com.example.demo.study.factory.Car;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2020-04-19 16:56
 */
public class SimpleFactoryTest {
    public static void main(String[] args) {
        Car car = new SimpleFactory().getCar("audi");
        System.out.println(car.getName());
    }
}
