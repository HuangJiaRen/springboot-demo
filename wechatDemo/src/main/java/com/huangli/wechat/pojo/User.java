package com.huangli.wechat.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {
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
//注册的手机号
    private String mobile;
//    private WebchatStudent student;
//老学员手机号  备课系统返回 保存
    private String studentMobile;

    private String stuNum;

}