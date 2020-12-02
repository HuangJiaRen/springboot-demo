package com.example.demo.common;

import com.example.demo.util.DateUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class UserInfo implements Serializable {

    private final String timestamp = DateUtils.toString(new Date(), DateUtils.YYYY_MM_DD_HH_MM_SS);

    private Long uaId;

    private String uaEmail;

    private Integer uaAccountType;

    private Integer uaRoleId;

    private List<String> schoolIdLt;
}
