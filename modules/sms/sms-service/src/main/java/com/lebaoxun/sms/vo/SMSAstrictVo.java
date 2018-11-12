package com.lebaoxun.sms.vo;

public class SMSAstrictVo {
	
	private String appid;
	private String format;
	private Integer astrict;
	
	public SMSAstrictVo() {
		// TODO Auto-generated constructor stub
	}
	
	public SMSAstrictVo(String appid, String format, Integer astrict) {
		// TODO Auto-generated constructor stub
		this.appid = appid;
		this.format = format;
		this.astrict = astrict;
	}
	
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public Integer getAstrict() {
		return astrict;
	}
	public void setAstrict(Integer astrict) {
		this.astrict = astrict;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}
}
