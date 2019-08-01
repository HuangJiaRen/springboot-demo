package com.example.demo.mgservice;

import com.example.demo.bean.MongoUser;
import com.example.demo.mgdao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2019-08-01 15:21
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void saveUser(MongoUser user) {
        userDao.saveUser(user);
    }

    @Override
    public List<MongoUser> getUsers() {
        System.out.println(1);
        return userDao.getUsers();
    }
}
