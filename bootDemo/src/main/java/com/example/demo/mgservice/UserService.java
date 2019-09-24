package com.example.demo.mgservice;

import com.example.demo.bean.MongoUser;
import com.example.demo.common.ServiceResult;
import com.example.demo.pojo.WebchatUserReq;
import com.example.demo.req.VcodeReq;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2019-08-01 15:20
 */
public interface UserService {
     void saveUser(MongoUser user);
     List<MongoUser> getUsers();

    ServiceResult getTokenCode();

    ServiceResult getQRCode(WebchatUserReq userReq) throws IOException;

    ServiceResult getImgCode(VcodeReq vcodeReq, HttpServletRequest req, HttpServletResponse resp);
}
