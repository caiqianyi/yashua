package com.lebaoxun.modules.mall.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.mall.entity.MallProductCommentEntity;
import com.lebaoxun.modules.mall.entity.MallProductCommentImageEntity;
import com.lebaoxun.modules.mall.service.MallProductCommentService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.soa.core.redis.lock.RedisLock;

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
	private MallProductCommentService mallProductCommentService;

	@RequestMapping("/mall/mallproductcomment/publish")
	ResponseMessage publish(@RequestParam("userId") Long userId,
			@RequestParam("orderProductId") Long orderProductId,
			@RequestBody MallProductCommentEntity comment) {
		mallProductCommentService.publish(userId, orderProductId, comment);
		return ResponseMessage.ok();
	}

	@RequestMapping("/mall/mallproductcomment/selectByProduct")
	ResponseMessage selectByProduct(
			@RequestParam("productId") Long productId) {
		return ResponseMessage.ok(mallProductCommentService
				.selectLastByProduct(productId));
	}
	
	@RequestMapping("/mall/mallproductcomment/selectLastByProduct")
	MallProductCommentEntity selectLastByProduct(@RequestParam("productId") Long productId){
		return mallProductCommentService.selectLastByProduct(productId);
	}

	/**
	 * 列表
	 */
	@RequestMapping("/mall/mallproductcomment/list")
	ResponseMessage list(@RequestParam Map<String, Object> params) {
		PageUtils page = mallProductCommentService.queryPage(params);
		return ResponseMessage.ok(page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/mall/mallproductcomment/info/{id}")
	ResponseMessage info(@PathVariable("id") Long id) {
		MallProductCommentEntity mallProductComment = mallProductCommentService
				.selectById(id);
		return ResponseMessage.ok().put("mallProductComment",
				mallProductComment);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/mall/mallproductcomment/save")
	@RedisLock(value = "mall:mallproductcomment:save:lock:#arg0")
	ResponseMessage save(@RequestParam("adminId") Long adminId,
			@RequestBody MallProductCommentEntity mallProductComment) {
		mallProductCommentService.insert(mallProductComment);
		return ResponseMessage.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/mall/mallproductcomment/update")
	@RedisLock(value = "mall:mallproductcomment:update:lock:#arg0")
	ResponseMessage update(@RequestParam("adminId") Long adminId,
			@RequestBody MallProductCommentEntity mallProductComment) {
		mallProductCommentService.updateById(mallProductComment);
		return ResponseMessage.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/mall/mallproductcomment/delete")
	@RedisLock(value = "mall:mallproductcomment:delete:lock:#arg0")
	ResponseMessage delete(@RequestParam("adminId") Long adminId,
			@RequestBody Long[] ids) {
		mallProductCommentService.deleteBatchIds(Arrays.asList(ids));
		return ResponseMessage.ok();
	}

}
