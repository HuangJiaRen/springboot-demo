package com.example.demo.service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: RedisDao
 * @Description: redis接口
 * @version V1.0
 */
public interface RedisService {

    public void setString(String key, int seconds, String value);

    public boolean exist(String key);

    /**
     * 存储String
     * @param key
     * @param param
     */
    public <T> boolean setString(String key, String param);

    /**
     * 获取Stringֵ
     * @param key
     * @return String
     */
    public String getString(String key);

    /**
     * 存储实体类
     * @param key
     * @param bean
     */
    public <T> boolean setBean(String key, Object bean);

    /**
     * 获取实体类
     * @param key
     * @return T
     */
    public <T> T getBean(String key);

    /**
     * 存储 list
     * @param key
     * @param list
     */
    public <T> boolean setList(String key, List<T> list);

    /**
     * 获取list
     * @param key
     * @return list
     */
    public <T> List<T> getList(String key);

    /**
     * 存储 map
     * @param key
     * @param map
     */
    public <T> boolean setMap(String key, Map<String, T> map);

    /**
     * 获取map
     * @param key
     * @return Map
     */
    public <T> Map<String, T> getMap(String key);

    /**
     * @param key
     * @return
     */
    public boolean del(String key);
    /**
     * @param key
     * @param num
     * @return
     */
    public <T> boolean setInteger(String key, Integer num);

    /**
     * @param key
     * @return
     */
    public Integer getSetInteger(String key, Integer num);

    /**
     * @param key
     * @return
     */
    public Integer getInteger(String key);


    /**
     * 非序列化存储
     * @param key
     * @param map
     * @return
     */
    public <T> boolean setHash(String key, Map<String, String> map);

    /**获取key中所有的map的key值
     * @param key
     * @return
     */
    public  Map<String, String> getAllHash(String key);

    /**获取key中的Map中的key=fields 的value集合
     * @param key
     * @param fields
     * @return
     */
    public List<String> getHashm(String key, String... fields);


    /**
     * @Title: login
     * @Description: TODO
     * @param  userId
     * @param  second
     * @return String 返回token
     * @throws
     */
    public String login(String userId, int second);


    public Boolean validate(String token);

    public void logout(String token);

    public String getUserId(String token);

}