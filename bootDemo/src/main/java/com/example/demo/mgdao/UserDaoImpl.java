package com.example.demo.mgdao;

import com.example.demo.bean.MongoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2019-08-01 15:17
 */

@Component
public class UserDaoImpl implements UserDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void saveUser(MongoUser user) {
        mongoTemplate.save(user);
    }

    @Override
    public List<MongoUser> getUsers() {
        return mongoTemplate.findAll(MongoUser.class);
    }
}
