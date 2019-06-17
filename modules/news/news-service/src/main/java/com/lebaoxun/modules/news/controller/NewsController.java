package com.lebaoxun.modules.news.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.modules.news.entity.NewsEntity;
import com.lebaoxun.modules.news.entity.PraiseLogEntity;
import com.lebaoxun.modules.news.entity.ReplysEntity;
import com.lebaoxun.modules.news.service.NewsService;
import com.lebaoxun.modules.news.service.PraiseLogService;
import com.lebaoxun.modules.news.service.ReplysService;
import com.lebaoxun.soa.core.redis.lock.RedisLock;


/**
 * 新闻表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-05 16:39:42
 */
@RestController
public class NewsController {
	
    @Autowired
    private NewsService newsService;
    
    @Autowired
    private PraiseLogService praiseLogService;
    
    @Autowired
    private ReplysService replysService;

    /**
     * 列表
     */
    @RequestMapping("/news/news/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        PageUtils page = newsService.queryPage(params);
        return ResponseMessage.ok(page);
    }

    /**
     * 发布列表
     */
    @RequestMapping("/news/release/list")
    List<NewsEntity> list(@RequestParam(value="size",required=false) Integer size, 
    		@RequestParam(value="offset",required=false) Integer offset,
    		@RequestParam(value="class_id",required=false) Integer class_id){
        return newsService.queryReleaseNews(size, offset, class_id);
    }
    
    /**
     * 文章点赞
     */
    @RequestMapping("/news/release/praise")
    @RedisLock(value="news:release:praise:lock:#arg0")
    ResponseMessage praise(@RequestParam("id") Long id,
    		@RequestParam("userId") Long userId,
    		@RequestParam(value="host",required=false) String host){
    	
    	int count = praiseLogService.selectCount(new EntityWrapper<PraiseLogEntity>().eq("type", "news").eq("record_id", id).eq("user_id", userId));
    	if(count > 0){
    		throw new I18nMessageException("-1","您已经点过赞了");
    	}
    	PraiseLogEntity praiseLog = new PraiseLogEntity();
    	praiseLog.setHost(host);
    	praiseLog.setRecordId(""+id);
    	praiseLog.setType("news");
    	praiseLog.setUserId(userId);
		praiseLogService.insert(praiseLog);
    	return ResponseMessage.ok();
    }
    
    /**
     * 评论
     */
    @RequestMapping("/news/release/toReply")
    @RedisLock(value="news:release:toReply:lock:#arg0")
    ResponseMessage toReply(@RequestParam("id") Long id,
    		@RequestParam("userId") Long userId,
    		@RequestParam("content") String content,
    		@RequestParam(value="toReplyId",required=false) Integer toReplyId,
    		@RequestParam(value="host",required=false) String host){
    	ReplysEntity replys = new ReplysEntity();
    	replys.setContent(content);
    	replys.setPraises(0);
    	replys.setType("news");
    	replys.setRecordId(""+id);
    	replys.setToReplyId(toReplyId);
    	replys.setUserId(userId);
    	replysService.insert(replys);
    	return ResponseMessage.ok();
    }
    
    /**
     * 列表
     */
    @RequestMapping("/news/release/replys")
    ResponseMessage replys(@RequestParam Map<String, Object> params){
    	params.put("type", "news");
        PageUtils page = replysService.queryPage(params);
        return ResponseMessage.ok(page);
    }
    
    /**
     * 信息
     */
    @RequestMapping("/news/news/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id){
		NewsEntity news = newsService.selectById(id);
        return ResponseMessage.ok().put("news", news);
    }
    
    @RequestMapping("/news/release/info/{id}")
    NewsEntity releaseInfo(@PathVariable("id") Long id){
        return newsService.queryReleaseNewsInfo(id);
    }

    /**
     * 保存
     */
    @RequestMapping("/news/news/save")
    @RedisLock(value="news:news:save:lock:#arg0")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody NewsEntity news){
		newsService.insert(news);
        return ResponseMessage.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/news/news/update")
    @RedisLock(value="news:news:update:lock:#arg0")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody NewsEntity news){
    	NewsEntity n = newsService.selectById(news.getId());
    	n.setAuthor(news.getAuthor());
    	n.setCheckStatus(news.getCheckStatus());
    	n.setClassId(news.getClassId());
    	n.setClicks(news.getClicks());
    	n.setContent(news.getContent());
    	n.setIsTop(news.getIsTop());
    	n.setLastReplyId(news.getLastReplyId());
    	n.setPicItems(news.getPicItems());
    	n.setPraises(news.getPraises());
    	n.setTitle(news.getTitle());
		newsService.updateAllColumnById(n);
        return ResponseMessage.ok();
    }
    
    /**
     * 修改点击数 flag=true 增加
     */
    @RequestMapping("/news/modify/clicks")
    @RedisLock(value="news:modify:clicks:lock:#arg0")
    ResponseMessage modifyClicks(@RequestParam("id") Long id,@RequestParam("flag") boolean flag){
		newsService.modifyClicks(id, flag);
        return ResponseMessage.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/news/news/delete")
    @RedisLock(value="news:news:delete:lock:#arg0")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Integer[] ids){
		newsService.deleteBatchIds(Arrays.asList(ids));
        return ResponseMessage.ok();
    }

}
