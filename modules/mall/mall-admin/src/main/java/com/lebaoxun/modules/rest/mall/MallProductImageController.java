package com.lebaoxun.modules.rest.mall;

import java.util.Map;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.mall.entity.MallProductImageEntity;
import com.lebaoxun.modules.mall.service.IMallProductImageService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;



/**
 * 商品图片表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
@RestController
public class MallProductImageController {
    @Autowired
    private IMallProductImageService mallProductImageService;
    
    @Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;

    /**
     * 列表
     */
    @RequestMapping("/mall/mallproductimage/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        return mallProductImageService.list(params);
    }


    /**
     * 信息
     */
    @RequestMapping("/mall/mallproductimage/info/{picImgId}")
    ResponseMessage info(@PathVariable("picImgId") Long picImgId){
        return mallProductImageService.info(picImgId);
    }

    /**
     * 保存
     */
    @RequestMapping("/mall/mallproductimage/save")
    ResponseMessage save(@RequestBody MallProductImageEntity mallProductImage){
        return mallProductImageService.save(oauth2SecuritySubject.getCurrentUser(),mallProductImage);
    }

    /**
     * 修改
     */
    @RequestMapping("/mall/mallproductimage/update")
    ResponseMessage update(@RequestBody MallProductImageEntity mallProductImage){
        return mallProductImageService.update(oauth2SecuritySubject.getCurrentUser(),mallProductImage);
    }

    /**
     * 删除
     */
    @RequestMapping("/mall/mallproductimage/delete")
    ResponseMessage delete(@RequestBody Long[] picImgIds){
        return mallProductImageService.delete(oauth2SecuritySubject.getCurrentUser(),picImgIds);
    }

}
