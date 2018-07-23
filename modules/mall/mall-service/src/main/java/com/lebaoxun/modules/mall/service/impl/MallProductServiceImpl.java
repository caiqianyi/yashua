package com.lebaoxun.modules.mall.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;
import com.lebaoxun.modules.mall.dao.MallCategoryProductDao;
import com.lebaoxun.modules.mall.dao.MallProductAttrDao;
import com.lebaoxun.modules.mall.dao.MallProductDao;
import com.lebaoxun.modules.mall.dao.MallProductSpecificationDao;
import com.lebaoxun.modules.mall.entity.MallProductAttrEntity;
import com.lebaoxun.modules.mall.entity.MallProductEntity;
import com.lebaoxun.modules.mall.service.MallProductService;


@Service("mallProductService")
public class MallProductServiceImpl extends ServiceImpl<MallProductDao, MallProductEntity> implements MallProductService {

	@Resource
	private MallCategoryProductDao mallCategoryProductDao;
	
	@Resource
	private MallProductAttrDao mallProductAttrDao;
	
	@Resource
	private MallProductSpecificationDao mallProductSpecificationDao;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	String categoryId = (String)params.get("categoryId"),
    			productNumber = (String) params.get("productNumber");
    	Page<MallProductEntity> page = null;
    	if(StringUtils.isNotBlank(categoryId)
    			&& StringUtils.isNumeric(categoryId)){
    		Long catId = Long.parseLong(categoryId);
    		Long proNum = StringUtils.isNotBlank(productNumber) &&
    				        StringUtils.isNumeric(productNumber) ? Long.parseLong(productNumber) : null;
    		page = new Query<MallProductEntity>(params).getPage();
    		page.setRecords(this.baseMapper.findProductByCategory(catId, proNum, null, page.getLimit(), page.getOffset()));
            page.setTotal(this.baseMapper.countProductByCategory(catId, proNum, null));
    	}else{
    		page = this.selectPage(
    				new Query<MallProductEntity>(params).getPage(),
    				new EntityWrapper<MallProductEntity>().eq(StringUtils.isNotBlank(productNumber)&&StringUtils.isNumeric(productNumber), "product_number", productNumber)
    				);
    	}
        return new PageUtils(page);
    }
    
    @Override
    public List<MallProductEntity> findShowProdcutByCategory(Long categoryId,
    		Integer size, Integer offset) {
    	// TODO Auto-generated method stub
    	return this.baseMapper.findShowProdcutByCategory(categoryId, size, offset);
    }

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void update(MallProductEntity mallProduct) {
		// TODO Auto-generated method stub
		MallProductEntity mpe = this.selectById(mallProduct.getId());
		if(mpe == null){
			throw new I18nMessageException("500");
		}
		
		Integer showInShelve = mallProduct.getShowInShelve();
		if(1 == showInShelve){//如果上架
			mpe.setShowInShelve(showInShelve);
			mpe.setShelveBy(mallProduct.getUpdateBy());
			mpe.setShelveTime(new Date());
		}
		mpe.setProductNumber(mallProduct.getProductNumber());
		mpe.setDescription(mallProduct.getDescription());
		mpe.setIntroduce(mallProduct.getIntroduce());
		mpe.setName(mallProduct.getName());
		mpe.setRemarks(mallProduct.getRemarks());
		mpe.setSearchKey(mallProduct.getSearchKey());
		mpe.setShareDescription(mallProduct.getShareDescription());
		mpe.setShareTitle(mallProduct.getShareTitle());
		mpe.setShowInHot(mallProduct.getShowInHot());
		mpe.setShowInTop(mallProduct.getShowInTop());
		mpe.setShowPic(mallProduct.getShowPic());
		mpe.setShowPrice(mallProduct.getShowPrice());
		mpe.setShowScore(mallProduct.getShowScore());
		mpe.setUpdateBy(mallProduct.getUpdateBy());
		mpe.setUpdateTime(new Date());
		
		this.baseMapper.updateById(mpe);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void delete(Long id) {
		// TODO Auto-generated method stub
		this.baseMapper.deleteById(id);
		mallProductAttrDao.deleteByProduct(id);
		mallCategoryProductDao.deleteByProduct(id);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void create(MallProductEntity mallProduct) {
		// TODO Auto-generated method stub
		MallProductEntity mpe = this.selectOne(new EntityWrapper<MallProductEntity>().eq("product_number", mallProduct.getProductNumber()));
		if(mpe != null){
			throw new I18nMessageException("-1","商品编号已存在！");
		}
		this.baseMapper.addProduct(mallProduct);
		MallProductAttrEntity mallProductAttr = new MallProductAttrEntity();
		mallProductAttr.setClicks(0);
		mallProductAttr.setCommentAverage(new BigDecimal("0"));
		mallProductAttr.setFavoriteNumber(0);
		mallProductAttr.setProductId(mallProduct.getId());
		mallProductAttr.setQuestionNumber(0);
		mallProductAttr.setReplies(0);
		mallProductAttr.setSalesVolume(0);
		mallProductAttr.setShowReplies(0);
		mallProductAttr.setStock(0);
		mallProductAttrDao.insert(mallProductAttr);
	}

	@Override
	public MallProductEntity findShowProdcutInfo(Long id) {
		// TODO Auto-generated method stub
		return this.baseMapper.findShowProdcutInfo(id);
	}
	
	@Override
	public List<MallProductEntity> findShowProdcutByHaveScore(Integer size,
			Integer offset) {
		// TODO Auto-generated method stub
		return this.baseMapper.findShowProdcutByHaveScore(size, offset);
	}
}
