package com.example.demo.config;

import com.example.demo.common.DataSourceType;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author huangli
 * @package:com.ganinfo.datasource
 * @className:MyAbstractRoutingDataSource
 * @description:多数据源切换
 **/

public class MyAbstractRoutingDataSource extends AbstractRoutingDataSource {

    private final int dataSourceNumber;
    private AtomicInteger count = new AtomicInteger(0);

    public MyAbstractRoutingDataSource(int dataSourceNumber) {
        this.dataSourceNumber = dataSourceNumber;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        String typeKey = DataSourceContextHandler.getJdbcType();
        // 写
        if (typeKey.equals(DataSourceType.write.getType())) {
            return DataSourceType.write.getType();
        }
        // 读简单负载均衡
        int number = count.getAndAdd(1);
        int lookupKey = number % dataSourceNumber;
        return new Integer(lookupKey);
    }
}
