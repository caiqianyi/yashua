package com.lebaoxun.modules.news.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.record.formula.functions.T;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.service.IService;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.news.entity.NewsEntity;
import com.lebaoxun.modules.news.service.hystrix.NewsServiceHystrix;


/**
 * 新闻表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-05 16:39:42
 */

@FeignClient(value="news-service",fallback=NewsServiceHystrix.class)
public interface INewsService{
	/**
     * 列表
     */
    @RequestMapping("/news/news/list")
    ResponseMessage list(@RequestParam Map<String, Object> params);


    /**
     * 信息
     */
    @RequestMapping("/news/news/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id);

    /**
     * 保存
     */
    @RequestMapping("/news/news/save")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody NewsEntity news);

    /**
     * 修改
     */
    @RequestMapping(value="/news/news/update")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody NewsEntity news);

    /**
     * 删除
     */
    @RequestMapping("/news/news/delete")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Integer[] ids);
    
    
    /**
     * 发布列表
     */
    @RequestMapping("/news/release/list")
    List<NewsEntity> list(@RequestParam(value="size",required=false) Integer size, 
    		@RequestParam(value="offset",required=false) Integer offset,
    		@RequestParam(value="class_id",required=false) Integer class_id);
    
    /**
     * 文章点赞
     */
    @RequestMapping("/news/release/praise")
    ResponseMessage praise(@RequestParam("id") Long id,
    		@RequestParam("userId") Long userId,
    		@RequestParam(value="host",required=false) String host);
    
    /**
     * 评论
     */
    @RequestMapping("/news/release/toReply")
    ResponseMessage toReply(@RequestParam("id") Long id,
    		@RequestParam("userId") Long userId,
    		@RequestParam("content") String content,
    		@RequestParam(value="toReplyId",required=false) Integer toReplyId,
    		@RequestParam(value="host",required=false) String host);
    
    /**
     * 回复列表
     */
    @RequestMapping("/news/release/replys")
    ResponseMessage replys(@RequestParam Map<String, Object> params);
    
    /**
     * 审核通过新闻查询
     * @param id
     * @return
     */
    @RequestMapping("/news/release/info/{id}")
    NewsEntity releaseInfo(@PathVariable("id") Long id);
    
    /**
     * 修改点击数 flag=true 增加
     */
    @RequestMapping("/news/modify/clicks")
    ResponseMessage modifyClicks(@RequestParam("id") Long id,@RequestParam("flag") boolean flag);


	

	


	

    
}

