package com.example.demo.mgdao;

import com.example.demo.bean.MongoUser;

import java.util.List;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2019-08-01 15:16
 */
public interface UserDao {
    void saveUser(MongoUser user);

    List<MongoUser> getUsers();
}
