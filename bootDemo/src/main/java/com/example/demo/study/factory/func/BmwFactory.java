package com.example.demo.study.factory.func;

import com.example.demo.study.factory.Bmw;
import com.example.demo.study.factory.Car;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2020-04-19 17:50
 */
public class BmwFactory implements Factory {
    @Override
    public Car getCar() {
        return new Bmw();
    }
}
