package com.example.demo.study.delegate;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2020-04-19 21:14
 */
public class ExecutorA implements IExecutor {
    @Override
    public void doing() {
        System.out.println("A在工作");
    }
}
