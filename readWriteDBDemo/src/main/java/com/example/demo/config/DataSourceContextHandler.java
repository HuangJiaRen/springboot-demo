package com.example.demo.config;

import com.example.demo.common.DataSourceType;
import org.apache.log4j.Logger;

public class DataSourceContextHandler {
    private static Logger logger = Logger.getLogger(DataSourceContextHandler.class);
    private static final ThreadLocal<String> local = new ThreadLocal<String>();

    public static ThreadLocal<String> getLocal() {
        return local;
    }

    /**
     * 读可能是多个库
     */
    public static void read() {
        logger.debug("读操作-----");
        local.set(DataSourceType.read.getType());
    }

    /**
     * 写只有一个库
     */
    public static void write() {
        logger.debug("写操作-----");
        local.set(DataSourceType.write.getType());
    }

    public static String getJdbcType() {
        return local.get();
    }
}
