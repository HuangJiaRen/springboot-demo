package com.huangli.wechat.service;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2020-09-18 16:42
 */

@Service
public class TestRetryServiceImpl implements TestRetryService {

    @Override
    @Retryable(value = Exception.class,maxAttempts = 3,backoff = @Backoff(delay = 2000,multiplier = 1.5))
    public int dignifiedTest(int code) throws Exception{
        System.out.println("dignifiedTest被调用,时间："+ LocalTime.now());
        if (code==0){
            throw new Exception("情况不对头！");
        }
        System.out.println("dignifiedTest被调用,情况对头了！");

        return 200;
    }

    @Recover
    public int recover(Exception e){
        System.out.println("回调方法执行！！！！");
        //记日志到数据库 或者调用其余的方法
        return 400;
    }

}
