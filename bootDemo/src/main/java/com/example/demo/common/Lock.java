package com.example.demo.common;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2019-12-12 22:47
 */
public class Lock {
    /**
     * 锁资源对象信息
     */
    private String key;
    /**
     * 过期时间
     */
    private Long expireTime = -1L;
    private String owner;
    private Long requestTime;
    private Long acquiredTime;
    private Long releaseTime;
    private Object internalLock;

    /**
     * 锁资源信息
     */
    private ResourceLock resourceLock;

    /**
     * 构造
     *
     * @param resourceLock
     */
    public Lock(ResourceLock resourceLock) {
        this.resourceLock = resourceLock;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Long requestTime) {
        this.requestTime = requestTime;
    }

    public Long getAcquiredTime() {
        return acquiredTime;
    }

    public void setAcquiredTime(Long acquiredTime) {
        this.acquiredTime = acquiredTime;
    }

    public Long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Long releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Object getInternalLock() {
        return internalLock;
    }

    public void setInternalLock(Object internalLock) {
        this.internalLock = internalLock;
    }

    public ResourceLock getResourceLock() {
        return resourceLock;
    }

    public void setResourceLock(ResourceLock resourceLock) {
        this.resourceLock = resourceLock;
    }
}
