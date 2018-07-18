package com.lebaoxun.modules.mall.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;
import com.lebaoxun.modules.mall.dao.MallCartDao;
import com.lebaoxun.modules.mall.entity.MallCartEntity;
import com.lebaoxun.modules.mall.pojo.MallProductCartVo;
import com.lebaoxun.modules.mall.service.MallCartService;


@Service("mallCartService")
public class MallCartServiceImpl extends ServiceImpl<MallCartDao, MallCartEntity> implements MallCartService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<MallCartEntity> page = this.selectPage(
                new Query<MallCartEntity>(params).getPage(),
                new EntityWrapper<MallCartEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void sync(Long userId, List<MallCartEntity> list) {
		List<MallCartEntity> records = this.selectList(new EntityWrapper<MallCartEntity>().eq("user_id", userId));
		
		List<Long> productSpecIds = new ArrayList<Long>();
		for(MallCartEntity cart : list){
			productSpecIds.add(cart.getProductSpecId());
		}
		for(MallCartEntity entity : records){
			if(!productSpecIds.contains(entity.getProductSpecId())){
				this.baseMapper.delete(new EntityWrapper<MallCartEntity>().eq("product_spec_id",entity.getProductSpecId()));
			}else{
				MallCartEntity mce = list.remove(productSpecIds.indexOf(entity.getProductSpecId()));
				entity.setBuyNumber(mce.getBuyNumber());
				entity.setCheckStatus(mce.getCheckStatus());
				entity.setUpdateTime(new Date());
				this.baseMapper.updateById(entity);
				productSpecIds.remove(entity.getProductSpecId());
			}
		}
		for(MallCartEntity cart : list){
			cart.setCartId(0);
			cart.setUserId(userId);
			cart.setCreateTime(new Date());
			cart.setUpdateTime(null);
			this.baseMapper.insert(cart);
		}
	}

	@Override
	public List<MallProductCartVo> findByUser(Long userId) {
		// TODO Auto-generated method stub
		return this.baseMapper.queryByUser(userId);
	}

	@Override
	public List<MallProductCartVo> queryByProductSpecId(Long[] ids) {
		// TODO Auto-generated method stub
		return this.baseMapper.queryByProductSpecId(ids);
	}

}