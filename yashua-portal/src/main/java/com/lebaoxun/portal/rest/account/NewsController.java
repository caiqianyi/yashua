package com.lebaoxun.portal.rest.account;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.news.service.INewsService;
import com.lebaoxun.portal.rest.BaseController;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;
import com.lebaoxun.security.oauth2.entity.Oauth2UserLog;

@Controller
public class NewsController extends BaseController{
	
	@Resource
	private INewsService newsService;
	
	@Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;
	
	@RequestMapping("/news/list.html")
	public String list(@RequestParam(value="size",required=false)Integer size,
			@RequestParam(value="offset",required=false)Integer offset,
			@RequestParam(value="class_id",required=false)Integer class_id,
			Map<String,Object> map){
		map.put("release", newsService.list(size, offset, class_id));
		return "/news/list";
	}
	
	@RequestMapping("/news/info/{id}.html")
	public ModelAndView list(@RequestParam Map<String, Object> params,@PathVariable("id") Long id){
		params.put("record_id", id);
		ModelAndView mav = new ModelAndView("/news/info");
		mav.addObject("news", newsService.releaseInfo(id));
		return mav;
	}
	
	
	/**
     * 文章点赞
     */
    @RequestMapping("/news/praise")
    ResponseMessage praise(@RequestParam("id") Long id){
    	Oauth2UserLog log = oauth2SecuritySubject.getCurrentUserLog();
    	return newsService.praise(id, oauth2SecuritySubject.getCurrentUser(), log.getHost());
    }
    
    /**
     * 评论
     */
    @RequestMapping("/news/toReply")
    ResponseMessage toReply(@RequestParam("id") Long id,
    		@RequestParam("content") String content,
    		@RequestParam(value="toReplyId",required=false) Integer toReplyId){
    	Oauth2UserLog log = oauth2SecuritySubject.getCurrentUserLog();
    	return newsService.toReply(id, oauth2SecuritySubject.getCurrentUser(), content, toReplyId, log.getHost());
    }
}
