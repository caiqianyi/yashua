package com.lebaoxun.modules.yashua.service;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.yashua.entity.AppVersionEntity;
import com.lebaoxun.modules.yashua.service.hystrix.VersionServiceHystrix;


@FeignClient(value="yashua-core-service",fallback=VersionServiceHystrix.class)
public interface IVersionService {

	@RequestMapping("/version/version/list")
	ResponseMessage list(Map<String, Object> params);

	@RequestMapping("/version/version/save")
	ResponseMessage save(@RequestBody AppVersionEntity appVersionEntity);

	@RequestMapping("/version/version/update")
	ResponseMessage update(@RequestBody AppVersionEntity appVersionEntity);
	
	@RequestMapping("/version/version/info/{id}")
	ResponseMessage info(@PathVariable("id") long id);

	@RequestMapping("/version/version/delete")
	ResponseMessage delete( @RequestBody Integer[] ids);

	@RequestMapping("/version/version/new/info")
	ResponseMessage newInfo(@RequestParam("versionType") String versionType);
	
	@RequestMapping("/version/version/addLoad")
	ResponseMessage addLoad(@RequestParam("versionNumber") String versionNumber,@RequestParam("versionType") String versionType);

}
