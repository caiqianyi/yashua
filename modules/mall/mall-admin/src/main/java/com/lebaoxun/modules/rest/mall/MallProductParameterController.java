package com.lebaoxun.modules.rest.mall;

import java.util.Map;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.mall.entity.MallProductParameterEntity;
import com.lebaoxun.modules.mall.service.IMallProductParameterService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;



/**
 * 商品参数表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
@RestController
public class MallProductParameterController {
    @Autowired
    private IMallProductParameterService mallProductParameterService;
    
    @Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;

    /**
     * 列表
     */
    @RequestMapping("/mall/mallproductparameter/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        return mallProductParameterService.list(params);
    }


    /**
     * 信息
     */
    @RequestMapping("/mall/mallproductparameter/info/{productParameterId}")
    ResponseMessage info(@PathVariable("productParameterId") Long productParameterId){
        return mallProductParameterService.info(productParameterId);
    }

    /**
     * 保存
     */
    @RequestMapping("/mall/mallproductparameter/save")
    ResponseMessage save(@RequestBody MallProductParameterEntity mallProductParameter){
        return mallProductParameterService.save(oauth2SecuritySubject.getCurrentUser(),mallProductParameter);
    }

    /**
     * 修改
     */
    @RequestMapping("/mall/mallproductparameter/update")
    ResponseMessage update(@RequestBody MallProductParameterEntity mallProductParameter){
        return mallProductParameterService.update(oauth2SecuritySubject.getCurrentUser(),mallProductParameter);
    }

    /**
     * 删除
     */
    @RequestMapping("/mall/mallproductparameter/delete")
    ResponseMessage delete(@RequestBody Long[] productParameterIds){
        return mallProductParameterService.delete(oauth2SecuritySubject.getCurrentUser(),productParameterIds);
    }

}
