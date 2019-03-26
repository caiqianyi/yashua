package com.lebaoxun.modules.mall.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.swing.plaf.synth.SynthSpinnerUI;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;
import com.lebaoxun.commons.utils.StringUtils;
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
import com.lebaoxun.modules.pay.service.IWxPayService;

@Service("mallOrderService")
public class MallOrderServiceImpl extends
		ServiceImpl<MallOrderDao, MallOrderEntity> implements MallOrderService {

	private static final String[] FARAWAY_ADDRESS={"内蒙古","宁夏","青海","海南","甘肃","云南","贵州","广西"};
	private static final String[] LONG_FARAWAY_ADDRESS={"新疆","西藏"};
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
	private RedisTemplate<String, Object> redisTemplate;
	
	@Resource
	private IWxPayService wxPayService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String status = (String) params.get("status"), orderNo = (String) params
				.get("orderNo"), userId = (String) params.get("userId");
		Page<MallOrderEntity> page = this.selectPage(
				new Query<MallOrderEntity>(params).getPage(),
				new EntityWrapper<MallOrderEntity>()
						.notIn(StringUtils.isBlank(status), "order_status", -1)
						.eq(StringUtils.isNotBlank(status)
								&& StringUtils.isNumeric(status),
								"order_status", status)
						.eq(StringUtils.isNotBlank(userId)
								&& StringUtils.isNumeric(userId), "user_id",
								userId)
						.eq(StringUtils.isNotBlank(orderNo), "order_no",
								orderNo));
		return new PageUtils(page);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String create(Long userId, Integer maxOrderNum,
			List<MallCartEntity> products) {
		List<Long> productSpecIds = new ArrayList<Long>();
		for (MallCartEntity mope : products) {
			if (mope.getProductSpecId() != null) {
				productSpecIds.add(mope.getProductSpecId());
			}
		}
		Date now = new Date();
		List<MallProductCartVo> mpcvs = mallCartDao
				.queryByProductSpecId(productSpecIds.toArray(new Long[] {}));
		if (mpcvs.size() != products.size()) {
			throw new I18nMessageException("-1", "商品不存在或已下架 ");
		}
		int count = this.baseMapper
				.selectCount(new EntityWrapper<MallOrderEntity>().eq("user_id",
						userId).eq("order_status", 0));
		if (count >= maxOrderNum) {
			throw new I18nMessageException("20110", "订单创建失败，您还有" + count
					+ "个未支付订单，立即去处理!");
		}
		String date = DateFormatUtils.format(now, "yyyyMMdd");
		String key = "mall:orderNo:" + date;
		Integer inr = (Integer) redisTemplate.opsForValue().get(key);
		if (inr == null) {
			inr = RandomUtils.nextInt(10000);
			redisTemplate.opsForValue().set(key, inr, 25l,
					TimeUnit.valueOf("HOURS"));
		} else {
			inr = redisTemplate.opsForValue().increment(key, 1).intValue();
		}
		String orderNo = "lv" + date + format1(inr, 5);
		logger.debug("orderNo={}", orderNo);

		MallOrderEntity order = new MallOrderEntity();
		order.setCreateTime(now);
		order.setUserId(userId);
		order.setOrderStatus(0);
		order.setPayType(0);// 在线支付
		
		order.setOrderNo(orderNo);
		Integer totalBuyNumber = 0, orderScore = 0;
		BigDecimal totalMoney = new BigDecimal(0);
		List<MallOrderProductEntity> mopes = new ArrayList<MallOrderProductEntity>();
		for (MallProductCartVo mpcv : mpcvs) {
			MallProductSpecificationEntity mpse = mpcv.getSpec();
			MallProductEntity mpe = mpcv.getProduct();
			if (mpse == null || mpse.getStatus() != 1) {
				throw new I18nMessageException("-1", "“" + mpe.getName()
						+ "”商品不存在或已下架");
			}
			MallCartEntity cart = products.get(productSpecIds.indexOf(mpcv
					.getSpec().getProductSpecId()));
			if (cart.getBuyNumber() == null) {
				throw new I18nMessageException("-1", "请输入正确的商品数量");
			}
			if (mpse.getStock() <= 0 || mpse.getStock() < cart.getBuyNumber()) {
				throw new I18nMessageException("-1", "“" + mpe.getName()
						+ "”商品 规格”" + mpse.getSpecName() + " "
						+ mpse.getSpecAttrName() + "”库存不足");
			}
			totalBuyNumber += cart.getBuyNumber();
			BigDecimal mprice = mpse.getPrice();
			if(cart.getBuyNumber()>=2)
				mprice=mprice.multiply(new BigDecimal(0.95));
			else if(cart.getBuyNumber()>=4)
				mprice=mprice.multiply(new BigDecimal(0.9));
			BigDecimal money = mprice.multiply(
					new BigDecimal(cart.getBuyNumber()));
			logger.debug("mpse.getPrice={},money={}", mpse.getPrice(), money);
			totalMoney = totalMoney.add(money);
			orderScore += mpse.getScore() * cart.getBuyNumber();

			MallOrderProductEntity mope = new MallOrderProductEntity();
			mope.setProductId(mpe.getId());
			mope.setBuyNumber(cart.getBuyNumber());
			mope.setName(mpe.getName());
			mope.setPicImg(mpe.getShowPic());
			mope.setPrice(mpse.getPrice());
			mope.setProductAmount(money);
			mope.setProductScore(0);
			mope.setProductSpecId(mpcv.getSpec().getProductSpecId());
			mope.setProductSpecName(mpcv.getSpecName() + " "
					+ mpcv.getSepcAttrName());
			mope.setScore(mpse.getScore());
			mope.setStatus(0);// 待发货
			mopes.add(mope);
		}

		logger.debug("totalMoney={}", totalMoney);
		logger.debug("totalBuyNumber={}", totalBuyNumber);
		order.setShipmentAmount(new BigDecimal(0));// 快递费
		order.setPayAmount(totalMoney.add(order.getShipmentAmount()));
		order.setOrderAmount(totalMoney);
		order.setBuyNumber(totalBuyNumber);
		order.setOrderScore(orderScore);
		this.baseMapper.save(order);

		for (MallOrderProductEntity mope : mopes) {
			mope.setOrderId(order.getId());
			mallOrderProductDao.insert(mope);
		}

		mallCartDao.delete(new EntityWrapper<MallCartEntity>().eq("user_id",
				userId).in("product_spec_id", productSpecIds));
		return orderNo;
	}

	public static String format1(Integer value, int minLength) {
		StringBuffer st = new StringBuffer(value.toString());
		if (st.length() < minLength) {
			int len = minLength - st.length();
			for (int i = 0; i < len; i++) {
				st.insert(0, "0");
			}
		}
		return st.toString();
	}

	@Override
	public MallOrderEntity selectOrderByOrderNo(Long userId, String orderNo,String address,
			Integer status) {
		MallOrderEntity mallOrderEntity = this.baseMapper.selectOrderByOrderNo(userId, orderNo, status);
		BigDecimal orderAmount = mallOrderEntity.getOrderAmount();
		
		
			if(checkAddress(address)==1){
				if(orderAmount.compareTo(new BigDecimal(199))>-1){
					mallOrderEntity.setShipmentAmount(new BigDecimal(0));
				}
				else
					mallOrderEntity.setShipmentAmount(new BigDecimal(20));
			}
	
		
			else if(checkAddress(address)==2){
				if(orderAmount.compareTo(new BigDecimal(299))>-1){
					mallOrderEntity.setShipmentAmount(new BigDecimal(0));
				}
				else
					mallOrderEntity.setShipmentAmount(new BigDecimal(25));	
			}
			else if(checkAddress(address)==0){
				if(orderAmount.compareTo(new BigDecimal(99))>-1){
					mallOrderEntity.setShipmentAmount(new BigDecimal(0));
				}
				else
					mallOrderEntity.setShipmentAmount(new BigDecimal(10));
			}
			mallOrderEntity.setPayAmount(mallOrderEntity.getOrderAmount().add(mallOrderEntity.getShipmentAmount()));
			this.baseMapper.updateById(mallOrderEntity);
			return mallOrderEntity;
	}
	
	public int checkAddress(String address){
		int flag=0;
		for(int i=0;i<FARAWAY_ADDRESS.length;i++){
			if(address.startsWith(FARAWAY_ADDRESS[i])){
				flag=1;break;
			}
		}
		for(int i=0;i<LONG_FARAWAY_ADDRESS.length;i++){
			if(address.startsWith(LONG_FARAWAY_ADDRESS[i])){
				flag=2;break;
			}
		}
		return flag;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public ResponseMessage confirmOrder(Long userId, String orderNo, Integer invoiceType,
			String invoiceTitle, String invoiceNo, String address, String consignee, String mobile,
			String wxopenid,String spbill_create_ip, Long fuid) {
		// TODO Auto-generated method stub
		MallOrderEntity order = this.baseMapper.selectOrderByOrderNo(userId,
				orderNo, 0);
		if (order == null) {
			throw new I18nMessageException("-1", "此订单不存在或已支付");
		}
		order.setAddress(address);
		order.setConsignee(consignee);
		order.setMobile(mobile);
		order.setInvoiceTitle(invoiceTitle);
		order.setInvoiceNo(invoiceNo);
		order.setInvoiceType(invoiceType);
		order.setUpdateTime(new Date());
		order.setPayType(1);// 现金支付
		order.setFuid(fuid);
		this.baseMapper.updateById(order);
		Integer totalFee = order.getPayAmount().setScale(2, BigDecimal.ROUND_DOWN).multiply(new BigDecimal("100")).intValue();
		return wxPayService.payment(spbill_create_ip, orderNo, "购买魔牙产品", totalFee, "", "yashua", wxopenid, userId, new BigDecimal("0"), "shopping");
		//return wxPayService.payment(spbill_create_ip, orderNo, "购买魔牙产品", totalFee, "", "yashua", wxopenid, userId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void scoreExchange(Long userId, String orderNo, Integer invoiceType,
			String invoiceTitle, String invoiceNo, String address, String consignee, 
			String mobile) {
		// TODO Auto-generated method stub
		MallOrderEntity order = this.baseMapper.selectOrderByOrderNo(userId,
				orderNo, 0);
		if (order == null) {
			throw new I18nMessageException("-1", "此订单不存在或已支付");
		}

		if (order.getOrderScore() <= 0) {
			throw new I18nMessageException("-1", "此商品无法进行兑换");
		}

		order.setAddress(address);
		order.setConsignee(consignee);
		order.setMobile(mobile);
		order.setInvoiceTitle(invoiceTitle);
		order.setInvoiceNo(invoiceNo);
		order.setInvoiceType(invoiceType);
		order.setUpdateTime(new Date());
		order.setPayType(0);// 积分支付
		order.setOrderStatus(1);// 支付成功

		List<MallOrderProductEntity> list = this.baseMapper
				.selectOrderProductByOrderId(order.getId());
		for (MallOrderProductEntity mope : list) {
			mope.setStatus(1);
			mallOrderProductDao.updateById(mope);
		}
		logger.info("userId={},order.getOrderScore={},orderNo={}",userId,order.getOrderScore(),orderNo);
		this.baseMapper.balancePay(userId, order.getOrderScore(),
				"MALL_EXCHANGE", "商品兑换", orderNo);
		this.baseMapper.updateById(order);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public MallOrderEntity payMallOrder(String orderNo, String buyTime) {
		MallOrderEntity order = this.baseMapper.selectOrderByOrderNo(null, orderNo, null);
		if (order == null) {
			throw new I18nMessageException("-1", "此订单不存在或已支付");
		}

		if (order.getOrderScore() <= 0) {
			throw new I18nMessageException("-1", "此商品无法进行兑换");
		}
		order.setUpdateTime(new Date(Long.parseLong(buyTime)));
		order.setPayType(1);//
		order.setOrderStatus(1);// 支付成功
		this.baseMapper.updateById(order);
		return order;
	}

	@Override
	public List<MallOrderEntity> mylist(Long userId, Integer status,
			Integer size, Integer offset) {
		// TODO Auto-generated method stub
		return this.baseMapper.mylist(userId, status, size, offset);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void delete(Long userId, String orderNo) {
		// TODO Auto-generated method stub
		MallOrderEntity order = this
				.selectOne(new EntityWrapper<MallOrderEntity>().eq("user_id",
						userId).eq("order_no", orderNo));
		if (order == null) {
			throw new I18nMessageException("-1", "此订单不存在或已支付");
		}
		order.setOrderStatus(-1);
		this.baseMapper.updateById(order);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean deleteBatchIds(Collection<? extends Serializable> idList) {
		// TODO Auto-generated method stub
		boolean result = true;
		for (Serializable id : idList) {
			this.deleteById(id);
			mallOrderProductDao
					.delete(new EntityWrapper<MallOrderProductEntity>().eq(
							"order_id", id));
		}
		return result;
	}

	@Override
	public MallOrderProductEntity selectOrderProductByOrderProductId(
			Long userId, Long orderProductId) {
		// TODO Auto-generated method stub
		return this.baseMapper.selectOrderProductByOrderProductId(userId,
				orderProductId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void sendOut(Long orderId, String postid) {
		// TODO Auto-generated method stub
		MallOrderEntity order = this
				.selectOne(new EntityWrapper<MallOrderEntity>().eq("id",
						orderId).eq("order_status", 1));
		if (order == null) {
			throw new I18nMessageException("-1", "此订单不存在或已发货");
		}
		order.setPostid(postid);
		order.setOrderStatus(2);
		this.baseMapper.updateById(order);

		List<MallOrderProductEntity> list = this.baseMapper
				.selectOrderProductByOrderId(order.getId());
		for (MallOrderProductEntity mope : list) {
			mope.setStatus(2);
			mallOrderProductDao.updateById(mope);
		}
	}

	private static RestTemplate restTemplate = new RestTemplate();

	@Override
	public Map<String, Object> kuaid100Query(String postid) {
		// TODO Auto-generated method stub
		return kuaid100QueryBase(postid);
	}

	public static Map<String, Object> kuaid100QueryBase(String postid) {
		// TODO Auto-generated method stub
		String autoComNumUri = "http://www.kuaidi100.com/autonumber/autoComNum?resultv2=1&text="
				+ postid;
		String autoComNumJson = restTemplate.getForObject(autoComNumUri,
				String.class, new Object[] {});
		JSONObject result1 = (JSONObject) JSONObject.parse(autoComNumJson);
		JSONArray autoResult = result1.getJSONArray("auto");
		if (autoResult != null && autoResult.size() > 0) {
			String comCode = autoResult.getJSONObject(0).getString("comCode");
			String queryUri = "http://www.kuaidi100.com/query?type=" + comCode
					+ "&postid=" + postid + "&temp="
					+ System.currentTimeMillis();
			String postResult = restTemplate.getForObject(queryUri,
					String.class, new Object[] {});
			JSONObject postJson = (JSONObject) JSONObject.parse(postResult);
			if ("200".equals(postJson.getString("status"))) {
				Map<String, Object> result = new LinkedHashMap<String, Object>();
				result.put("com", comCode);
				result.put("nu", postid);
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				JSONArray datas = postJson.getJSONArray("data");
				for (int i = 0; i < datas.size(); i++) {
					JSONObject item = datas.getJSONObject(i);
					Map<String, Object> m = new LinkedHashMap<String, Object>();
					try {
						m.put("time",
								DateUtils.parseDate(item.getString("time"),
										new String[] { "yyyy-MM-dd HH:mm:ss" })
										.getTime());
						m.put("ftime",
								DateUtils.parseDate(item.getString("ftime"),
										new String[] { "yyyy-MM-dd HH:mm:ss" })
										.getTime());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					m.put("context", item.getString("context"));
					list.add(m);
				}
				result.put("list", list);
				return result;
			}
			throw new I18nMessageException("-1", postJson.getString("message"));
		}
		throw new I18nMessageException("-1", "运单号查询失败");
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void confirmReceive(Long userId, String orderNo) {
		// TODO Auto-generated method stub
		MallOrderEntity order = this.baseMapper.selectOrderByOrderNo(userId, orderNo, 2);
		if (order == null) {
			throw new I18nMessageException("-1", "此订单不存在或确认");
		}
		order.setOrderStatus(3);
		this.baseMapper.updateById(order);
		List<MallOrderProductEntity> list = this.baseMapper
				.selectOrderProductByOrderId(order.getId());
		for (MallOrderProductEntity mope : list) {
			mope.setStatus(3);
			mallOrderProductDao.updateById(mope);
		}
	}
}
