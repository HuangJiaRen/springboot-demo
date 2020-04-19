package com.example.demo.study.factory.abstrac;

import com.example.demo.study.factory.Car;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2020-04-19 19:59
 */
public abstract class AbstractFactory {
    abstract Car getCar();

    private Car getCar(String name){
        if("Bmw".equalsIgnoreCase(name)){
            return new BmwFactory().getCar();
        }else if("Audi".equalsIgnoreCase(name)){
            return new AudiFactory().getCar();
        }else {
            return null;
        }
    }
}
