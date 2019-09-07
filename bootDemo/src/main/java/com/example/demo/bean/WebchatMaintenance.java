package com.example.demo.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class WebchatMaintenance implements Serializable {
    private Long id;

    private String secretKey;

    private String appId;

    private String templateId;

    private String schoolId;
   private String mchId;
private String payKey;

private String city;


}