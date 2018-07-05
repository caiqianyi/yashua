package com.lebaoxun.admin.rest.news;

import java.util.Map;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.news.entity.ReplysEntity;
import com.lebaoxun.modules.news.service.IReplysService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;



/**
 * 回复表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-05 16:39:42
 */
@RestController
public class ReplysController {
    @Autowired
    private IReplysService replysService;
    
    @Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;

    /**
     * 列表
     */
    @RequestMapping("/news/replys/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        return replysService.list(params);
    }


    /**
     * 信息
     */
    @RequestMapping("/news/replys/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id){
        return replysService.info(id);
    }

    /**
     * 保存
     */
    @RequestMapping("/news/replys/save")
    ResponseMessage save(@RequestBody ReplysEntity replys){
        return replysService.save(oauth2SecuritySubject.getCurrentUser(),replys);
    }

    /**
     * 修改
     */
    @RequestMapping("/news/replys/update")
    ResponseMessage update(@RequestBody ReplysEntity replys){
        return replysService.update(oauth2SecuritySubject.getCurrentUser(),replys);
    }

    /**
     * 删除
     */
    @RequestMapping("/news/replys/delete")
    ResponseMessage delete(@RequestBody Integer[] ids){
        return replysService.delete(oauth2SecuritySubject.getCurrentUser(),ids);
    }

}
