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

    /**
     * 列表
     */
    @RequestMapping("/mall/mallcategoryproduct/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        return mallCategoryProductService.list(params);
    }


    /**
     * 信息
     */
    @RequestMapping("/mall/mallcategoryproduct/info/{id}")
    ResponseMessage info(@PathVariable("id") Long id){
        return mallCategoryProductService.info(id);
    }

    /**
     * 保存
     */
    @RequestMapping("/mall/mallcategoryproduct/save")
    ResponseMessage save(@RequestBody MallCategoryProductEntity mallCategoryProduct){
        return mallCategoryProductService.save(oauth2SecuritySubject.getCurrentUser(),mallCategoryProduct);
    }

    /**
     * 修改
     */
    @RequestMapping("/mall/mallcategoryproduct/update")
    ResponseMessage update(@RequestBody MallCategoryProductEntity mallCategoryProduct){
        return mallCategoryProductService.update(oauth2SecuritySubject.getCurrentUser(),mallCategoryProduct);
    }

    /**
     * 删除
     */
    @RequestMapping("/mall/mallcategoryproduct/delete")
    ResponseMessage delete(@RequestBody Long[] ids){
        return mallCategoryProductService.delete(oauth2SecuritySubject.getCurrentUser(),ids);
    }

}
