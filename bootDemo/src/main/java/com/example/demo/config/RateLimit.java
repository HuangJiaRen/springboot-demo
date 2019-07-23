package com.example.demo.config;

import java.lang.annotation.*;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2019-07-22 09:55
 */

@Inherited
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    double limitNum() default 20;  //默认每秒放入桶中的token
}
