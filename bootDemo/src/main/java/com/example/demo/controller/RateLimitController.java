package com.example.demo.controller;

import com.example.demo.common.MyResult;
import com.example.demo.config.RateLimit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huangli
 * @version 1.0
 * @description 2个接口设定每秒限流5个和每秒限流10个
 * @date 2019-07-22 10:07
 */
@RestController
public class RateLimitController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @PostMapping(value = "/getResults")
    @RateLimit(limitNum = 5.0)
    public MyResult getResults() {
        log.info("调用了方法getResults");
        return MyResult.OK("调用了方法", null);
    }

    @PostMapping(value = "/getResultTwo")
    @RateLimit(limitNum = 10.0)
    public MyResult getResultTwo() {
        log.info("调用了方法getResultTwo");
        return MyResult.OK("调用了方法getResultTwo", null);
    }
}
