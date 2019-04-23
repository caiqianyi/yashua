package com.lebaoxun.portal.rest.account;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.news.service.INewsService;
import com.lebaoxun.modules.news.service.IReplysService;
import com.lebaoxun.portal.rest.BaseController;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;
import com.lebaoxun.security.oauth2.entity.Oauth2UserLog;
import com.lebaoxun.soa.core.redis.IRedisHash;

@Controller
public class NewsController extends BaseController{
	
	@Resource
	private INewsService newsService;
	
	@Resource
	private IReplysService replysService;
	
	@Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;
	
	@Resource
	private IRedisHash redisHash;
	
	@RequestMapping("/news/list")
	@ResponseBody
	ResponseMessage datas(@RequestParam(value="size",required=false)Integer size,
			@RequestParam(value="offset",required=false)Integer offset,
			@RequestParam(value="class_id",required=false)Integer class_id,
			Map<String,Object> map){
		return new ResponseMessage(newsService.list(size, offset, class_id));
	}
	
	@RequestMapping("/news/list.html")
	String list(@RequestParam(value="size",required=false)Integer size,
			@RequestParam(value="offset",required=false)Integer offset,
			@RequestParam(value="class_id",required=false)Integer class_id,
			Map<String,Object> map){
		map.put("release", newsService.list(size, offset, class_id));
		return "/news/list";
	}
	
	@RequestMapping("/news/info/{id}")
	ResponseMessage info(@PathVariable("id") Long id){
		return new ResponseMessage(newsService.releaseInfo(id));
	}
	
	@RequestMapping("/news/info/{id}.html")
	public ModelAndView list(@RequestParam Map<String, Object> params,@PathVariable("id") Long id){
		params.put("record_id", id);
		ModelAndView mav = new ModelAndView("/news/info");
		mav.addObject("news", newsService.releaseInfo(id));
		return mav;
	}
	
	/**
     * 评论
     */
    @RequestMapping("/news/replys")
    @ResponseBody
    ResponseMessage replys(@RequestParam("id") Long id,
    		@RequestParam("size") Integer size,
			@RequestParam("offset") Integer offset){
    	return replysService.findReplys("user", "news", id+"", size, offset);
    }
    
	/**
     * 文章点赞
     */
    @RequestMapping("/news/praise")
    @ResponseBody
    ResponseMessage praise(@RequestParam("id") Long id){
    	Oauth2UserLog log = oauth2SecuritySubject.getCurrentUserLog();
    	return newsService.praise(id, oauth2SecuritySubject.getCurrentUser(), log.getHost());
    }
    
    /**
     * 评论
     */
    @RequestMapping("/news/toReply")
    @ResponseBody
    ResponseMessage toReply(@RequestParam("id") Long id,
    		@RequestParam("content") String content,
    		@RequestParam(value="toReplyId",required=false) Integer toReplyId){
    	Oauth2UserLog log = oauth2SecuritySubject.getCurrentUserLog();
    	return newsService.toReply(id, oauth2SecuritySubject.getCurrentUser(), content, toReplyId, log.getHost());
    }
    
    /**
     * 增加文章点击数
     */
    @RequestMapping("/news/modify/clicks")
    @ResponseBody
    ResponseMessage modifyClicks(@RequestParam("id") Long id,@RequestParam("uuid") String uuid){
    	String k = "news:clicks:"+id;
    	if(redisHash.hExists(k, uuid)){
    		return ResponseMessage.ok();
    	}
    	redisHash.hSet(k, uuid, new Date().getTime());
    	return newsService.modifyClicks(id, true);
    }
}
