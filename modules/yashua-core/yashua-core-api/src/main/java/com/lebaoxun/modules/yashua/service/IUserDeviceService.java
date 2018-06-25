package com.lebaoxun.modules.yashua.service;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.yashua.entity.UserDeviceEntity;
import com.lebaoxun.modules.yashua.service.hystrix.UserDeviceServiceHystrix;

/**
 * 用户设备表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-23 16:42:44
 */

@FeignClient(value="yashua-core-service",fallback=UserDeviceServiceHystrix.class)
public interface IUserDeviceService {
	/**
     * 列表
     */
    @RequestMapping("/yashua/userdevice/list")
    ResponseMessage list(@RequestParam Map<String, Object> params);


    /**
     * 信息
     */
    @RequestMapping("/yashua/userdevice/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id);

    /**
     * 保存
     */
    @RequestMapping("/yashua/userdevice/save")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody UserDeviceEntity userDevice);
    
    @RequestMapping("/yashua/userdevice/addBatch")
    ResponseMessage addBatch(@RequestParam("adminId")Long adminId,@RequestBody List<UserDeviceEntity> userDevices);
    
    /**
     * 修改
     */
    @RequestMapping("/yashua/userdevice/update")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody UserDeviceEntity userDevice);

    /**
     * 删除
     */
    @RequestMapping("/yashua/userdevice/delete")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Integer[] ids);
    
}

