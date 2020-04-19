package com.example.demo.study.proxy.jdk;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2020-04-18 17:03
 */
public class JDKProxyTest {
    public static void main(String[] args) throws Exception {
        Person instance = (Person) new MeiPo().getInstance(new XiaoQiang());
        instance.findLove();
    }
}
