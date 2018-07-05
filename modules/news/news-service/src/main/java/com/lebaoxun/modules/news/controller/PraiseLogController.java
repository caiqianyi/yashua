package com.lebaoxun.modules.news.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.news.entity.PraiseLogEntity;
import com.lebaoxun.modules.news.service.PraiseLogService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.soa.core.redis.lock.RedisLock;


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
    private PraiseLogService praiseLogService;

    /**
     * 列表
     */
    @RequestMapping("/news/praiselog/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        PageUtils page = praiseLogService.queryPage(params);
        return ResponseMessage.ok(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/news/praiselog/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id){
		PraiseLogEntity praiseLog = praiseLogService.selectById(id);
        return ResponseMessage.ok().put("praiseLog", praiseLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/news/praiselog/save")
    @RedisLock(value="news:praiselog:save:lock:#arg0")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody PraiseLogEntity praiseLog){
		praiseLogService.insert(praiseLog);
        return ResponseMessage.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/news/praiselog/update")
    @RedisLock(value="news:praiselog:update:lock:#arg0")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody PraiseLogEntity praiseLog){
		praiseLogService.updateById(praiseLog);
        return ResponseMessage.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/news/praiselog/delete")
    @RedisLock(value="news:praiselog:delete:lock:#arg0")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Integer[] ids){
		praiseLogService.deleteBatchIds(Arrays.asList(ids));
        return ResponseMessage.ok();
    }

}
