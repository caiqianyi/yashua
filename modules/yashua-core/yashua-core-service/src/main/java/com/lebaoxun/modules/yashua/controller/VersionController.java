package com.lebaoxun.modules.yashua.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.modules.yashua.entity.AppVersionEntity;
import com.lebaoxun.modules.yashua.service.VersionService;
import com.lebaoxun.soa.core.redis.lock.RedisLock;

@RestController
public class VersionController {
	
	@Autowired
	private VersionService versionService;
	/**
     * App列表
     */
    @RequestMapping("/version/version/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        PageUtils page = versionService.queryPage(params);
        return ResponseMessage.ok(page);
    }
    
    /**
     * App版本新增
     */
    @RequestMapping("/version/version/save")
    @RedisLock(value="version:version:save:lock:#arg0")
	ResponseMessage save(@RequestBody AppVersionEntity appVersionEntity){
    	appVersionEntity.setCreateTime(new Date());
    	versionService.insert(appVersionEntity);
    	 return ResponseMessage.ok();
    }
    
    /**
     * 修改
     */
    @RequestMapping("/version/version/update")
    @RedisLock(value="version:version:update:lock:#arg0")
    ResponseMessage update(@RequestBody AppVersionEntity appVersionEntity){
  
    	versionService.updateById(appVersionEntity);
        return ResponseMessage.ok();
    }
    
    /**
     * 信息
     */
    @RequestMapping("/version/version/info/{id}")
	ResponseMessage info(@PathVariable("id") long id){
    	AppVersionEntity appVersionEntity = versionService.selectById(id);
        return ResponseMessage.ok().put("version", appVersionEntity);
    }
    
    /**
     * 删除
     */
    @RequestMapping("/version/version/delete")
    @RedisLock(value="version:version:delete:lock:#arg0")
	ResponseMessage delete( @RequestBody Integer[] ids){
    	versionService.deleteBatchIds(Arrays.asList(ids));
        return ResponseMessage.ok();
    }
    
    /**
     * App获取最新的版本信息
     */
    @RequestMapping("/version/version/new/info")
	ResponseMessage newInfo(@RequestParam("versionType") String versionType){
    	AppVersionEntity appVersionEntity = versionService.newInfo(versionType);
    	return ResponseMessage.ok(appVersionEntity);
    }
    
    @RequestMapping("/version/version/addLoad")
	ResponseMessage addLoad(@RequestParam("versionNumber") String versionNumber,@RequestParam("versionType") String versionType){
    	versionService.addLoad(versionNumber,versionType);
    	return ResponseMessage.ok();
    }
	
}
