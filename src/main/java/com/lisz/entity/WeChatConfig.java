package com.lisz.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WeChatConfig {
	// 从application.properties里面读取
	@Value("${WeChat.appID}")
	private String appID;
	@Value("${WeChat.appsecret}")
	private String appsecret;
	@Value("${WeChat.tokenString}")
	private String tokenString;
	
	public String getAppID() {
		return appID;
	}
	public void setAppID(String appID) {
		this.appID = appID;
	}
	public String getAppsecret() {
		return appsecret;
	}
	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}
	public String getTokenString() {
		return tokenString;
	}
	public void setTokenString(String tokenString) {
		this.tokenString = tokenString;
	}
}
