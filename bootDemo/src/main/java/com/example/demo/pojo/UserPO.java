package com.example.demo.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class UserPO {
    private Integer id;

    private String sourceOpenId;

    private String openId;

    private String userName;

    private String nickName;

    private String gender;

    private String city;

    private String province;

    private String country;

    private String avatarUrl;

    private String unionId;

    private String userTel;

    private String userEmail;

    private Integer userStatus;

    private Integer freeNum;

    private String schoolZone;

    private String grade;

    private Date createTime;
    //临时变量
    private String sessionKey;

    private static final long serialVersionUID = 1L;
}
