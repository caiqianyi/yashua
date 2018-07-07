package com.lebaoxun.admin.rest;

import java.io.IOException;
import java.util.Base64;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;
import com.lebaoxun.upload.service.IUploadLocalService;

@RestController
public class UploadController extends BaseController{
	@Resource
	private IUploadLocalService uploadLocalService;
	
	@Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;
	
	@RequestMapping("/upload/imageUp")
	ResponseMessage uploadImages(@RequestParam("upfile") MultipartFile file) throws IOException{
		if(file.isEmpty()){
            throw new I18nMessageException("500");
        }
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        logger.debug("fileName={},size={}",fileName,size);
        String fileType = fileName.substring(fileName.indexOf(".")+1);
        logger.debug("fileType={}",fileType);
        
        //base64,将字节码转化为base64的字符串
        String result = Base64.getEncoder().encodeToString(file.getBytes());
        return uploadLocalService.uploadImg("yashua", "admin/"+oauth2SecuritySubject.getCurrentUser(), "png", false, result);
	}
}
