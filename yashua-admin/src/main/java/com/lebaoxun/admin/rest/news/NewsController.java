package com.lebaoxun.admin.rest.news;

import java.util.Map;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.news.entity.NewsEntity;
import com.lebaoxun.modules.news.service.INewsService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;



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
    private INewsService newsService;
    
    @Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;

    /**
     * 列表
     */
    @RequestMapping("/news/news/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        return newsService.list(params);
    }


    /**
     * 信息
     */
    @RequestMapping("/news/news/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id){
        return newsService.info(id);
    }

    /**
     * 保存
     */
    @RequestMapping("/news/news/save")
    ResponseMessage save(@RequestBody NewsEntity news){
        return newsService.save(oauth2SecuritySubject.getCurrentUser(),news);
    }

    /**
     * 修改
     */
    @RequestMapping("/news/news/update")
    ResponseMessage update(@RequestBody NewsEntity news){
        return newsService.update(oauth2SecuritySubject.getCurrentUser(),news);
    }

    /**
     * 删除
     */
    @RequestMapping("/news/news/delete")
    ResponseMessage delete(@RequestBody Integer[] ids){
        return newsService.delete(oauth2SecuritySubject.getCurrentUser(),ids);
    }

}
