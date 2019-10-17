package com.example.demo.controller;

import com.example.demo.common.ServiceResult;
import com.example.demo.mgservice.UserService;
import com.example.demo.pojo.WebchatUserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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
     * 获取token
     *
     * @return
     */
    @PostMapping(value = "getTokenCode")
    public ServiceResult getTokenCode() {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult = userService.getTokenCode();
        return serviceResult;
    }

    /**
     * 获取二维码
     *
     * @param userReq
     * @return
     * @throws IOException
     */
    @PostMapping(value = "getQRCode")
    public ServiceResult getQRCode(WebchatUserReq userReq) throws IOException {
        ServiceResult serviceResult = userService.getQRCode(userReq);
        return serviceResult;
    }
}
