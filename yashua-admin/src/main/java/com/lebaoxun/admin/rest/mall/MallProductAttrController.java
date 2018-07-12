package com.lebaoxun.admin.rest.mall;

import java.util.Map;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.mall.entity.MallProductAttrEntity;
import com.lebaoxun.modules.mall.service.IMallProductAttrService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;



/**
 * 商品属性表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
@RestController
public class MallProductAttrController {
    @Autowired
    private IMallProductAttrService mallProductAttrService;
    
    @Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;

    /**
     * 列表
     */
    @RequestMapping("/mall/mallproductattr/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        return mallProductAttrService.list(params);
    }


    /**
     * 信息
     */
    @RequestMapping("/mall/mallproductattr/info/{id}")
    ResponseMessage info(@PathVariable("id") Long id){
        return mallProductAttrService.info(id);
    }

    /**
     * 保存
     */
    @RequestMapping("/mall/mallproductattr/save")
    ResponseMessage save(@RequestBody MallProductAttrEntity mallProductAttr){
        return mallProductAttrService.save(oauth2SecuritySubject.getCurrentUser(),mallProductAttr);
    }

    /**
     * 修改
     */
    @RequestMapping("/mall/mallproductattr/update")
    ResponseMessage update(@RequestBody MallProductAttrEntity mallProductAttr){
        return mallProductAttrService.update(oauth2SecuritySubject.getCurrentUser(),mallProductAttr);
    }

    /**
     * 删除
     */
    @RequestMapping("/mall/mallproductattr/delete")
    ResponseMessage delete(@RequestBody Long[] ids){
        return mallProductAttrService.delete(oauth2SecuritySubject.getCurrentUser(),ids);
    }

}
