package com.lebaoxun.portal.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lebaoxun.security.oauth2.interceptor.AuthorityInterceptor;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	
    @Bean
    public AuthorityInterceptor authorityInterceptor() {
        return new AuthorityInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorityInterceptor())
        	.excludePathPatterns("/")
        	.excludePathPatterns("/*.html")
        	.excludePathPatterns("/**/*.html")
        	.excludePathPatterns("/**/**/*.html")
        	.excludePathPatterns("/statics/**")
        	.excludePathPatterns("/hystrix.stream")
        	.excludePathPatterns("/hystrix/**")
        	.excludePathPatterns("/turbine/**")
	        .excludePathPatterns("/favicon.ico")
	        .excludePathPatterns("/captcha.jpg")
	        .excludePathPatterns("/oauth2/**")
	        .excludePathPatterns("/register/**")
	        .excludePathPatterns("/sms/**")
	        .excludePathPatterns("/news/modify/clicks")
	        .excludePathPatterns("/news/replys")
	        .excludePathPatterns("/mall/product/list")//商品列表
	        .excludePathPatterns("/mall/product/score/list")//积分商城商品列表
	        .excludePathPatterns("/mall/product/info/specs")//商品详情
	        .excludePathPatterns("/mall/cart/product/list")//购物车列表
	        .excludePathPatterns("/mall/comment/list")//评论列表
	        .excludePathPatterns("/yashua/device/macupload")//上传mac
        	;
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/statics/**").addResourceLocations("classpath:/statics/");
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = jackson2HttpMessageConverter.getObjectMapper();

        //生成json时，将所有Long转换成String
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);

        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        converters.add(0, jackson2HttpMessageConverter);
    }

}
