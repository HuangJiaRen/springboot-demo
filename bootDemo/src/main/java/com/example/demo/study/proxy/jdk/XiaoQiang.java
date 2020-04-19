package com.example.demo.study.proxy.jdk;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2020-04-18 16:45
 */
public class XiaoQiang implements Person {
    private String name = "小强";
    private String sex = "男";

    @Override
    public void findLove() {
        System.out.print("我的名字是：" + this.getName() + ",性别：" + this.getSex() + "\n");
        System.out.println("白富美");
        System.out.println("大长腿");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
