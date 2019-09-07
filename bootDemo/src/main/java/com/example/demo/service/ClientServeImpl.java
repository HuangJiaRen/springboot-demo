package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.bean.User;
import com.example.demo.bean.WebchatMaintenance;
import com.example.demo.common.HttpResultEnum;
import com.example.demo.common.ServiceResult;
import com.example.demo.pojo.UserPO;
import com.example.demo.pojo.WebchatUserReq;
import com.example.demo.util.AesCbuUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
@Transactional(rollbackFor = Exception.class)
public class ClientServeImpl implements ClientServe {
	private final static Logger logger = LoggerFactory
			.getLogger(ClientServeImpl.class);

	// 你的小程序的session-key;
	@Value("${WechatSecretKey}")
	private String wechatSecretKey;

	// 你的小程序的AppID
	@Value("${WechatAppId}")
	private String wechatAppId;

	// 你的小程序的AppID
	@Value("${WebchatUrl}")
	private String webchatUrl;




	// 兼容方案;
	@Value("${teacher.appid}")
	private String teacherAppId;


	@Value("${teacher.secret}")
	private String teacherSecret;

	@Autowired
	UserMapper userMapper;

	@Autowired
	ShareMapper shareMapper;
	
	
	@Autowired
	ChannelStatisticMapper channelStatisticMapper;
	
	@Autowired
	SourceMapper sourceMapper;
	@Autowired
	private WebchatMaintenanceService webchatMaintenanceService;


	@Autowired
    RestTemplate restTemplate;

	@Override
	public ServiceResult authority(WebchatUserReq user) throws Exception {
		// TODO Auto-generated method stub
		ServiceResult result = null;

		String sourceOpenId = user.getSourceOpenId();
		String sourceId = user.getSourceId();
		String openId = user.getOpenId();
		String code = user.getCode();
		String encryptedData = user.getEncryptedData();
		String iv = user.getIv();
		if (StringUtils.isBlank(sourceOpenId)) {
			sourceOpenId = null;
		}
		if (StringUtils.isBlank(sourceId)) {
			sourceId = null;
		}

		if (StringUtils.isBlank(openId)) {
			openId = null;
		}

		// login code can not be null
		if (StringUtils.isBlank(code)) {
			code = null;
		}

		// 需要解析昵称等信息时，必填
		if (StringUtils.isBlank(encryptedData)) {
			encryptedData = null;
		}
		if (StringUtils.isBlank(iv)) {
			iv = null;
		}

		if (StringUtils.isBlank(user.getSchoolId())) {
			return new ServiceResult(102, "schoolId不能为空");
		}
		WebchatMaintenance webchatMaintenance = webchatMaintenanceService.getBySchoolId(user.getSchoolId());

		String encryptedate = null;
		String sessionKey = null;
		if (openId == null) { // 首次授权
			String grantType = "authorization_code";

			String params = "appid=" + webchatMaintenance.getAppId() + "&secret="
					+ webchatMaintenance.getSecretKey() + "&js_code=" + user.getCode()
					+ "&grant_type=" + grantType;

			String url = webchatUrl + "?" + params;

			// 进行网络请求,访问url接口
			ResponseEntity<String> responseEntity = restTemplate.exchange(url,
					HttpMethod.GET, null, String.class);
			// 根据返回值进行后续操作
			if (responseEntity != null
					&& responseEntity.getStatusCode() == HttpStatus.OK) {
				logger.info("authority 微信返回值" + JSONObject.toJSONString(responseEntity));
				String sessionData = responseEntity.getBody();
				// 解析从微信服务器获得的openid和session_key;
				JSONObject weChatSession = JSONObject.parseObject(sessionData);
				// //获取会话秘钥
				sessionKey = weChatSession.getString("session_key");

				// 获取用户的唯一标识
				openId = weChatSession.getString("openid");
				// decoding encrypted info with AES解密 EncryptedData信息
				encryptedate = AesCbuUtil.decrypt(user.getEncryptedData(),
						sessionKey, user.getIv(), "UTF-8");
			}
		}

		// 数据查询是否存在
		UserPO userInfo = userMapper.findOneByOpenId(openId);

		if (userInfo == null && null != encryptedate
				&& encryptedate.length() > 0) {

			JSONObject userInfoJSON = JSONObject.parseObject(encryptedate);

			// 不存在用户，新建，免费次数为1
			String gender = userInfoJSON.getString("gender");
			userInfo = new UserPO();
			userInfo.setAvatarUrl(userInfoJSON.getString("avatarUrl"));
			userInfo.setCity(userInfoJSON.getString("city"));
			userInfo.setCountry(userInfoJSON.getString("country"));
			userInfo.setCreateTime(new Date());
			userInfo.setFreeNum(1);
			userInfo.setGender("男".equals(gender) ? "1" : "2"); // 性别1男2女
			userInfo.setNickName(userInfoJSON.getString("nickName"));
			userInfo.setOpenId(openId);
			userInfo.setProvince(userInfoJSON.getString("province"));
			if (sourceOpenId != null && !openId.equals(sourceOpenId)) {
				userInfo.setSourceOpenId(sourceOpenId);
			}

			userInfo.setUnionId(userInfoJSON.getString("unionId"));
			userInfo.setUserStatus(1);
			userMapper.insertSelective(userInfo);
			result = new ServiceResult(HttpResultEnum.SUCCESS);
			userInfo.setSessionKey(sessionKey);
			result.setData(userInfo);
			return result;

		}
//		if (sourceOpenId != null && !openId.equals(sourceOpenId)) {// 分享路径过来的用户
//			if (StringUtils.isBlank(userInfo.getSourceOpenId())
//					&& userInfo.getFreeNum() != 0) {
//				User u = new User();
//				u.setId(userInfo.getId());
//				u.setSourceOpenId(sourceOpenId);
//				userMapper.updateByPrimaryKeySelective(u);
//				// 想分享表插入数据
//				Share share = new Share();
//				share.setSourceOpenId(sourceOpenId);
//				share.setOpenId(openId);
//				share.setCreateTime(new Date());
//				share.setShareStatus(0);
//				share.setSourseId(Integer.valueOf(sourceId));
//				if (shareMapper.judgeSahreValid(share) == null) {
//					shareMapper.insertSelective(share);
//				}
//			}
//		}


		result = new ServiceResult(HttpResultEnum.SUCCESS);
		userInfo.setSessionKey(sessionKey);
		result.setData(userInfo);

		return result;
	}

	public static void main(String[] args) {
		while (true){
			try {
				String decrypt = AesCbuUtil.decrypt("dsjakfjsa",
						"dsdssdsd", "ddddd", "UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
			}

		}


	}

}
