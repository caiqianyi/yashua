package com.lebaoxun.sms.service;

import java.util.List;

import com.lebaoxun.sms.vo.SMSAstrictVo;
import com.lebaoxun.sms.vo.SMSClientVo;

public interface ISMSClientService {
	
	/**
	 * 创建账号
	 * @param appid appid
	 * @param secret secret
	 * @param status 0=禁用，1=启用
	 */
	void saveOrUpdate(SMSClientVo clientVo);

	/**
	 * 删除账号
	 * @param appid
	 */
	void delete(String appid);
	
	SMSClientVo find(String appid);
	
	List<SMSClientVo> findAll();
	
	/**
	 * 配置发送限制，策略
	 * @param appid
	 * @param config
	 */
	void setAstrict(String appid,String format,Integer astrict);
	
	void deleteAstrict(String appid,String format);
	
	List<SMSAstrictVo> findAstrict(String appid);
	
	SMSAstrictVo findAstrict(String appid,String format);
	
	
	
	boolean isInBlackList(String appid,String mobile);
	
	boolean isFreeze(String appid,String mobile);
	
	
	
	
	void addSendLog(String appid,String mobile);
	
	void freeze(String appid,String mobile,String reason);
	
	void addBlackList(String appid,String mobile);
	
	
	String refreshVfCode(String appid, String mobile);
	
	boolean checkVfCode(String appid, String tel,String vfCode);
}
