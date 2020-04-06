package com.huangli.wechat.controller;

import com.huangli.wechat.BlogProperties;
import com.huangli.wechat.common.ServiceResult;
import com.huangli.wechat.mapper.UserMapper;
import com.huangli.wechat.req.WebchatUserReq;
import com.huangli.wechat.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {
    @Autowired
    private BlogProperties blogProperties;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "swagger第一个接口", notes = "hello接口")
    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public String hello() {
        System.out.println(blogProperties.getTile());
        return "hello springboot!";
    }

    @ApiOperation(value = "整合mybatis", notes = "整合mybatis")
    @RequestMapping(value = "/db", method = RequestMethod.GET)
    public List<Map<String, Object>> db() {
        return userMapper.listUsers();
    }

    @ApiOperation(value = "getAccessToken", notes = "getAccessToken")
    @RequestMapping(value = "/getAccessToken", method = RequestMethod.GET)
    public ServiceResult getAccessToken(WebchatUserReq req) {
        return userService.getAccessToken(req);
    }



    @ApiOperation(value = "getQRToken", notes = "getQRToken")
    @RequestMapping(value = "/getQRToken", method = RequestMethod.POST)
    public ServiceResult getQRToken(WebchatUserReq req) throws IOException {
        return userService.getQRCode(req);
    }

    @ApiOperation(value = "sendMsg", notes = "发送订阅消息")
    @RequestMapping(value = "/sendMsg", method = RequestMethod.POST)
    public ServiceResult sendMsg(WebchatUserReq req) throws IOException {
        return userService.sendMsg(req);
    }

    @RequestMapping(value = "/jvmCpu", method = RequestMethod.GET)
    public String jvmCpu(){
        while (true){

        }

    }
}
