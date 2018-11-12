package com.lebaoxun.sms.vo;

public class SMSClientVo {
	
	private String appid;
	private String secret;
	
	public SMSClientVo() {
		// TODO Auto-generated constructor stub
	}
	public SMSClientVo(String appid,String secret) {
		// TODO Auto-generated constructor stub
		this.appid = appid;
		this.secret = secret;
	}
	
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
}
