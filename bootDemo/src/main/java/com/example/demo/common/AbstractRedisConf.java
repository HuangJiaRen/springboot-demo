package com.example.demo.common;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;
import java.util.List;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2019-12-12 22:21
 */
public abstract class AbstractRedisConf {
    /**
     * @description: redis 配置模板类,用户支持多个redis连接配置
     * <p>
     * 配置方式：
     * 继承该类，并为子类标注如下注解
     * <p>
     * Configuration 标注为配置类
     * EnableConfigurationProperties(DefaultRedisConfig.class)  标注为Properties类
     * ConfigurationProperties(prefix = "spring.redis")  设置配置前缀
     * <p>
     * 如需更改key或value序列化器，可在子类中重写的redisTemplate()方法中进行配置
     * 如需暴露redisConnectionFactory实例，可重写redisConnectionFactory方法，并			添加@Bean注解，注意为暴露的bean指定name
     * <p>
     *
     * <p>
     * /**
     * Database index used by the connection factory.
     */

    private final String keyPrefix = "/";

    private int database = 0;

    /**
     * 连接密码
     */
    private String password;

    /**
     * 端口号
     */
    private int port = 6379;

    /**
     * 超时时间
     */
    private Duration timeout;

    /**
     *
     */
    private Sentinel sentinel;

    private final Jedis jedis = new Jedis();


    /**
     * Pool properties.
     */
    public static class Pool {

        /**
         * Maximum number of "idle" connections in the pool. Use a negative value to
         * indicate an unlimited number of idle connections.
         */
        private int maxIdle = 5;

        /**
         * Target for the minimum number of idle connections to maintain in the pool. This
         * setting only has an effect if it is positive.
         */
        private int minIdle = 1;

        /**
         * Maximum number of connections that can be allocated by the pool at a given
         * time. Use a negative value for no limit.
         */
        private int maxActive = 10;

        /**
         * Maximum amount of time a connection allocation should block before throwing an
         * exception when the pool is exhausted. Use a negative value to block
         * indefinitely.
         */
        private Duration maxWait = Duration.ofSeconds(30);

        public int getMaxIdle() {
            return this.maxIdle;
        }

        public void setMaxIdle(int maxIdle) {
            this.maxIdle = maxIdle;
        }

        public int getMinIdle() {
            return this.minIdle;
        }

        public void setMinIdle(int minIdle) {
            this.minIdle = minIdle;
        }

        public int getMaxActive() {
            return this.maxActive;
        }

        public void setMaxActive(int maxActive) {
            this.maxActive = maxActive;
        }

        public Duration getMaxWait() {
            return this.maxWait;
        }

        public void setMaxWait(Duration maxWait) {
            this.maxWait = maxWait;
        }

    }

    /**
     * Redis sentinel properties.
     */
    public static class Sentinel {

        /**
         * Name of the Redis server.
         */
        private String master;

        /**
         * Comma-separated list of "host:port" pairs.
         */
        private List<String> nodes;

        public String getMaster() {
            return this.master;
        }

        public void setMaster(String master) {
            this.master = master;
        }

        public List<String> getNodes() {
            return this.nodes;
        }

        public void setNodes(List<String> nodes) {
            this.nodes = nodes;
        }

    }

    /**
     * Jedis client properties.
     */
    public static class Jedis {

        /**
         * Jedis pool configuration.
         */
        private Pool pool;

        public Pool getPool() {
            return this.pool;
        }

        public void setPool(Pool pool) {
            this.pool = pool;
        }

    }


    public String getKeyPrefix() {
        return keyPrefix;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Duration getTimeout() {
        return timeout;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    public Sentinel getSentinel() {
        return sentinel;
    }

    public void setSentinel(Sentinel sentinel) {
        this.sentinel = sentinel;
    }

    public Jedis getJedis() {
        return jedis;
    }

    /**
     * sentinel 配置
     */
    protected RedisSentinelConfiguration redisSentinelConfiguration() {
        RedisSentinelConfiguration sentinelConfiguration = new RedisSentinelConfiguration();

        // 设置maste 节点信息
        sentinelConfiguration.setMaster(sentinel.master);

        //节点配置
        for (String senrinls : sentinel.nodes) {
            String[] redisNode = senrinls.split(":");
            sentinelConfiguration.addSentinel(new RedisNode(redisNode[0], Integer.valueOf(redisNode[1])));
        }

//        sentinelConfiguration.setDatabase(database);
        if (password != null) {
//            sentinelConfiguration.setPassword(RedisPassword.of(password));
        }
        return sentinelConfiguration;

    }


    /**
     * jedis  连接池配置信息
     *
     * @return
     */
    protected JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(jedis.pool.maxIdle);
        jedisPoolConfig.setMinIdle(jedis.pool.minIdle);
        jedisPoolConfig.setMaxWaitMillis(jedis.pool.maxWait.toMillis());
        jedisPoolConfig.setMaxTotal(jedis.pool.maxActive);
        return jedisPoolConfig;
    }

    /**
     * redis 模板
     *
     * @return
     */
    public RedisTemplate redisTemplate() {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        //设置  redis模板的序列化操作
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);
        redisTemplate.setHashValueSerializer(redisSerializer);
        redisTemplate.setStringSerializer(redisSerializer);
        return redisTemplate;
    }

    /**
     * redis 连接工厂
     *
     * @return
     */
    public RedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.afterPropertiesSet();
        return factory;
    }
}