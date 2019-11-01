package com.huangli.jdbc.shardingjdbcdemo.config;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;
import com.google.common.collect.Range;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * @author huangli
 * @version 1.0
 * @description
 * 分库策略的简单实现
 * 分库算法类需要实现SingleKeyDatabaseShardingAlgorithm<T>接口，
 * 这是一个泛型接口，T代表分库依据的字段的类型，比如我们根据userId%2来分库，userId是Long型的，这里的T就是Long
 */
public class ModuloDatabaseShardingAlgorithm implements SingleKeyDatabaseShardingAlgorithm<Long> {
    @Override
    public String doEqualSharding(Collection<String> databaseNames, ShardingValue<Long> shardingValue) {
        for (String each : databaseNames) {
            if (each.endsWith(Long.parseLong(shardingValue.getValue().toString()) % 2 + "")) {
                return each;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Collection<String> doInSharding(Collection<String> databaseNames, ShardingValue<Long> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(databaseNames.size());
        for (Long value : shardingValue.getValues()) {
            for (String tableName : databaseNames) {
                if (tableName.endsWith(value % 2 + "")) {
                    result.add(tableName);
                }
            }
        }
        return result;
    }

    @Override
    public Collection<String> doBetweenSharding(Collection<String> databaseNames, ShardingValue<Long> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(databaseNames.size());
        Range<Long> range = shardingValue.getValueRange();
        for (Long i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {
            for (String each : databaseNames) {
                if (each.endsWith(i % 2 + "")) {
                    result.add(each);
                }
            }
        }
        return result;
    }
}
