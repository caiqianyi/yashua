package com.lebaoxun.admin.rest.mall;

import java.util.Map;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.mall.entity.MallProductEntity;
import com.lebaoxun.modules.mall.service.IMallProductService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;



/**
 * 商品表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
@RestController
public class MallProductController {
    @Autowired
    private IMallProductService mallProductService;
    
    @Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;

    /**
     * 列表
     */
    @RequestMapping("/mall/mallproduct/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        return mallProductService.list(params);
    }


    /**
     * 信息
     */
    @RequestMapping("/mall/mallproduct/info/{id}")
    ResponseMessage info(@PathVariable("id") Long id){
        return mallProductService.info(id);
    }

    /**
     * 保存
     */
    @RequestMapping("/mall/mallproduct/save")
    ResponseMessage save(@RequestBody MallProductEntity mallProduct){
        return mallProductService.save(oauth2SecuritySubject.getCurrentUser(),mallProduct);
    }

    /**
     * 修改
     */
    @RequestMapping("/mall/mallproduct/update")
    ResponseMessage update(@RequestBody MallProductEntity mallProduct){
        return mallProductService.update(oauth2SecuritySubject.getCurrentUser(),mallProduct);
    }

    /**
     * 删除
     */
    @RequestMapping("/mall/mallproduct/delete")
    ResponseMessage delete(@RequestBody Long[] ids){
        return mallProductService.delete(oauth2SecuritySubject.getCurrentUser(),ids);
    }

}
