package com.example.demo.mgservice;

import com.example.demo.bean.MongoUser;
import com.example.demo.common.ServiceResult;
import com.example.demo.mgdao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2019-08-01 15:21
 */
@Service
public class UserServiceImpl implements UserService {

    private final String appid = "wxa4bb858ebd7f1728";
    private final String appSecret = "d7c5012dfda07f40bac6b79b417a2ae2";

    @Autowired
    private RestTemplate restTemplate;

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

    @Override
    public ServiceResult getTokenCode() {
        ServiceResult serviceResult = new ServiceResult();
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+appSecret;

        String json = restTemplate.getForObject(url, String.class);
        serviceResult.setData(json);

        return serviceResult;
    }
}
