package com.huangli.jdbc.shardingjdbcdemo.controller;

import com.huangli.jdbc.shardingjdbcdemo.pojo.UserEntity;
import com.huangli.jdbc.shardingjdbcdemo.pojo.UserSexEnum;
import com.huangli.jdbc.shardingjdbcdemo.service.User1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 */

@Service
@RestController
public class UserController {
    @Autowired
    private User1Service user1Service;

    @PostMapping("/getUsers")
    public List<UserEntity> getUsers() {
        List<UserEntity> users=user1Service.getUsers();
        return users;
    }

    //测试
    @PostMapping(value="update1")
    public String updateTransactional(@RequestParam(value = "id") Long id,
                                      @RequestParam(value = "user_id") Long userId,
                                      @RequestParam(value = "order_id") Long orderId,
                                      @RequestParam(value = "nickName") String nickName,
                                      @RequestParam(value = "passWord") String passWord,
                                      @RequestParam(value = "userName") String userName
    ) {
        UserEntity user2 = new UserEntity();
        user2.setId(id);
        user2.setUserId(userId);
        user2.setOrderId(orderId);
        user2.setNickName(nickName);
        user2.setPassWord(passWord);
        user2.setUserName(userName);
        user2.setUserSex(UserSexEnum.WOMAN);
        user1Service.updateTransactional(user2);
        return "test";
    }
}
