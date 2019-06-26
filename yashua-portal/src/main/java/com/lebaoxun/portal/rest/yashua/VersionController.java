package com.lebaoxun.portal.rest.yashua;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.yashua.service.IVersionService;

@RestController
public class VersionController {
	
	@Autowired
	private IVersionService versionService;
	
	/**
     * 获取最新的版本信息
     */
    @RequestMapping("/version/version/new/info")
    ResponseMessage info(@RequestParam("versionType") String versionType){
      return versionService.newInfo(versionType);
    }
    
    /**
     * app版本下载数目自加
     */
    
    @RequestMapping("/version/version/addLoad")
    ResponseMessage addLoad(@RequestParam("versionNumber") String versionNumber,@RequestParam("versionType") String versionType ){
      return versionService.addLoad(versionNumber,versionType);
    }
}
