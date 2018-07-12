package com.lebaoxun.admin.rest.mall;

import java.util.Map;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.mall.entity.MallOrderEntity;
import com.lebaoxun.modules.mall.service.IMallOrderService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;



/**
 * 订单表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:11
 */
@RestController
public class MallOrderController {
    @Autowired
    private IMallOrderService mallOrderService;
    
    @Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;

    /**
     * 列表
     */
    @RequestMapping("/mall/mallorder/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        return mallOrderService.list(params);
    }


    /**
     * 信息
     */
    @RequestMapping("/mall/mallorder/info/{id}")
    ResponseMessage info(@PathVariable("id") Long id){
        return mallOrderService.info(id);
    }

    /**
     * 保存
     */
    @RequestMapping("/mall/mallorder/save")
    ResponseMessage save(@RequestBody MallOrderEntity mallOrder){
        return mallOrderService.save(oauth2SecuritySubject.getCurrentUser(),mallOrder);
    }

    /**
     * 修改
     */
    @RequestMapping("/mall/mallorder/update")
    ResponseMessage update(@RequestBody MallOrderEntity mallOrder){
        return mallOrderService.update(oauth2SecuritySubject.getCurrentUser(),mallOrder);
    }

    /**
     * 删除
     */
    @RequestMapping("/mall/mallorder/delete")
    ResponseMessage delete(@RequestBody Long[] ids){
        return mallOrderService.delete(oauth2SecuritySubject.getCurrentUser(),ids);
    }

}
