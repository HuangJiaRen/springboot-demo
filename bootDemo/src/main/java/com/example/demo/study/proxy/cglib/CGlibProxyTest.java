package com.example.demo.study.proxy.cglib;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2020-04-18 23:44
 */
public class CGlibProxyTest {
    public static void main(String[] args) {
        ZhangSan zhangSan = (ZhangSan) new CGlibMeiPo().getInstance(ZhangSan.class);
        zhangSan.findLove();
    }
}
