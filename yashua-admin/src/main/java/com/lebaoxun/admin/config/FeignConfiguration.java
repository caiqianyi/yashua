package com.lebaoxun.admin.config;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.support.RequestContext;

import com.google.gson.Gson;
import com.lebaoxun.security.oauth2.Oauth2AccessToken;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;
import com.lebaoxun.security.oauth2.entity.Oauth2UserLog;

import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;

@Configuration
public class FeignConfiguration {
    /**
     * 日志级别
     * @return
     */
    @Bean  
    Logger.Level feignLoggerLevel() {  
        return Logger.Level.FULL;  
    }

    /**
     * 创建Feign请求拦截器，在发送请求前设置认证的token,各个微服务将token设置到环境变量中来达到通用
     * @return
     */
    /*@Bean
    public FeignBasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new FeignBasicAuthRequestInterceptor();
    }*/

}

/**
 * Feign请求拦截器
 * @author yinjihuan
 * @create 2017-11-10 17:25
 **/
//@Repository("basicAuthRequestInterceptor")
class FeignBasicAuthRequestInterceptor  implements RequestInterceptor {
	private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;
	
    public FeignBasicAuthRequestInterceptor() {

    }

    @Override
    public void apply(RequestTemplate template) {
    	/*String assess_token = Oauth2AccessToken.getToken();
    	logger.debug("assess_token={}",assess_token);
    	Oauth2UserLog log = oauth2SecuritySubject.getCurrentUserLog();
    	logger.debug("log={}",new Gson().toJson(log));
    	if(log != null){
    		template.header("oauth2.platform", log.getPlatformSource());
    		template.header("oauth2.host", log.getHost());
    	}*/
        //template.header("Authorization", System.getProperty("fangjia.auth.token"));
    }
}
