package com.huangli.jdbc.shardingjdbcdemo.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 */

@Data
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long orderId;
    private Long userId;
    private String userName;
    private String passWord;
    private UserSexEnum userSex;
    private String nickName;

}
