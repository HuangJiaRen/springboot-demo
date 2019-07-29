package com.example.demo.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2019-07-29 16:30
 */

@Data
public class User implements Serializable {
    private String userName;
    private int age;
    private int sex;

    public User(String userName, int age, int sex) {
        this.userName = userName;
        this.age = age;
        this.sex = sex;
    }
}
