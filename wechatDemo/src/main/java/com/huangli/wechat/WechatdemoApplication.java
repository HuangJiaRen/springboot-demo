package com.huangli.wechat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.huangli.wechat.mapper"})
public class WechatdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WechatdemoApplication.class, args);
    }

}
