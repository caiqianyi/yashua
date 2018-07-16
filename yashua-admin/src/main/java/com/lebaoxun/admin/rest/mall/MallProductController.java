package com.lebaoxun.admin.rest.mall;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.admin.rest.BaseController;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.mall.entity.MallProductEntity;
import com.lebaoxun.modules.mall.service.IMallProductService;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;
import com.lebaoxun.upload.service.IUploadLocalService;



/**
 * 商品表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
@RestController
public class MallProductController extends BaseController {
    @Autowired
    private IMallProductService mallProductService;
    
    @Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;
    
    @Resource
	private IUploadLocalService uploadLocalService;

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
    	mallProduct.setCreateBy(oauth2SecuritySubject.getCurrentUser()+"");
    	mallProduct.setCreateTime(new Date());
        return mallProductService.save(oauth2SecuritySubject.getCurrentUser(),mallProduct);
    }

    /**
     * 修改
     */
    @RequestMapping("/mall/mallproduct/update")
    ResponseMessage update(@RequestBody MallProductEntity mallProduct){
    	Long userId = oauth2SecuritySubject.getCurrentUser();
    	mallProduct.setUpdateBy(userId+"");
        return mallProductService.update(userId,mallProduct);
    }

    /**
     * 删除
     */
    @RequestMapping("/mall/mallproduct/delete")
    ResponseMessage delete(@RequestParam("id") Long id){
    	ResponseMessage rm = mallProductService.info(id);
    	LinkedHashMap<String,Object> map = (LinkedHashMap) rm.getData();
    	logger.debug("rm.data.class={}",rm.getData().getClass());
    	logger.debug("showPic={}",((LinkedHashMap<String,Object> )map.get("mallProduct")).get("showPic"));
    	rm = mallProductService.delete(oauth2SecuritySubject.getCurrentUser(),id);
    	if("0".equals(rm.getErrcode())){
    		uploadLocalService.deleteFile("yashua", ((LinkedHashMap<String,Object> )map.get("mallProduct")).get("showPic").toString());
    	}
        return rm;
    }

}
