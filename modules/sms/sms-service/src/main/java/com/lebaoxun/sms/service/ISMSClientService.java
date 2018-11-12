package com.lebaoxun.sms.service;

import java.util.List;
import java.util.Map;

public interface ISMSClientService {
	
	/**
	 * 创建账号
	 * @param appid appid
	 * @param secret secret
	 * @param status 0=禁用，1=启用
	 */
	void create(String appid, String secret, Integer status, List<Map<String,Integer>> config);

	/**
	 * 删除账号
	 * @param appid
	 */
	void delete(String appid);

	/**
	 * 修改账号状态
	 * @param appid
	 * @param status
	 */
	void update(String appid, Integer status);
	
	/**
	 * 配置发送限制，策略
	 * @param appid
	 * @param config
	 */
	void setAstrict(String appid,List<Map<String,Integer>> config);
	
	/**
	 * 获取账号配置
	 * @param appid
	 * @return
	 */
	Map<String,Object> get(String appid);
	
	/**
	 * 全部账号
	 * @return
	 */
	List<Map<String,Object>> list();
	
}
