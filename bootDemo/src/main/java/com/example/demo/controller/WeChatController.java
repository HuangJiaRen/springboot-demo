package com.example.demo.controller;

import com.example.demo.common.ServiceResult;
import com.example.demo.mgservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2019-09-23 17:48
 */
@RestController
public class WeChatController {

    @Autowired
    private UserService userService;

    /**
     *
     * @return
     */
    @PostMapping(value = "getTokenCode")
    public ServiceResult getTokenCode(){
        ServiceResult serviceResult = new ServiceResult();
        serviceResult = userService.getTokenCode();
        return serviceResult;
    }
}
