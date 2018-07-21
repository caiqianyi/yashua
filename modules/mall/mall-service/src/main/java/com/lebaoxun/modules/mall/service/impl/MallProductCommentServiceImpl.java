package com.lebaoxun.modules.mall.service.impl;

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
import com.lebaoxun.modules.mall.dao.MallOrderDao;
import com.lebaoxun.modules.mall.dao.MallOrderProductDao;
import com.lebaoxun.modules.mall.dao.MallProductCommentDao;
import com.lebaoxun.modules.mall.dao.MallProductCommentImageDao;
import com.lebaoxun.modules.mall.entity.MallOrderEntity;
import com.lebaoxun.modules.mall.entity.MallOrderProductEntity;
import com.lebaoxun.modules.mall.entity.MallProductCommentEntity;
import com.lebaoxun.modules.mall.entity.MallProductCommentImageEntity;
import com.lebaoxun.modules.mall.service.MallProductCommentService;


@Service("mallProductCommentService")
public class MallProductCommentServiceImpl extends ServiceImpl<MallProductCommentDao, MallProductCommentEntity> implements MallProductCommentService {

	@Resource
	private MallOrderDao mallOrderDao;
	
	@Resource
	private MallOrderProductDao mallOrderProductDao;
	
	@Resource
	private MallProductCommentImageDao mallProductCommentImageDao;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<MallProductCommentEntity> page = this.selectPage(
                new Query<MallProductCommentEntity>(params).getPage(),
                new EntityWrapper<MallProductCommentEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void publish(Long userId, Long orderProductId,
			MallProductCommentEntity comment) {
		// TODO Auto-generated method stub
		MallOrderProductEntity mope = mallOrderDao.selectOrderProductByOrderProductId(userId, orderProductId);
		if(mope == null || mope.getStatus() != 3){
			throw new I18nMessageException("500");
		}
		
		List<MallOrderProductEntity> list = mallOrderDao.selectOrderProductByOrderId(mope.getOrderId());
		boolean isAll = true;//是否全部评价
		for(MallOrderProductEntity oProduct: list){
			if(oProduct.getOrderProductId() != orderProductId && oProduct.getStatus() != 4){//有一个未评价
				isAll = false;
				break;
			}
		}
		//修改订单状态为已评价
		mope.setStatus(4);
		mallOrderProductDao.updateById(mope);
		
		if(isAll){
			MallOrderEntity order = mallOrderDao.selectById(mope.getOrderId());
			order.setOrderStatus(4);
			mallOrderDao.updateById(order);
		}
		comment.setProductId(mope.getProductId());
		this.baseMapper.save(comment);
		List<MallProductCommentImageEntity> images = comment.getPicImgs();
		for(MallProductCommentImageEntity commentImg : images){
			commentImg.setProductCommentId(comment.getId());
			mallProductCommentImageDao.insert(commentImg);
		}
	}

	@Override
	public List<MallProductCommentEntity> selectByProduct(Long productId) {
		// TODO Auto-generated method stub
		return this.baseMapper.selectByProduct(productId);
	}

	@Override
	public MallProductCommentEntity selectLastByProduct(Long productId) {
		// TODO Auto-generated method stub
		return this.baseMapper.selectLastByProduct(productId);
	}

}
