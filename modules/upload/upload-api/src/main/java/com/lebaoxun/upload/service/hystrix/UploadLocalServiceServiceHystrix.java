package com.lebaoxun.upload.service.hystrix;

import org.springframework.stereotype.Component;

import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.upload.service.IUploadLocalService;

@Component
public class UploadLocalServiceServiceHystrix implements IUploadLocalService {
	
	//private Logger logger = LoggerFactory.getLogger(UploadServiceServiceHystrix.class);

	@Override
	public ResponseMessage uploadImg(String mode, String namespace,
			String fileType, Boolean check, String imgStr) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage deleteFile(String mode, String file) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}
}
