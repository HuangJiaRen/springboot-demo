package com.huangli.wechat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@MapperScan({"com.huangli.wechat.mapper"})
@EnableRetry
public class WechatdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WechatdemoApplication.class, args);
    }

}
