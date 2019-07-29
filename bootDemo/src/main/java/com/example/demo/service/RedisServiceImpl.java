package com.example.demo.service;

import com.example.demo.util.RedisUtil;
import com.example.demo.util.SerializeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: RedisDaoImpl
 * @Description: TODO
 * @date 2017年10月19日 下午2:54:39
 * @version V1.0
 */
@Service
public class RedisServiceImpl implements RedisService {
    private static final String VIRTUAL_COURSE_PREX = "";
    @Autowired
    private RedisUtil redisUtil;

    private static final String RETURN_OK="OK";


    /**
     *
     * @param key
     * @return
     */
    public String buildKey(String key) {
        return VIRTUAL_COURSE_PREX + key;
    }

    /*
     * (non-Javadoc)
     *
     * java.lang.String)
     */
    @Override
    public void setString(String key, int seconds, String value) {
        Jedis jedis = redisUtil.getJedis();
        jedis.setex(buildKey(key), seconds, value);

    }

    /*
     * (non-Javadoc)
     *
     */
    @Override
    public boolean exist(String key) {
        Jedis jedis = redisUtil.getJedis();
        String bKey = buildKey(key);
        if (jedis == null || !jedis.exists(bKey)) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param key
     * @param param
     */
    @Override
    public <T> boolean setString(String key, String param) {

        Jedis jedis = redisUtil.getJedis();
        String bKey = buildKey(key);
        String set = null;
        System.out.println("jedis=============="+jedis);
        set = jedis.set(bKey.getBytes(), SerializeUtil.serialize(param));
        if (!set.isEmpty() && (RETURN_OK).equals(set)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * ��ȡStringֵ
     *
     * @param key
     * @return String
     */
    @Override
    public String getString(String key) {
        Jedis jedis = redisUtil.getJedis();
        String bKey = buildKey(key);
        String retru = null;
        if (jedis == null || !jedis.exists(bKey)) {
            return null;
        }
        byte[] in = jedis.get(bKey.getBytes());
        retru = SerializeUtil.unserialize(in).toString();
        return retru;

    }

    /**
     *
     * @param key
     * @param bean
     */
    @Override
    public <T> boolean setBean(String key, Object bean) {
        String bKey = buildKey(key);
        Jedis jedis = redisUtil.getJedis();
        String set = null;
        set = jedis.set(bKey.getBytes(), SerializeUtil.serialize(bean));
        if (!set.isEmpty() && (RETURN_OK).equals(set)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     *
     * @param key
     * @return T
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T getBean(String key) {
        Jedis jedis = redisUtil.getJedis();
        String bKey = buildKey(key);
        T bean = null;
        if (jedis == null || !jedis.exists(bKey.getBytes())) {
            return null;
        }
        byte[] in = jedis.get(bKey.getBytes());
        bean = (T) SerializeUtil.unserialize(in);
        return bean;

    }

    /**
     * @param key
     * @param list
     */
    @Override
    public <T> boolean setList(String key, List<T> list) {
        Jedis jedis = redisUtil.getJedis();
        String bKey = buildKey(key);
        String set = null;
        set = jedis.set(bKey.getBytes(), SerializeUtil.serialize(list));
        if (!set.isEmpty() && (RETURN_OK).equals(set)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     *
     * @param key
     * @return list
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> getList(String key) {
        Jedis jedis = redisUtil.getJedis();
        String bKey = buildKey(key);
        List<T> list = null;
        if (jedis == null || !jedis.exists(bKey.getBytes())) {
            return null;
        }
        byte[] in = jedis.get(bKey.getBytes());
        list = (List<T>) SerializeUtil.unserialize(in);
        return list;

    }

    /**
     *
     * @param <T>
     * @param key
     */
    @Override
    public <T> boolean setMap(String key, Map<String, T> map) {
        String bKey = buildKey(key);
        Jedis jedis = redisUtil.getJedis();
        String set = null;
        set = jedis.set(bKey.getBytes(), SerializeUtil.serialize(map));
        if (!set.isEmpty() && (RETURN_OK).equals(set)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     *
     * @param key
     * @return Map
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> Map<String, T> getMap(String key) {
        String bKey = buildKey(key);
        Jedis jedis = redisUtil.getJedis();
        Map<String, T> map = null;
        if (jedis == null || !jedis.exists(bKey.getBytes())) {
            return null;
        }
        byte[] in = jedis.get(bKey.getBytes());
        map = (Map<String, T>) SerializeUtil.unserialize(in);
        return map;
    }

    @Override
    public boolean del(String key) {
        String bKey = buildKey(key);
        Jedis jedis = redisUtil.getJedis();
        Long del = (long) 0;
        if (jedis == null || !jedis.exists(bKey.getBytes())) {
            return false;
        }
        del = jedis.del(bKey.getBytes());
        if (del > 0) {
            return true;
        }
        return false;
    }

    @Override
    public <T> boolean setInteger(String key, Integer num) {
        Jedis jedis = redisUtil.getJedis();
        String bKey = buildKey(key);
        String set = null;
        set = jedis.set(bKey, String.valueOf(num));
        if (!set.isEmpty() && (RETURN_OK).equals(set)) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public Integer getSetInteger(String key, Integer num) {
        String bKey = buildKey(key);
        Jedis jedis = redisUtil.getJedis();
        String set = null;
        set = jedis.getSet(bKey, String.valueOf(num));
        return Integer.valueOf(set);
    }

    @Override
    public Integer getInteger(String key) {
        String bKey = buildKey(key);
        Jedis jedis = redisUtil.getJedis();
        String set = null;
        if (jedis == null || !jedis.exists(bKey)) {
            return null;
        }
        set = jedis.get(bKey);
        return Integer.valueOf(set);
    }

    @Override
    public <T> boolean setHash(String key, Map<String, String> map) {
        String bKey = buildKey(key);
        Jedis jedis = redisUtil.getJedis();
        String hmset = null;
        hmset = jedis.hmset(bKey, map);
        if (!hmset.isEmpty() && (RETURN_OK).equals(hmset)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Map<String, String> getAllHash(String key) {
        String bKey = buildKey(key);
        Jedis jedis = redisUtil.getJedis();
        Map<String, String> map = null;
        if (jedis == null || !jedis.exists(bKey)) {
            return null;
        }
        map = jedis.hgetAll(bKey);
        return map;
    }

    @Override
    public List<String> getHashm(String key, String... fields) {
        String bKey = buildKey(key);
        Jedis jedis = redisUtil.getJedis();
        List<String> list = null;
        if (jedis == null || !jedis.exists(bKey)) {
            return null;
        }
        list = jedis.hmget(bKey, fields);
        return list;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.hc.redis.dao.RedisDao#login(java.lang.String, int)
     */
    @Override
    public String login(String userId, int second) {
        Jedis jedis = redisUtil.getJedis();
        String token = buildKey(userId);
        if (jedis == null || !jedis.exists(token)) {
            // token生产规则自定义ֵ
            jedis.setex(token, second, userId);
        } else {
            jedis.expire(token, second);
        }

        return token;
    }

    @Override
    public Boolean validate(String token) {
        Jedis jedis = redisUtil.getJedis();
        boolean flag = false;
        if (jedis == null || !jedis.exists(token)) {
            flag = false;
        } else {
            //重新设置有效时间
            jedis.expire(token, 10);
            flag = true;
        }
        return flag;

    }

    @Override
    public void logout(String token) {
        Jedis jedis = redisUtil.getJedis();
        if (jedis != null && jedis.exists(token)) {
            jedis.del(token);
        }

    }

    @Override
    public String getUserId(String token) {
        Jedis jedis = redisUtil.getJedis();
        String userId = null;
        if (jedis != null && jedis.exists(token)) {
            userId = jedis.get(token);
        }
        return userId;
    }

}
