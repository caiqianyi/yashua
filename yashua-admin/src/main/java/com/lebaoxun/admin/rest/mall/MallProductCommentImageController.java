package com.lebaoxun.admin.rest.mall;

import java.util.Map;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.mall.entity.MallProductCommentImageEntity;
import com.lebaoxun.modules.mall.service.IMallProductCommentImageService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;



/**
 * 评价图片表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:11
 */
@RestController
public class MallProductCommentImageController {
    @Autowired
    private IMallProductCommentImageService mallProductCommentImageService;
    
    @Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;

    /**
     * 列表
     */
    @RequestMapping("/mall/mallproductcommentimage/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        return mallProductCommentImageService.list(params);
    }


    /**
     * 信息
     */
    @RequestMapping("/mall/mallproductcommentimage/info/{id}")
    ResponseMessage info(@PathVariable("id") Long id){
        return mallProductCommentImageService.info(id);
    }

    /**
     * 保存
     */
    @RequestMapping("/mall/mallproductcommentimage/save")
    ResponseMessage save(@RequestBody MallProductCommentImageEntity mallProductCommentImage){
        return mallProductCommentImageService.save(oauth2SecuritySubject.getCurrentUser(),mallProductCommentImage);
    }

    /**
     * 修改
     */
    @RequestMapping("/mall/mallproductcommentimage/update")
    ResponseMessage update(@RequestBody MallProductCommentImageEntity mallProductCommentImage){
        return mallProductCommentImageService.update(oauth2SecuritySubject.getCurrentUser(),mallProductCommentImage);
    }

    /**
     * 删除
     */
    @RequestMapping("/mall/mallproductcommentimage/delete")
    ResponseMessage delete(@RequestBody Long[] ids){
        return mallProductCommentImageService.delete(oauth2SecuritySubject.getCurrentUser(),ids);
    }

}
