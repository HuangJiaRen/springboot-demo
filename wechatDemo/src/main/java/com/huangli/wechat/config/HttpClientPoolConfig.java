package com.huangli.wechat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @Author: huangli
 * @Description:
 */
@Configuration
@ConfigurationProperties(prefix = "spring.pool")
@Data
public class HttpClientPoolConfig {

    /**
     * java配置的优先级低于yml配置；如果yml配置不存在，会采用java配置
     */
    /**
     * 连接池的最大连接数
     */
    private int maxTotalConnect = 3500;
    /**
     * 同路由的并发数
     */
    private int maxConnectPerRoute = 3500;
    /**
     * 客户端和服务器建立连接超时，默认2s
     */
    private int connectTimeout = 120000;
    /**
     * 指客户端从服务器读取数据包的间隔超时时间,不是总读取时间，默认30s
     */
    private int readTimeout = 30 * 1000;

    private String charset = "UTF-8";
    /**
     * 重试次数,默认2次
     */
    private int retryTimes = 2;
    /**
     * 从连接池获取连接的超时时间,不宜过长,单位ms
     */
    private int connectionRequestTimout = 200;
    /**
     * 针对不同的地址,特别设置不同的长连接保持时间
     */
    private Map<String, Integer> keepAliveTargetHost;
    /**
     * 针对不同的地址,特别设置不同的长连接保持时间,单位 s
     */
    private int keepAliveTime = 60;

}
