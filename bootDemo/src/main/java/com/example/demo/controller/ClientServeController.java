package com.example.demo.controller;

import com.example.demo.common.HttpResultEnum;
import com.example.demo.common.ServiceResult;
import com.example.demo.pojo.WebchatUserReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 */
@RestController
public class ClientServeController {

	private final static Logger logger = LoggerFactory.getLogger(ClientServeController.class);
	@Autowired
	private ClientServe clientServe;
	@Autowired
//	EmailSchedule emailSchedule;

	
	//用户注册  1.普通进入，2分享进入
	//code;iv;EncryptedData;sourceOpenId
	@PostMapping(value = "authority")
	public ServiceResult authority (@RequestBody WebchatUserReq user) {
		ServiceResult result = null;
		try {
			result =  clientServe.authority(user);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ServiceResult(HttpResultEnum.FAIL);
		}
		return result;
	}

	
}
