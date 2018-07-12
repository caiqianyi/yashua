package com.lebaoxun.modules.rest.mall;

import java.util.Map;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.mall.entity.MallSpecificationAttributeEntity;
import com.lebaoxun.modules.mall.service.IMallSpecificationAttributeService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;



/**
 * 规格属性表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
@RestController
public class MallSpecificationAttributeController {
    @Autowired
    private IMallSpecificationAttributeService mallSpecificationAttributeService;
    
    @Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;

    /**
     * 列表
     */
    @RequestMapping("/mall/mallspecificationattribute/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        return mallSpecificationAttributeService.list(params);
    }


    /**
     * 信息
     */
    @RequestMapping("/mall/mallspecificationattribute/info/{specAttrId}")
    ResponseMessage info(@PathVariable("specAttrId") Long specAttrId){
        return mallSpecificationAttributeService.info(specAttrId);
    }

    /**
     * 保存
     */
    @RequestMapping("/mall/mallspecificationattribute/save")
    ResponseMessage save(@RequestBody MallSpecificationAttributeEntity mallSpecificationAttribute){
        return mallSpecificationAttributeService.save(oauth2SecuritySubject.getCurrentUser(),mallSpecificationAttribute);
    }

    /**
     * 修改
     */
    @RequestMapping("/mall/mallspecificationattribute/update")
    ResponseMessage update(@RequestBody MallSpecificationAttributeEntity mallSpecificationAttribute){
        return mallSpecificationAttributeService.update(oauth2SecuritySubject.getCurrentUser(),mallSpecificationAttribute);
    }

    /**
     * 删除
     */
    @RequestMapping("/mall/mallspecificationattribute/delete")
    ResponseMessage delete(@RequestBody Long[] specAttrIds){
        return mallSpecificationAttributeService.delete(oauth2SecuritySubject.getCurrentUser(),specAttrIds);
    }

}
