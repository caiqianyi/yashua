package com.lebaoxun.modules.news.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.lebaoxun.modules.news.entity.NewsEntity;
import com.lebaoxun.modules.news.service.hystrix.NewsServiceHystrix;
import com.lebaoxun.commons.exception.ResponseMessage;

import java.util.Map;

/**
 * 新闻表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-05 16:39:42
 */

@FeignClient(value="news-service",fallback=NewsServiceHystrix.class)
public interface INewsService {
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
    @RequestMapping("/news/news/update")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody NewsEntity news);

    /**
     * 删除
     */
    @RequestMapping("/news/news/delete")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Integer[] ids);
    
}

