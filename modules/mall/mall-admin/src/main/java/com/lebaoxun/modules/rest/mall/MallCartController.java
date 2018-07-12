package com.lebaoxun.modules.rest.mall;

import java.util.Map;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.mall.entity.MallCartEntity;
import com.lebaoxun.modules.mall.service.IMallCartService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;



/**
 * 购物车表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
@RestController
public class MallCartController {
    @Autowired
    private IMallCartService mallCartService;
    
    @Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;

    /**
     * 列表
     */
    @RequestMapping("/mall/mallcart/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        return mallCartService.list(params);
    }


    /**
     * 信息
     */
    @RequestMapping("/mall/mallcart/info/{cartId}")
    ResponseMessage info(@PathVariable("cartId") Long cartId){
        return mallCartService.info(cartId);
    }

    /**
     * 保存
     */
    @RequestMapping("/mall/mallcart/save")
    ResponseMessage save(@RequestBody MallCartEntity mallCart){
        return mallCartService.save(oauth2SecuritySubject.getCurrentUser(),mallCart);
    }

    /**
     * 修改
     */
    @RequestMapping("/mall/mallcart/update")
    ResponseMessage update(@RequestBody MallCartEntity mallCart){
        return mallCartService.update(oauth2SecuritySubject.getCurrentUser(),mallCart);
    }

    /**
     * 删除
     */
    @RequestMapping("/mall/mallcart/delete")
    ResponseMessage delete(@RequestBody Long[] cartIds){
        return mallCartService.delete(oauth2SecuritySubject.getCurrentUser(),cartIds);
    }

}
