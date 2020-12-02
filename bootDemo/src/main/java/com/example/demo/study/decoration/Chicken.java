package com.example.demo.study.decoration;

/**
 * @author huangli
 * @version 1.0
 * @description 鸡肉
 * @date 2020-12-02 13:44
 */
public class Chicken extends Food {
    public Chicken() {
        desc = "鸡肉";
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
