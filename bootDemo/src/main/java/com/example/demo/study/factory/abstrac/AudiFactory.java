package com.example.demo.study.factory.abstrac;

import com.example.demo.study.factory.Audi;
import com.example.demo.study.factory.Car;
import com.example.demo.study.factory.func.Factory;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2020-04-19 17:50
 */
public class AudiFactory extends AbstractFactory {
    @Override
    public Car getCar() {
        return new Audi();
    }
}
