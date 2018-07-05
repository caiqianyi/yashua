package com.lebaoxun.modules.news.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.news.entity.ReplysEntity;
import com.lebaoxun.modules.news.service.ReplysService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.soa.core.redis.lock.RedisLock;


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
    private ReplysService replysService;

    /**
     * 列表
     */
    @RequestMapping("/news/replys/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        PageUtils page = replysService.queryPage(params);
        return ResponseMessage.ok(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/news/replys/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id){
		ReplysEntity replys = replysService.selectById(id);
        return ResponseMessage.ok().put("replys", replys);
    }

    /**
     * 保存
     */
    @RequestMapping("/news/replys/save")
    @RedisLock(value="news:replys:save:lock:#arg0")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody ReplysEntity replys){
		replysService.insert(replys);
        return ResponseMessage.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/news/replys/update")
    @RedisLock(value="news:replys:update:lock:#arg0")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody ReplysEntity replys){
		replysService.updateById(replys);
        return ResponseMessage.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/news/replys/delete")
    @RedisLock(value="news:replys:delete:lock:#arg0")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Integer[] ids){
		replysService.deleteBatchIds(Arrays.asList(ids));
        return ResponseMessage.ok();
    }

}
