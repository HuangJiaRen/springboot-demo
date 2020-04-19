package com.example.demo.study.factory.simple;

import com.example.demo.study.factory.Audi;
import com.example.demo.study.factory.Bmw;
import com.example.demo.study.factory.Car;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2020-04-19 16:54
 */
public class SimpleFactory {
    public Car getCar(String name){
        if("Bmw".equalsIgnoreCase(name)){
            return new Bmw();
        }else if("Audi".equalsIgnoreCase(name)){
            return new Audi();
        }else {
            return null;
        }
    }
}
