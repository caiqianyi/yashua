package com.lebaoxun.modules.rest.mall;

import java.util.Map;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.mall.entity.MallCategoryEntity;
import com.lebaoxun.modules.mall.service.IMallCategoryService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;



/**
 * 分类表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
@RestController
public class MallCategoryController {
    @Autowired
    private IMallCategoryService mallCategoryService;
    
    @Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;

    /**
     * 列表
     */
    @RequestMapping("/mall/mallcategory/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        return mallCategoryService.list(params);
    }


    /**
     * 信息
     */
    @RequestMapping("/mall/mallcategory/info/{id}")
    ResponseMessage info(@PathVariable("id") Long id){
        return mallCategoryService.info(id);
    }

    /**
     * 保存
     */
    @RequestMapping("/mall/mallcategory/save")
    ResponseMessage save(@RequestBody MallCategoryEntity mallCategory){
        return mallCategoryService.save(oauth2SecuritySubject.getCurrentUser(),mallCategory);
    }

    /**
     * 修改
     */
    @RequestMapping("/mall/mallcategory/update")
    ResponseMessage update(@RequestBody MallCategoryEntity mallCategory){
        return mallCategoryService.update(oauth2SecuritySubject.getCurrentUser(),mallCategory);
    }

    /**
     * 删除
     */
    @RequestMapping("/mall/mallcategory/delete")
    ResponseMessage delete(@RequestBody Long[] ids){
        return mallCategoryService.delete(oauth2SecuritySubject.getCurrentUser(),ids);
    }

}
