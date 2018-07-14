package com.lebaoxun.modules.mall.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lebaoxun.modules.mall.dao.MallCategoryProductDao;
import com.lebaoxun.modules.mall.entity.MallCategoryProductEntity;
import com.lebaoxun.modules.mall.pojo.MallCategoryProductVo;
import com.lebaoxun.modules.mall.service.MallCategoryProductService;


@Service("mallCategoryProductService")
public class MallCategoryProductServiceImpl extends ServiceImpl<MallCategoryProductDao, MallCategoryProductEntity> implements MallCategoryProductService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public List<MallCategoryProductVo> findByProduct(Long productId) {
		// TODO Auto-generated method stub
		return this.baseMapper.findByProduct(productId);
	}
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void edit(Long adminId,Long productId, Long[] categoryIds) {
		// TODO Auto-generated method stub
		List<MallCategoryProductVo> mcpvs = this.baseMapper.findByProduct(productId);
		List<Long> ids = new ArrayList<Long>();
		ids.addAll(Arrays.asList(categoryIds));
		for(MallCategoryProductVo mcpv : mcpvs){
			boolean isFind = false;
			for(Long id : categoryIds){
				if(mcpv.getCategoryId().equals(id)){
					ids.remove(id);
					isFind = true;
					break;
				}
			}
			if(!isFind){
				this.baseMapper.deleteById(mcpv.getId());
			}
		}
		logger.debug("ids={},categoryIds={}",ids,categoryIds);
		for(Long id : ids){
			MallCategoryProductEntity mcpe = new MallCategoryProductEntity();
			mcpe.setCategoryId(id);
			mcpe.setCreateBy(adminId+"");
			mcpe.setCreateTime(new Date());
			mcpe.setProductId(productId);
			this.baseMapper.insert(mcpe);
		}
	}

	
}
