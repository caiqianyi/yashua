package com.lebaoxun.modules.rest.mall;

import java.util.Map;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.mall.entity.MallProductCommentEntity;
import com.lebaoxun.modules.mall.service.IMallProductCommentService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;



/**
 * 评价表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:11
 */
@RestController
public class MallProductCommentController {
    @Autowired
    private IMallProductCommentService mallProductCommentService;
    
    @Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;

    /**
     * 列表
     */
    @RequestMapping("/mall/mallproductcomment/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        return mallProductCommentService.list(params);
    }


    /**
     * 信息
     */
    @RequestMapping("/mall/mallproductcomment/info/{id}")
    ResponseMessage info(@PathVariable("id") Long id){
        return mallProductCommentService.info(id);
    }

    /**
     * 保存
     */
    @RequestMapping("/mall/mallproductcomment/save")
    ResponseMessage save(@RequestBody MallProductCommentEntity mallProductComment){
        return mallProductCommentService.save(oauth2SecuritySubject.getCurrentUser(),mallProductComment);
    }

    /**
     * 修改
     */
    @RequestMapping("/mall/mallproductcomment/update")
    ResponseMessage update(@RequestBody MallProductCommentEntity mallProductComment){
        return mallProductCommentService.update(oauth2SecuritySubject.getCurrentUser(),mallProductComment);
    }

    /**
     * 删除
     */
    @RequestMapping("/mall/mallproductcomment/delete")
    ResponseMessage delete(@RequestBody Long[] ids){
        return mallProductCommentService.delete(oauth2SecuritySubject.getCurrentUser(),ids);
    }

}
