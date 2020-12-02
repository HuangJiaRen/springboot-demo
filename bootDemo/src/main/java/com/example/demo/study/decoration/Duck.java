package com.example.demo.study.decoration;

/**
 * @author huangli
 * @version 1.0
 * @description 鸭肉
 * @date 2020-12-02 13:45
 */
public class Duck extends Food {
    public Duck() {
        desc = "鸭肉";
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
