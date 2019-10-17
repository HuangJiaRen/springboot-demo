package com.example.demo.pojo;

import lombok.Data;

@Data
public class WebchatUserReq {
    String sourceOpenId;
    String code;
    String iv;
    String encryptedData;
    String sourceId;
    String sessionKey;
    String openId;
    String schoolId;
    String accessToken;
}
