package com.lebaoxun.portal.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	public HttpServletRequest request;
	@Autowired
	public HttpServletResponse response;

}
