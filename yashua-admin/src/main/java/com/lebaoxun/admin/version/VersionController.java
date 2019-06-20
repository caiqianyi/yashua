package com.lebaoxun.admin.version;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.news.entity.NewsEntity;
import com.lebaoxun.modules.yashua.entity.AppVersionEntity;
import com.lebaoxun.modules.yashua.service.IVersionService;

@RestController
public class VersionController {
	@Autowired
	private IVersionService versionService;

	/**
     * App版本列表
     */
    @RequestMapping("/version/version/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        return versionService.list(params);
    }
    
    /**
     * 保存
     */
    @RequestMapping("/version/version/save")
    ResponseMessage save(@RequestBody AppVersionEntity appVersionEntity){
        return versionService.save(appVersionEntity);
    }

    /**
     * 修改
     */
    @RequestMapping("/version/version/update")
    ResponseMessage update(@RequestBody AppVersionEntity appVersionEntity){
        return versionService.update(appVersionEntity);
    }

    
    
    /**
     * 信息
     */
    @RequestMapping("/version/version/info/{id}")
    ResponseMessage info(@PathVariable("id") long id){
        return versionService.info(id);
    }
    
    /**
     * 删除
     */
    @RequestMapping("/version/version/delete")
    ResponseMessage delete(@RequestBody Integer[] ids){
        return versionService.delete(ids);
    }
}
