package com.example.demo.common;

import java.util.concurrent.TimeoutException;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2019-12-12 22:47
 */
public interface ResourceLock {


    /**
     * 阻塞试获取锁信息
     *
     * @param key        锁对象
     * @param expireTime 过期时间
     * @param timeOut    超时时间
     */
    Lock lock(String key, Long expireTime, Long timeOut) throws InterruptedException, TimeoutException;

    /**
     * 获取锁信息
     *
     * @param key        锁对象
     * @param expireTime 过期时间
     * @return
     */
    Lock tryLock(String key, Long expireTime);

    /**
     * 释放锁信息
     *
     * @param lock
     */
    void unLock(Lock lock);
}