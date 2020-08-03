package com.example.demo.study.proxy.staticproxy;


/**
 * @author huangli
 * @version 1.0
 * @description 静态代理
 * @date 2020-08-02 16:27
 */
public class ProxyTest implements Person2 {
    private Person2 o;

    public ProxyTest(Person2 o){
        this.o = o;
    }

    public static void main(String[] args) {
        //s为被代理的对象，某些情况下 我们不希望修改已有的代码，我们采用代理来间接访问
        Student s = new Student();
        //创建代理类对象
        ProxyTest proxy = new ProxyTest(s);
        //调用代理类对象的方法
        proxy.sayHello("welcome to java", 20);
        System.out.println("******");
        //调用代理类对象的方法
        proxy.sayGoodBye(true, 100);

    }

    @Override
    public void sayHello(String content, int age) {
        System.out.println("ProxyTest sayHello begin");
        //在代理类的方法中 间接访问被代理对象的方法
        o.sayHello(content, age);
        System.out.println("ProxyTest sayHello end");

    }

    @Override
    public void sayGoodBye(boolean seeAgin, double time) {
        System.out.println("ProxyTest sayGoodBye begin");
        //在代理类的方法中 间接访问被代理对象的方法
        o.sayGoodBye(seeAgin, time);
        System.out.println("ProxyTest sayGoodBye end");
    }
}
