package com.lebaoxun.modules.news.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.news.entity.NewsEntity;
import com.lebaoxun.modules.news.service.NewsService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
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

    /**
     * 列表
     */
    @RequestMapping("/news/news/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        PageUtils page = newsService.queryPage(params);
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
		newsService.updateById(news);
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
