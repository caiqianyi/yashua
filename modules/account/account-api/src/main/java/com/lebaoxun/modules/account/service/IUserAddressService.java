package com.lebaoxun.modules.account.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.lebaoxun.modules.account.entity.UserAddressEntity;
import com.lebaoxun.modules.account.service.hystrix.UserAddressServiceHystrix;
import com.lebaoxun.commons.exception.ResponseMessage;

import java.util.Map;

/**
 * 用户地址
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-26 10:20:24
 */

@FeignClient(value="account-service",fallback=UserAddressServiceHystrix.class)
public interface IUserAddressService {
	/**
     * 列表
     */
    @RequestMapping("/account/useraddress/list")
    ResponseMessage list(@RequestParam Map<String, Object> params);


    /**
     * 信息
     */
    @RequestMapping("/account/useraddress/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id);

    /**
     * 保存
     */
    @RequestMapping("/account/useraddress/save")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody UserAddressEntity userAddress);

    /**
     * 修改
     */
    @RequestMapping("/account/useraddress/update")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody UserAddressEntity userAddress);

    /**
     * 删除
     */
    @RequestMapping("/account/useraddress/delete")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Integer[] ids);
    
}

