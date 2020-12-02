package com.example.demo.study.test;

import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2020-09-08 09:34
 */
public class TestDemo {
    public static void main(String[] args) {
        B b = new B();
        b.getName();


        int[] org = {1, 2, 3, 4};
        int[] copyOf = Arrays.copyOf(org, org.length + 1);

        System.out.println(JSONObject.toJSONString(copyOf));
    }
}
