package com.example.demo.study.proxy.staticproxy;

/**
 * 需要被代理的类 实现了一个接口Person
 * @author
 *
 */
public class Student implements Person2{

    @Override
    public void sayHello(String content, int age) {
        System.out.println("student say hello" + content + " "+ age);
    }

    @Override
    public void sayGoodBye(boolean seeAgin, double time) {
        System.out.println("student sayGoodBye " + time + " "+ seeAgin);
    }

}
