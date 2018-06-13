package com.lebaoxun.admin.config;

public class AccountConstant {
	/**
	 * 账号锁定n小时
	 */
	public static final String CACHEKEY_LOGIN_ACCOUNT_LOCK = "user:login:account:lock";
	
	public final static Integer ACCOUNT_ERROR_COUNT = 10;//一天账号密码错误次数
	
	public final static Long ACCOUNT_ERROR_LOCK_TIME = 60 * 60 * 2L;//锁定时间
	
	public final static Long ACCOUNT_ERROR_CHECK_EXPIRE = 60 * 60 * 24L;
}
