package com.example.demo.aop;

import com.example.demo.config.DataSourceContextHandler;
import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(-1)// 保证该AOP在@Transactional之前执行
@Component
public class DataSourceAop {
    private static Logger logger = Logger.getLogger(DataSourceAop.class);
    @Before("execution(* com.example.demo.*.mapper..*.select*(..)) || execution(* com.example.demo.*.mapper..*.get*(..))|| execution(* com.example.demo.*.mapper..*.query*(..))")
    public void setReadDataSourceType() {
        DataSourceContextHandler.read();
        logger.info("dataSource 切换到：Read");
    }

    @Before("execution(* com.example.demo.*.mapper..*.insert*(..)) || execution(* com.example.demo.*.mapper..*.update*(..))")
    public void setWriteDataSourceType() {
        DataSourceContextHandler.write();
        logger.info("dataSource 切换到：Write");
    }
}
