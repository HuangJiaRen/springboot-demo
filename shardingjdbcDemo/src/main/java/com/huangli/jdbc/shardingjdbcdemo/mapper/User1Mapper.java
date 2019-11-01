package com.huangli.jdbc.shardingjdbcdemo.mapper;

import com.huangli.jdbc.shardingjdbcdemo.pojo.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 */
@Mapper
public interface User1Mapper {
    List<UserEntity> getAll();

    void insert(UserEntity user);
}
