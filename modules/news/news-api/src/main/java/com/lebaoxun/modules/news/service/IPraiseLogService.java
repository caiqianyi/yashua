package com.lebaoxun.modules.news.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.lebaoxun.modules.news.entity.PraiseLogEntity;
import com.lebaoxun.modules.news.service.hystrix.PraiseLogServiceHystrix;
import com.lebaoxun.commons.exception.ResponseMessage;

import java.util.Map;

/**
 * 点赞表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-05 16:39:42
 */

@FeignClient(value="news-service",fallback=PraiseLogServiceHystrix.class)
public interface IPraiseLogService {
	/**
     * 列表
     */
    @RequestMapping("/news/praiselog/list")
    ResponseMessage list(@RequestParam Map<String, Object> params);


    /**
     * 信息
     */
    @RequestMapping("/news/praiselog/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id);

    /**
     * 保存
     */
    @RequestMapping("/news/praiselog/save")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody PraiseLogEntity praiseLog);

    /**
     * 修改
     */
    @RequestMapping("/news/praiselog/update")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody PraiseLogEntity praiseLog);

    /**
     * 删除
     */
    @RequestMapping("/news/praiselog/delete")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Integer[] ids);
    
}

