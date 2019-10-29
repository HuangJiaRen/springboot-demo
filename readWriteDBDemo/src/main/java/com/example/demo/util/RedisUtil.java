package com.example.demo.util;


import com.example.demo.common.Redis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Package:com.springboot.utils
 * @ClassName:RedisUtil
 * @Description:redis工具类
 **/
@EnableConfigurationProperties({Redis.class})
@Component
@Slf4j
public class RedisUtil {
    @Autowired
    private Redis redis;

    public void read() {
        System.out.println(redis.getIp());
    }


    /**
     * 非切片链接池
     */
    private JedisPool jedisPool;

    /**
     * 非切片连接池初始化
     */
    private JedisPool initialPool() {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(redis.getMaxTotal());
        config.setMaxIdle(redis.getMaxIdle());
        config.setMaxWaitMillis(redis.getMaxActive());
        config.setTestOnBorrow(redis.getTestOnBorrow());
        config.setTestOnReturn(redis.getTestOnReturn());
        jedisPool = new JedisPool(config, redis.getIp(), redis.getPort());
        return jedisPool;

    }

    /**
     * 在多线程环境同步初始化
     */
    private synchronized void poolInit() {

        if (jedisPool == null) {

            initialPool();

        }

    }

    /**
     * 非切片客户端链接 同步获取非切片Jedis实例
     *
     * @return Jedis
     */
    public synchronized Jedis getJedis() {
        if (jedisPool == null) {
            poolInit();
        }
        Jedis jedis = null;
        try {
            if (jedisPool != null) {

                jedis = jedisPool.getResource();
                // jedis.auth(password);
            }
        } catch (Exception e) {
            System.out.println("抛错");
            e.printStackTrace();
            // 释放jedis对象
            jedisPool.returnBrokenResource(jedis);
        } finally {
            // 返还连接池
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);

            }

        }
        return jedis;

    }

}
