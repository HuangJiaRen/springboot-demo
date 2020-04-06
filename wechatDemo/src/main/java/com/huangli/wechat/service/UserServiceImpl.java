package com.huangli.wechat.service;

import com.alibaba.fastjson.JSONObject;
import com.huangli.wechat.common.ServiceResult;
import com.huangli.wechat.pojo.User;
import com.huangli.wechat.req.WebchatUserReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2019-10-17 17:20
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private static final String appId = "wxa4bb858ebd7f1728";
    private static final String appSecret = "d7c5012dfda07f40bac6b79b417a2ae2";
    private static final String getAccessTokenStr = "https://api.weixin.qq.com/cgi-bin/token";
    private static final String getQRTokenStr = "https://api.weixin.qq.com/wxa/getwxacodeunlimit";

    private String accessToken = "26_XFc5UNyB_IxQB-CsDjt_yGXujoa1oWvQWF68cvQV8Ul0LZfYRXnJ4L-UWao58Zz8C4TIKx_hOCnROCODrlMWM5hs0ZN1vMeJtpOdJ0VnDmvsoUs5qLFEQ4CpY4cUoqmpEcbPgJwQ3E5m1jejFLLaAGAOUU";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void insertUser(User user) {

    }

    @Override
    public ServiceResult getAccessToken(WebchatUserReq req) {
        ServiceResult serviceResult = new ServiceResult();
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + appSecret;

        String json = restTemplate.getForObject(url, String.class);
        serviceResult.setData(json);

        return serviceResult;
    }

    @Override
    public ServiceResult getQRCode(WebchatUserReq req) throws IOException {
        ServiceResult serviceResult = new ServiceResult();
        URL url = new URL(getQRTokenStr + "?access_token=" + req.getAccessToken());

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

        log.info(pre + encode);
        serviceResult.setData(pre + encode);

        return serviceResult;
    }

    @Override
    public ServiceResult sendMsg(WebchatUserReq req) {
        ServiceResult serviceResult = new ServiceResult();
        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token="+accessToken;

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("access_token", req.getAccessToken());
        params.put("touser", req.getTouser());
        params.put("template_id", req.getTemplateId());
        params.put("page", req.getPage());
        params.put("data", req.getData());
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, req, String.class);
        serviceResult.setData(responseEntity);

        return serviceResult;
    }

    private String getRule4Similar() {
        StringBuffer sb = new StringBuffer();
        sb.append("type1").append("@");
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
