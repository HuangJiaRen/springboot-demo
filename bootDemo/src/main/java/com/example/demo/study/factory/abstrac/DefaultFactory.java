package com.example.demo.study.factory.abstrac;

import com.example.demo.study.factory.Car;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2020-04-19 19:59
 */
public class DefaultFactory extends AbstractFactory{
    private AudiFactory defaultFac = new AudiFactory();

    @Override
    public Car getCar(){
        return defaultFac.getCar();
    }
}
