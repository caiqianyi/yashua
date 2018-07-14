package com.lebaoxun.admin.rest.mall;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.mall.entity.MallCategoryProductEntity;
import com.lebaoxun.modules.mall.service.IMallCategoryProductService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;



/**
 * 商品分类关联表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
@RestController
public class MallCategoryProductController {
    @Autowired
    private IMallCategoryProductService mallCategoryProductService;
    
    @Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;

    @RequestMapping("/mall/mallcategoryproduct/tree/{productId}")
    ResponseMessage tree(@PathVariable("productId") Long productId){
    	return mallCategoryProductService.tree(productId);
    }

    /**
     * 修改
     */
    @RequestMapping("/mall/mallcategoryproduct/edit")
    ResponseMessage edit(@RequestParam("productId")Long productId,@RequestParam("categoryIds")Long[] categoryIds){
        return mallCategoryProductService.edit(oauth2SecuritySubject.getCurrentUser(), productId, categoryIds);
    }

}
