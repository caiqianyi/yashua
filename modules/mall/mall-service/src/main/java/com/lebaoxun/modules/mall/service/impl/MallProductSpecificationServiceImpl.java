package com.lebaoxun.modules.mall.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;
import com.lebaoxun.modules.mall.dao.MallProductAttrDao;
import com.lebaoxun.modules.mall.dao.MallProductDao;
import com.lebaoxun.modules.mall.dao.MallProductSpecificationDao;
import com.lebaoxun.modules.mall.entity.MallProductAttrEntity;
import com.lebaoxun.modules.mall.entity.MallProductEntity;
import com.lebaoxun.modules.mall.entity.MallProductSpecificationEntity;
import com.lebaoxun.modules.mall.service.MallProductSpecificationService;

@Service("mallProductSpecificationService")
public class MallProductSpecificationServiceImpl
		extends
		ServiceImpl<MallProductSpecificationDao, MallProductSpecificationEntity>
		implements MallProductSpecificationService {

	@Resource
	private MallProductDao mallProductDao;

	@Resource
	private MallProductAttrDao mallProductAttrDao;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Page<MallProductSpecificationEntity> page = this.selectPage(
				new Query<MallProductSpecificationEntity>(params).getPage(),
				new EntityWrapper<MallProductSpecificationEntity>());

		return new PageUtils(page);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void save(MallProductSpecificationEntity specification) {
		MallProductSpecificationEntity mps = this
				.selectOne(new EntityWrapper<MallProductSpecificationEntity>()
						.eq("product_id",
								specification.getProductId()).eq("spec_attr_id", specification.getSpecAttrId()));
		if (mps != null) {
			throw new I18nMessageException("-1", "规格已存在,请重新设置");
		}
		MallProductEntity product = mallProductDao.selectById(specification
				.getProductId());
		if (product == null) {
			throw new I18nMessageException("-1", "商品不存在");
		}
		if (specification.getStock() < 0) {
			throw new I18nMessageException("-1", "库存不能为负数");
		}
		List<MallProductSpecificationEntity> list = this
				.selectList(new EntityWrapper<MallProductSpecificationEntity>()
						.eq("product_id", specification.getProductId()));
		Integer stock = specification.getStock();
		for (MallProductSpecificationEntity item : list) {
			stock += item.getStock();
		}

		MallProductAttrEntity mallProductAttr = new MallProductAttrEntity();
		mallProductAttr.setStock(stock);
		mallProductAttrDao.update(mallProductAttr,
				new EntityWrapper<MallProductAttrEntity>().eq("product_id",
						specification.getProductId()));
		this.baseMapper.insert(specification);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void update(MallProductSpecificationEntity specification) {
		MallProductSpecificationEntity mps = this
				.selectOne(new EntityWrapper<MallProductSpecificationEntity>()
						.eq("product_spec_number",
								specification.getProductSpecNumber()));
		if (mps != null) {
			throw new I18nMessageException("-1", "规格编号已存在,请重新设置");
		}
		// TODO Auto-generated method stub
		List<MallProductSpecificationEntity> list = this
				.selectList(new EntityWrapper<MallProductSpecificationEntity>()
						.eq("product_id", specification.getProductId()));
		Integer stock = specification.getStock();
		for (MallProductSpecificationEntity item : list) {
			stock += item.getStock();
		}

		MallProductAttrEntity mallProductAttr = new MallProductAttrEntity();
		mallProductAttr.setStock(stock);
		mallProductAttrDao.update(mallProductAttr,
				new EntityWrapper<MallProductAttrEntity>().eq("product_id",
						specification.getProductId()));
		this.baseMapper.update(specification, new EntityWrapper<MallProductSpecificationEntity>().eq("product_spec_id",
						specification.getProductSpecId()));
	}

	@Override
	public List<MallProductSpecificationEntity> queryByProductId(Long productId) {
		// TODO Auto-generated method stub
		return this.baseMapper.queryByProductId(productId);
	}

}
