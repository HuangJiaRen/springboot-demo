package com.example.demo.study.delegate;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2020-04-19 21:15
 */
public class Dispatcher implements IExecutor {
    IExecutor executor;

    Dispatcher(IExecutor executor){
        this.executor = executor;
    }

    @Override
    public void doing() {
        this.executor.doing();
    }
}
