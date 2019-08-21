package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author huangli
 * @version 1.0
 * @description 测试第一个定时任务
 * @date 2019-08-09 10:04
 */
@Component
@Slf4j
//@EnableScheduling
public class ScheduledTaskTest {

    @Scheduled(cron = "0/5 * * * * ?")
    public void scheduledTest(){
        log.info("第一个定时任务");
    }
}
