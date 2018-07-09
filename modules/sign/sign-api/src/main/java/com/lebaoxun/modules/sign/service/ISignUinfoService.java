package com.lebaoxun.modules.sign.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.lebaoxun.modules.sign.entity.SignUinfoEntity;
import com.lebaoxun.modules.sign.service.hystrix.SignUinfoServiceHystrix;
import com.lebaoxun.commons.exception.ResponseMessage;

import java.util.Map;

/**
 * 签到用户表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-09 17:06:41
 */

@FeignClient(value="sign-service",fallback=SignUinfoServiceHystrix.class)
public interface ISignUinfoService {
	/**
     * 列表
     */
    @RequestMapping("/sign/signuinfo/list")
    ResponseMessage list(@RequestParam Map<String, Object> params);


    /**
     * 信息
     */
    @RequestMapping("/sign/signuinfo/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id);

    /**
     * 保存
     */
    @RequestMapping("/sign/signuinfo/save")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody SignUinfoEntity signUinfo);

    /**
     * 修改
     */
    @RequestMapping("/sign/signuinfo/update")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody SignUinfoEntity signUinfo);

    /**
     * 删除
     */
    @RequestMapping("/sign/signuinfo/delete")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Integer[] ids);
    
}

