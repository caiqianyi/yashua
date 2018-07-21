package com.lebaoxun.modules.mall.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.modules.mall.entity.MallProductCommentEntity;

/**
 * 评价表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:11
 */
public interface MallProductCommentService extends
		IService<MallProductCommentEntity> {

	PageUtils queryPage(Map<String, Object> params);

	void publish(Long userId, Long orderProductId,
			MallProductCommentEntity comment);
	
	List<MallProductCommentEntity> selectByProduct(Long productId);
	
	MallProductCommentEntity selectLastByProduct(Long productId);
}
