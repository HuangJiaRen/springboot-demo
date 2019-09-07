package com.example.demo.service;

import com.example.demo.common.ServiceResult;
import com.example.demo.pojo.WebchatUserReq;


public interface ClientServe {

	/*
	 * 包含用户id,用户昵称  和头像
	 */
	ServiceResult authority(WebchatUserReq user)throws Exception;
	
//	ServiceResult  getPhoneNumber(WebchatUserReq user)throws Exception;
//
//    ServiceResult authorityGray(WebchatUserReq user) throws Exception;
}
