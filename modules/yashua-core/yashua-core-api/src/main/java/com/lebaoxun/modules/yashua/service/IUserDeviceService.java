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
     * 列表
     */
    @RequestMapping("/yashua/userdevice/selcone")
    ResponseMessage selcone(@RequestParam Map<String, Object> params);
    /**
     * 信息
     */
    @RequestMapping("/yashua/userdevice/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id,
    		@RequestParam(value="userId",required=false) Long userId);

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
    
    /**
     * 绑定设备
     * @param account 用户ID
     * @param identity 设备ID
     * @param maxBindNum 最多绑定设备数
     * @return
     */
    @RequestMapping("/yashua/userdevice/bind")
    ResponseMessage bind(@RequestParam("account")String account,
    		@RequestParam("identity") String identity,
    		@RequestParam(value="maxBindNum",required=false) Integer maxBindNum);
    
    /**
     * 解除绑定
     * @param account 用户ID
     * @param identity 设备ID
     * @return
     */
    @RequestMapping("/yashua/userdevice/unbind")
    ResponseMessage unbind(@RequestParam("account")String account,
    		@RequestParam("identity") String identity);
   
    /**
     * 设备链接
     */

    @RequestMapping("/yashua/device/connect")
	ResponseMessage connect(@RequestParam("account") String account, @RequestParam("identity") String identity);


    /**
     * 设置牙刷名称
     */
    @RequestMapping("/yashua/device/setName")
	ResponseMessage setName(@RequestParam("account") String account,@RequestParam("name") String name, @RequestParam("identity") String identity);

    /**
     * 获取牙刷名称
     */
    @RequestMapping("/yashua/device/getDeviceName")
	ResponseMessage getDeviceName(@RequestParam("account") String account, @RequestParam("identity") String identity);
    
}

