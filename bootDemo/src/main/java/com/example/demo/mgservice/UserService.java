package com.example.demo.mgservice;

import com.example.demo.bean.MongoUser;
import com.example.demo.common.ServiceResult;

import java.util.List;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2019-08-01 15:20
 */
public interface UserService {
    public void saveUser(MongoUser user);
    public List<MongoUser> getUsers();

    ServiceResult getTokenCode();
}
