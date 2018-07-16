package com.lebaoxun.admin.rest.mall;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.mall.entity.MallProductSpecificationEntity;
import com.lebaoxun.modules.mall.service.IMallProductSpecificationService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;



/**
 * 商品规格表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
@RestController
public class MallProductSpecificationController {
    @Autowired
    private IMallProductSpecificationService mallProductSpecificationService;
    
    @Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;

    /**
     * 列表
     */
    @RequestMapping("/mall/mallproductspecification/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        return mallProductSpecificationService.list(params);
    }


    /**
     * 信息
     */
    @RequestMapping("/mall/mallproductspecification/info/{productSpecId}")
    ResponseMessage info(@PathVariable("productSpecId") Long productSpecId){
        return mallProductSpecificationService.info(productSpecId);
    }

    /**
     * 保存
     */
    @RequestMapping("/mall/mallproductspecification/save")
    ResponseMessage save(@RequestBody MallProductSpecificationEntity mallProductSpecification){
    	mallProductSpecification.setCreateBy(oauth2SecuritySubject.getCurrentUser()+"");
    	mallProductSpecification.setCreateTime(new Date());
        return mallProductSpecificationService.save(oauth2SecuritySubject.getCurrentUser(),mallProductSpecification);
    }

    /**
     * 修改
     */
    @RequestMapping("/mall/mallproductspecification/update")
    ResponseMessage update(@RequestBody MallProductSpecificationEntity mallProductSpecification){
    	Long userId = oauth2SecuritySubject.getCurrentUser();
    	mallProductSpecification.setUpdateBy(userId+"");
    	mallProductSpecification.setUpdateTime(new Date());
        return mallProductSpecificationService.update(oauth2SecuritySubject.getCurrentUser(),mallProductSpecification);
    }

    /**
     * 删除
     */
    @RequestMapping("/mall/mallproductspecification/delete")
    ResponseMessage delete(@RequestBody Long[] productSpecIds){
        return mallProductSpecificationService.delete(oauth2SecuritySubject.getCurrentUser(),productSpecIds);
    }

}
