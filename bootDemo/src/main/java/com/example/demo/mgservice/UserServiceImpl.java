package com.example.demo.mgservice;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.bean.ImgCode;
import com.example.demo.bean.MongoUser;
import com.example.demo.common.HttpResultEnum;
import com.example.demo.common.ServiceResult;
import com.example.demo.mgdao.UserDao;
import com.example.demo.pojo.WebchatUserReq;
import com.example.demo.req.VcodeReq;
import com.example.demo.util.VerifyCodeUtils;
import com.google.common.collect.Maps;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2019-08-01 15:21
 */
@Service
public class UserServiceImpl implements UserService {

    private final String appid = "wxa4bb858ebd7f1728";
    private final String appSecret = "d7c5012dfda07f40bac6b79b417a2ae2";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserDao userDao;

    @Override
    public void saveUser(MongoUser user) {
        userDao.saveUser(user);
    }

    @Override
    public List<MongoUser> getUsers() {
        System.out.println(1);
        return userDao.getUsers();
    }

    @Override
    public ServiceResult getTokenCode() {
        ServiceResult serviceResult = new ServiceResult();
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+appSecret;

        String json = restTemplate.getForObject(url, String.class);
        serviceResult.setData(json);

        return serviceResult;
    }

    @Override
    public ServiceResult getQRCode(WebchatUserReq userReq) throws IOException {
        ServiceResult serviceResult = new ServiceResult();
        URL url = new URL("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + userReq.getAccessToken());
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");// 提交模式
        // conn.setConnectTimeout(10000);//连接超时 单位毫秒
        // conn.setReadTimeout(2000);//读取超时 单位毫秒
        // 发送POST请求必须设置如下两行
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        // 获取URLConnection对象对应的输出流
        PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
        // 发送请求参数
        JSONObject paramJson = new JSONObject();
        paramJson.put("scene", getRule4Similar());
        printWriter.write(paramJson.toString());
        // flush输出流的缓冲
        printWriter.flush();
        //开始获取数据
        InputStream inputStream = httpURLConnection.getInputStream();
        String encode = getBase64FromInputStream(inputStream);

        String pre = "data:image/png;base64,";

        System.out.println(pre + encode);
        serviceResult.setData(pre + encode);
        return serviceResult;
    }

    @Override
    public ServiceResult getImgCode(VcodeReq vcodeReq, HttpServletRequest req, HttpServletResponse resp) {
        ServiceResult serviceResult = new ServiceResult();
        if (StringUtils.isBlank(vcodeReq.getMobile())) {
            return new ServiceResult(HttpResultEnum.FAIL.getStatus(), "手机号不能为空");
        }
        //生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        //生成图片
        int width = 100;//宽
        int height = 40;//高
        try {
            String outputImage = VerifyCodeUtils.outputImage(width, height, resp.getOutputStream(), verifyCode);
            ImgCode imgCode = setImgCode(vcodeReq, verifyCode, outputImage);
            serviceResult.setData(imgCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serviceResult;
    }

    /**
     * redis保存手机
     *
     * @param verifyCode
     * @param outputImage
     * @return
     */
    private ImgCode setImgCode(VcodeReq vcodeReq, String verifyCode, String outputImage) {
        ImgCode imgCode = new ImgCode();
        imgCode.setCode(verifyCode);
        String pre = "data:image/png;base64,";
        imgCode.setCodeImgBase64(pre + outputImage);
//        redisManage.putValueExpireTimes(BussinessConstants.IMG_CODE_REDIS_KEY_PRE
//                + vcodeReq.getMobile(), verifyCode, 1800l);
        return imgCode;
    }

    private String getRule4Similar() {
        StringBuffer sb = new StringBuffer();
        sb.append("type6").append("@");
        return sb.toString();
    }

    public static String getBase64FromInputStream(InputStream in) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        // 读取图片字节数组
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = in.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            data = swapStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new String(Base64.encodeBase64(data));
    }
}
