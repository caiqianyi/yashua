package com.lebaoxun.modules.sign.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.lebaoxun.modules.sign.entity.SignAwardEntity;
import com.lebaoxun.modules.sign.service.hystrix.SignAwardServiceHystrix;
import com.lebaoxun.commons.exception.ResponseMessage;

import java.util.Map;

/**
 * 签到奖励规则表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-09 17:06:41
 */

@FeignClient(value="sign-service",fallback=SignAwardServiceHystrix.class)
public interface ISignAwardService {
	/**
     * 列表
     */
    @RequestMapping("/sign/signaward/list")
    ResponseMessage list(@RequestParam Map<String, Object> params);


    /**
     * 信息
     */
    @RequestMapping("/sign/signaward/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id);

    /**
     * 保存
     */
    @RequestMapping("/sign/signaward/save")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody SignAwardEntity signAward);

    /**
     * 修改
     */
    @RequestMapping("/sign/signaward/update")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody SignAwardEntity signAward);

    /**
     * 删除
     */
    @RequestMapping("/sign/signaward/delete")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Integer[] ids);
    
}

