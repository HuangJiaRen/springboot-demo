package com.example.demo.controller;

import com.example.demo.common.Limit;
import com.example.demo.common.MyResult;
import com.example.demo.config.RateLimit;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author huangli
 * @version 1.0
 * @description 2个接口设定每秒限流5个和每秒限流10个
 * @date 2019-07-22 10:07
 */
@RestController
public class RateLimitController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();

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

    /**
     * 分布式限流
     *
     * @return
     */
    @ApiOperation(value = "分布式限流测试方法", notes = "分布式限流测试方法")
    @Limit(key = "test", period = 100, count = 10)
    @GetMapping("/limiterTest")
    public int testLimiter() {
        // 意味著 100S 内最多允許訪問10次
        return ATOMIC_INTEGER.incrementAndGet();
    }
}
