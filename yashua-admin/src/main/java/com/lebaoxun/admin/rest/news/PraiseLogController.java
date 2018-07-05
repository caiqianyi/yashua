package com.lebaoxun.admin.rest.news;

import java.util.Map;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.news.entity.PraiseLogEntity;
import com.lebaoxun.modules.news.service.IPraiseLogService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;



/**
 * 点赞表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-05 16:39:42
 */
@RestController
public class PraiseLogController {
    @Autowired
    private IPraiseLogService praiseLogService;
    
    @Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;

    /**
     * 列表
     */
    @RequestMapping("/news/praiselog/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        return praiseLogService.list(params);
    }


    /**
     * 信息
     */
    @RequestMapping("/news/praiselog/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id){
        return praiseLogService.info(id);
    }

    /**
     * 保存
     */
    @RequestMapping("/news/praiselog/save")
    ResponseMessage save(@RequestBody PraiseLogEntity praiseLog){
        return praiseLogService.save(oauth2SecuritySubject.getCurrentUser(),praiseLog);
    }

    /**
     * 修改
     */
    @RequestMapping("/news/praiselog/update")
    ResponseMessage update(@RequestBody PraiseLogEntity praiseLog){
        return praiseLogService.update(oauth2SecuritySubject.getCurrentUser(),praiseLog);
    }

    /**
     * 删除
     */
    @RequestMapping("/news/praiselog/delete")
    ResponseMessage delete(@RequestBody Integer[] ids){
        return praiseLogService.delete(oauth2SecuritySubject.getCurrentUser(),ids);
    }

}
