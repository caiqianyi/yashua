package com.lebaoxun.modules.mall.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;
import com.lebaoxun.modules.mall.dao.MallCartDao;
import com.lebaoxun.modules.mall.dao.MallOrderDao;
import com.lebaoxun.modules.mall.dao.MallOrderProductDao;
import com.lebaoxun.modules.mall.dao.MallProductAttrDao;
import com.lebaoxun.modules.mall.dao.MallProductDao;
import com.lebaoxun.modules.mall.dao.MallProductSpecificationDao;
import com.lebaoxun.modules.mall.entity.MallCartEntity;
import com.lebaoxun.modules.mall.entity.MallOrderEntity;
import com.lebaoxun.modules.mall.entity.MallOrderProductEntity;
import com.lebaoxun.modules.mall.entity.MallProductEntity;
import com.lebaoxun.modules.mall.entity.MallProductSpecificationEntity;
import com.lebaoxun.modules.mall.pojo.MallProductCartVo;
import com.lebaoxun.modules.mall.service.MallOrderService;


@Service("mallOrderService")
public class MallOrderServiceImpl extends ServiceImpl<MallOrderDao, MallOrderEntity> implements MallOrderService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private MallProductDao mallProductDao;
	
	@Resource
	private MallProductSpecificationDao mallProductSpecificationDao;

	@Resource
	private MallProductAttrDao mallProductAttrDao;
	
	@Resource
	private MallCartDao mallCartDao;
	
	@Resource
	private MallOrderProductDao mallOrderProductDao;
	
	@Resource
	private RedisTemplate<String,Object> redisTemplate;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<MallOrderEntity> page = this.selectPage(
                new Query<MallOrderEntity>(params).getPage(),
                new EntityWrapper<MallOrderEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String create(Long userId, List<MallCartEntity> products) {
		List<Long> productSpecIds = new ArrayList<Long>();
		for(MallCartEntity mope : products){
			if(mope.getProductSpecId() != null){
				productSpecIds.add(mope.getProductSpecId());
			}
		}
		Date now = new Date();
		List<MallProductCartVo> mpcvs = mallCartDao.queryByProductSpecId(productSpecIds.toArray(new Long[]{}));
		if(mpcvs.size() != products.size()){
			throw new I18nMessageException("-1","商品不存在或已下架");
		}
		String date = DateFormatUtils.format(now, "yyyyMMdd");
		String key = "mall:orderNo:"+date;
		Integer inr = (Integer)redisTemplate.opsForValue().get(key);
		if(inr == null){
			inr = RandomUtils.nextInt(10000);
			redisTemplate.opsForValue().set(key, inr, 25l, TimeUnit.valueOf("HOURS"));
		}else{
			inr = redisTemplate.opsForValue().increment(key, 1).intValue();
		}
		String orderNo = "lv"+date+format1(inr,5);
		logger.debug("orderNo={}",orderNo);
		
		MallOrderEntity order = new MallOrderEntity();
		order.setCreateTime(now);
		order.setUserId(userId);
		order.setOrderStatus(0);
		order.setPayType(1);//在线支付
		order.setShipmentAmount(new BigDecimal(0));//快递费
		order.setOrderNo(orderNo);
		Integer totalBuyNumber = 0;
		BigDecimal totalMoney = new BigDecimal(0);
		List<MallOrderProductEntity> mopes = new ArrayList<MallOrderProductEntity>();
		for(MallProductCartVo mpcv : mpcvs){
			MallProductSpecificationEntity mpse = mpcv.getSpec();
			MallProductEntity mpe = mpcv.getProduct();
			if(mpse == null || mpse.getStatus() != 1){
				throw new I18nMessageException("-1","“"+mpe.getName()+"”商品不存在或已下架");
			}
			MallCartEntity cart = products.get(productSpecIds.indexOf(mpcv.getSpec().getProductSpecId()));
			if(cart.getBuyNumber() == null){
				throw new I18nMessageException("-1","请输入正确的商品数量");
			}
			if(mpse.getStock() <= 0 || mpse.getStock() < cart.getBuyNumber()){
				throw new I18nMessageException("-1","“"+mpe.getName()+"”商品 规格”"+mpse.getSpecName()+" "+mpse.getSpecAttrName()+"”库存不足");
			}
			totalBuyNumber += cart.getBuyNumber();
			BigDecimal money = mpse.getPrice().multiply(new BigDecimal(cart.getBuyNumber()));
			logger.debug("mpse.getPrice={},money={}",mpse.getPrice(),money);
			totalMoney = totalMoney.add(money);
			
			MallOrderProductEntity mope = new MallOrderProductEntity();
			mope.setProductId(mpe.getId());
			mope.setBuyNumber(cart.getBuyNumber());
			mope.setName(mpe.getName());
			mope.setPicImg(mpe.getShowPic());
			mope.setPrice(mpse.getPrice());
			mope.setProductAmount(money);
			mope.setProductScore(0);
			mope.setProductSpecId(mpcv.getSpec().getProductSpecId());
			mope.setProductSpecName(mpcv.getSpecName()+" "+mpcv.getSepcAttrName());
			mope.setScore(mpse.getScore());
			mope.setStatus(0);//待发货
			mopes.add(mope);
		}
		
		logger.debug("totalMoney={}",totalMoney);
		logger.debug("totalBuyNumber={}",totalBuyNumber);
		order.setPayAmount(totalMoney);
		order.setOrderAmount(totalMoney);
		order.setBuyNumber(totalBuyNumber);
		this.baseMapper.save(order);
		
		for(MallOrderProductEntity mope: mopes){
			mope.setOrderId(order.getId());
			mallOrderProductDao.insert(mope);
		}
		
		mallCartDao.delete(new EntityWrapper<MallCartEntity>().eq("user_id", userId).in("product_spec_id", productSpecIds));
		return orderNo;
	}
	
	
	public static String format1(Integer value,int minLength){
		StringBuffer st = new StringBuffer(value.toString());
		if(st.length() < minLength){
			int len = minLength - st.length();
			for(int i=0;i<len;i++){
				st.insert(0, "0");
			}
		}
		return st.toString();
	}

	@Override
	public MallOrderEntity selectOrderByOrderNo(Long userId,
			String orderNo, Integer status) {
		// TODO Auto-generated method stub
		return this.baseMapper.selectOrderByOrderNo(userId,orderNo, status);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void confirmOrder(Long userId, String orderNo, Integer invoiceType,
			String invoiceTitle, String address, String consignee, String mobile) {
		// TODO Auto-generated method stub
		MallOrderEntity order = this.baseMapper.selectOrderByOrderNo(userId,orderNo, 0);
		if(order == null){
			throw new I18nMessageException("-1","此订单不存在或已支付");
		}
		order.setAddress(address);
		order.setConsignee(consignee);
		order.setMobile(mobile);
		order.setInvoiceTitle(invoiceTitle);
		order.setInvoiceType(invoiceType);
		order.setUpdateTime(new Date());
		this.baseMapper.updateById(order);
	}
	
	@Override
	public List<MallOrderEntity> mylist(Long userId, Integer status,
			Integer payType, Integer size, Integer offset) {
		// TODO Auto-generated method stub
		return this.baseMapper.mylist(userId, status, payType, size, offset);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void delete(Long userId, String orderNo) {
		// TODO Auto-generated method stub
		MallOrderEntity order = this.selectOne(new EntityWrapper<MallOrderEntity>().eq("user_id", userId).eq("order_no", orderNo));
		if(order == null){
			throw new I18nMessageException("-1","此订单不存在或已支付");
		}
		order.setOrderStatus(-1);
		this.baseMapper.updateById(order);
	}
}
