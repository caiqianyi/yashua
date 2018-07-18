package com.lebaoxun.modules.mall.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.modules.mall.entity.MallCartEntity;
import com.lebaoxun.modules.mall.pojo.MallProductCartVo;

/**
 * 购物车表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
public interface MallCartService extends IService<MallCartEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    void sync(Long userId,List<MallCartEntity> list);
    
    List<MallProductCartVo> findByUser(Long userId);
    
    List<MallProductCartVo> queryByProductSpecId(Long[] ids);
    
}

