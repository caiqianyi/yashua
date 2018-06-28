package com.lebaoxun.upload.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.upload.service.hystrix.UploadLocalServiceServiceHystrix;

@FeignClient(value="upload-service",fallback=UploadLocalServiceServiceHystrix.class)
public interface IUploadLocalService {
	
	/**
	 * 上传文件
	 * @param mode 项目名（取不同配置）
	 * @param namespace（文件space）
	 * @param fileType（文件类型）
	 * @param check（是否检查敏感信息）
	 * @param imgStr（base64 图片内容）
	 * @return
	 */
	@RequestMapping(value="/upload/local/{mode}/img",method=RequestMethod.POST)
	ResponseMessage uploadImg(@PathVariable("mode")String mode, 
			@RequestParam("namespace") String namespace, 
			@RequestParam("fileType") String fileType,
			@RequestParam(value="check",required=false) Boolean check,
			@RequestBody String imgStr);
	
	@RequestMapping(value="/upload/local/{mode}/file/delete",method=RequestMethod.GET)
	ResponseMessage deleteFile(@PathVariable("mode")String mode,
			@RequestParam("file") String file);
}
