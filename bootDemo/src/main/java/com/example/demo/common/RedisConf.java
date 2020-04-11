package com.example.demo.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2019-12-12 22:21
 */
@Configuration
@EnableConfigurationProperties(RedisConf.class)
@ConfigurationProperties(prefix = "frame.redis")
public class RedisConf  extends AbstractRedisConf {

    /**
     * springboot  初始化元数据加载
     * 官方地址:https://docs.spring.io/spring-boot/docs/2.1.5.RELEASE/reference/html/configuration-metadata
     * .html#configuration-metadata-annotation-processor
     * redis 连接工厂
     *
     * @return
     */
    @Override
    @Bean(name = "LockRedis")
    public RedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.afterPropertiesSet();
        return factory;
    }
}