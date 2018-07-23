package com.lebaoxun.portal.rest.account.mall;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.account.entity.UserEntity;
import com.lebaoxun.modules.account.service.IUserService;
import com.lebaoxun.modules.mall.entity.MallProductCommentEntity;
import com.lebaoxun.modules.mall.entity.MallProductCommentImageEntity;
import com.lebaoxun.modules.mall.service.IMallProductCommentService;
import com.lebaoxun.portal.rest.BaseController;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;
import com.lebaoxun.upload.service.IUploadLocalService;

@RestController
public class MallProductCommentController extends BaseController {
	
	@Resource
	private IMallProductCommentService mallProductCommentService;
	
	@Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;
	
	@Resource
    private IUploadLocalService uploadLocalService;
	
	@Resource
    private IUserService userService;

	@RequestMapping("/mall/mallproductcomment/publish")
	ResponseMessage publish(@RequestParam("orderProductId") Long orderProductId,
			@RequestBody MallProductCommentEntity comment){
		Long userId = oauth2SecuritySubject.getCurrentUser();
		UserEntity user = userService.findByUserId(userId);
		String namespace = "user/"+userId;
		comment.setCreateBy(userId+"");
		comment.setCreateTime(new Date());
		comment.setUserId(userId);
		comment.setNickname(user.getNickname());
		comment.setHeadimgurl(user.getHeadimgurl());
		comment.setType(0);
		comment.setPraises(0);
		comment.setStar(5);
		comment.setStatus(1);
		List<MallProductCommentImageEntity> comments = comment.getPicImgs();
		for(int sort=0;sort<comments.size();sort++){
			MallProductCommentImageEntity imgComment = comments.get(sort);
			String imgStr = imgComment.getPicImg();
			ResponseMessage r = uploadLocalService.uploadImg("yashua", namespace, "png", false, imgStr);
	    	if(!"0".equals(r.getErrcode())){
	    		return r;
	    	}
	    	Map<String,String> data = (Map<String, String>) r.getData();
	    	String picImg = data.get("uri");
	    	imgComment.setPicImg(picImg);
	    	imgComment.setStatus(1);
		}
		ResponseMessage rm = mallProductCommentService.publish(userId, orderProductId, comment);
		if(!"0".equals(rm.getErrcode())){
			for(int sort=0;sort<comments.size();sort++){
				MallProductCommentImageEntity imgComment = comments.get(sort);
				uploadLocalService.deleteFile("yashua", imgComment.getPicImg());
			}
		}
		return rm;
	}

	@RequestMapping("/mall/comment/list")
	ResponseMessage list(
			@RequestParam("productId") Long productId){
		return mallProductCommentService.selectByProduct(productId);
	}
	
}
