package com.example.demo.study.delegate;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2020-04-19 21:18
 */
public class DelegateTest {
    public static void main(String[] args) {
        Dispatcher dispatcher = new Dispatcher(new ExecutorA());
        dispatcher.doing();
    }
}
