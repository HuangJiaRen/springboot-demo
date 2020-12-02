package com.example.demo.study.decoration;

/**
 * @author huangli
 * @version 1.0
 * @description 客户端
 * @date 2020-12-02 13:54
 */
public class Client {
    public static void main(String[] args) {
        // 测试单纯的食物
        Food f1 = new Chicken();
        System.out.println(f1.getDesc());

        System.out.println("----------------------");
        // 测试单重修饰的食物
        RoastFood rf = new RoastFood(f1);
        System.out.println(rf.getDesc());

        System.out.println("----------------------");
        // 测试多重修饰的食物
        SteamedFood sf = new SteamedFood(rf);
        System.out.println(sf.getDesc());
    }
}
