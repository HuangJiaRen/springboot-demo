package com.example.demo.study.factory.func;

import com.example.demo.study.factory.Car;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2020-04-19 17:50
 */
public interface Factory {

    //符合汽车上路标准
    //尾气排放标准
    //电子设备安全系数
    //轮胎耐磨系数
    Car getCar();

}
