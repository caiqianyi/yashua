package com.lebaoxun.modules.rest.mall;

import java.util.Map;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.mall.entity.MallSpecificationEntity;
import com.lebaoxun.modules.mall.service.IMallSpecificationService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;



/**
 * 规格表

 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
@RestController
public class MallSpecificationController {
    @Autowired
    private IMallSpecificationService mallSpecificationService;
    
    @Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;

    /**
     * 列表
     */
    @RequestMapping("/mall/mallspecification/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        return mallSpecificationService.list(params);
    }


    /**
     * 信息
     */
    @RequestMapping("/mall/mallspecification/info/{specificationId}")
    ResponseMessage info(@PathVariable("specificationId") Long specificationId){
        return mallSpecificationService.info(specificationId);
    }

    /**
     * 保存
     */
    @RequestMapping("/mall/mallspecification/save")
    ResponseMessage save(@RequestBody MallSpecificationEntity mallSpecification){
        return mallSpecificationService.save(oauth2SecuritySubject.getCurrentUser(),mallSpecification);
    }

    /**
     * 修改
     */
    @RequestMapping("/mall/mallspecification/update")
    ResponseMessage update(@RequestBody MallSpecificationEntity mallSpecification){
        return mallSpecificationService.update(oauth2SecuritySubject.getCurrentUser(),mallSpecification);
    }

    /**
     * 删除
     */
    @RequestMapping("/mall/mallspecification/delete")
    ResponseMessage delete(@RequestBody Long[] specificationIds){
        return mallSpecificationService.delete(oauth2SecuritySubject.getCurrentUser(),specificationIds);
    }

}
