package com.example.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2019-07-29 16:30
 */

@Data
@AllArgsConstructor
@ToString
public class MongoUser implements Serializable {
    private String userName;
    private int age;
    private int sex;
}
