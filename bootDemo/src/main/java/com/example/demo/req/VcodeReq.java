package com.example.demo.req;

import lombok.Data;

/**
 * @description:
 * @author: huangli
 * @create:
 **/
@Data
public class VcodeReq {
    private String mobile;
    private String code;
    private String school;
    private String liveUrl;
    private String checkImgCode;
   private String remoteUrl;

}
