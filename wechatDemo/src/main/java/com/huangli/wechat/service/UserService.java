package com.huangli.wechat.service;

import com.huangli.wechat.common.ServiceResult;
import com.huangli.wechat.pojo.User;
import com.huangli.wechat.req.WebchatUserReq;

import java.io.IOException;
import java.net.MalformedURLException;

public interface UserService {
    void insertUser(User user);

    ServiceResult getAccessToken(WebchatUserReq req);

    ServiceResult getQRCode(WebchatUserReq req) throws IOException;

}
