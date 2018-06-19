package com.lebaoxun.modules.account.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.lebaoxun.modules.account.entity.UserLogEntity;
import com.lebaoxun.modules.account.service.hystrix.UserLogServiceHystrix;
import com.lebaoxun.commons.exception.ResponseMessage;

import java.util.Map;

/**
 * 
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-19 20:01:34
 */

@FeignClient(value="account-service",fallback=UserLogServiceHystrix.class)
public interface IUserLogService {
	/**
     * 列表
     */
    @RequestMapping("/account/userlog/list")
    ResponseMessage list(@RequestParam Map<String, Object> params);


    /**
     * 信息
     */
    @RequestMapping("/account/userlog/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id);

    /**
     * 保存
     */
    @RequestMapping("/account/userlog/save")
    ResponseMessage save(@RequestBody UserLogEntity userLog);

    /**
     * 修改
     */
    @RequestMapping("/account/userlog/update")
    ResponseMessage update(@RequestBody UserLogEntity userLog);

    /**
     * 删除
     */
    @RequestMapping("/account/userlog/delete")
    ResponseMessage delete(@RequestBody Integer[] ids);
    
}

