package com.lebaoxun.modules.rest.mall;

import java.util.Map;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.mall.entity.MallOrderProductEntity;
import com.lebaoxun.modules.mall.service.IMallOrderProductService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;



/**
 * 订单明细表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:11
 */
@RestController
public class MallOrderProductController {
    @Autowired
    private IMallOrderProductService mallOrderProductService;
    
    @Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;

    /**
     * 列表
     */
    @RequestMapping("/mall/mallorderproduct/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        return mallOrderProductService.list(params);
    }


    /**
     * 信息
     */
    @RequestMapping("/mall/mallorderproduct/info/{orderProductId}")
    ResponseMessage info(@PathVariable("orderProductId") Long orderProductId){
        return mallOrderProductService.info(orderProductId);
    }

    /**
     * 保存
     */
    @RequestMapping("/mall/mallorderproduct/save")
    ResponseMessage save(@RequestBody MallOrderProductEntity mallOrderProduct){
        return mallOrderProductService.save(oauth2SecuritySubject.getCurrentUser(),mallOrderProduct);
    }

    /**
     * 修改
     */
    @RequestMapping("/mall/mallorderproduct/update")
    ResponseMessage update(@RequestBody MallOrderProductEntity mallOrderProduct){
        return mallOrderProductService.update(oauth2SecuritySubject.getCurrentUser(),mallOrderProduct);
    }

    /**
     * 删除
     */
    @RequestMapping("/mall/mallorderproduct/delete")
    ResponseMessage delete(@RequestBody Long[] orderProductIds){
        return mallOrderProductService.delete(oauth2SecuritySubject.getCurrentUser(),orderProductIds);
    }

}
