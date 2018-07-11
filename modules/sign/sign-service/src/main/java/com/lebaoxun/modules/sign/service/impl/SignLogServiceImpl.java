package com.lebaoxun.modules.sign.service.impl;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.utils.FormulaCalculate;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;
import com.lebaoxun.modules.sign.dao.SignAwardDao;
import com.lebaoxun.modules.sign.dao.SignLogDao;
import com.lebaoxun.modules.sign.dao.SignUinfoDao;
import com.lebaoxun.modules.sign.entity.SignAwardEntity;
import com.lebaoxun.modules.sign.entity.SignLogEntity;
import com.lebaoxun.modules.sign.entity.SignUinfoEntity;
import com.lebaoxun.modules.sign.service.SignLogService;


@Service("signLogService")
public class SignLogServiceImpl extends ServiceImpl<SignLogDao, SignLogEntity> implements SignLogService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private SignUinfoDao signUinfoDao;
	@Resource
	private SignAwardDao signAwardDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<SignLogEntity> page = this.selectPage(
                new Query<SignLogEntity>(params).getPage(),
                new EntityWrapper<SignLogEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public SignUinfoEntity signIn(Long userId,String groupId) {
		// TODO Auto-generated method stub
		Date signTime = new Date();
		int signTodaycount = this.baseMapper.countSignByUserId(userId, signTime);
		if(signTodaycount > 0){
			throw new I18nMessageException("-1","您今日已签到过了");
		}
		SignLogEntity sle = new SignLogEntity();
		sle.setCreateTime(signTime);
		sle.setUserId(userId);
		this.baseMapper.insert(sle);
		
		SignUinfoEntity sue = signUinfoDao.findSignUinfoByUserId(userId);
		if(sue == null){
			sue = new SignUinfoEntity();
			sue.setKeepUpCount(1);
			sue.setMKeepUpCount(1);
			sue.setSignTime(signTime);
			sue.setTotalResignNum(0);
			sue.setTotalSignNum(1);
			sue.setMaxKeepUpCount(1);
			sue.setmMaxKeepUpCount(1);
			sue.setUserId(userId);
			signUinfoDao.insert(sue);
		}else{
			Date prevSignTime = sue.getSignTime();
			String prevSignTimeStr = DateFormatUtils.format(prevSignTime, "yyyyMMdd"),
					signTimeStr = DateFormatUtils.format(DateUtils.addDays(signTime, -1), "yyyyMMdd");
			boolean isKeepup = signTimeStr.equals(prevSignTimeStr);
			Integer keepUpCount = 0, 
					mKeepUpCount = 0,//
					resignNum = 0,//距上次漏签天数
					maxKeepUpCount = sue.getMaxKeepUpCount(),//历史最大连签数
					mMaxKeepUpCount = 0//当月最大连签数
					;
			if(isKeepup){//是否连续签到
				keepUpCount = sue.getKeepUpCount()+1;
				
				if(keepUpCount > maxKeepUpCount){
					maxKeepUpCount = keepUpCount;
				}
				
				if(DateFormatUtils.format(prevSignTime, "yyyyMM").equals(DateFormatUtils.format(signTime, "yyyyMM"))){
					mKeepUpCount = sue.getMKeepUpCount()+1;
					if(mKeepUpCount>mMaxKeepUpCount){
						mMaxKeepUpCount = mKeepUpCount;
					}
				}
			}else{
				Calendar aCalendar = Calendar.getInstance();
		        aCalendar.setTime(signTime);
		        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
		        aCalendar.setTime(prevSignTime);
		        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
				resignNum = day1-day2-1;
			}
			sue.setSignTime(signTime);
			sue.setTotalResignNum(sue.getTotalResignNum()+resignNum);
			sue.setTotalSignNum(sue.getTotalSignNum() + 1);
			sue.setKeepUpCount(keepUpCount);
			sue.setMKeepUpCount(mKeepUpCount);
			sue.setMaxKeepUpCount(maxKeepUpCount);
			sue.setmMaxKeepUpCount(mMaxKeepUpCount);
			signUinfoDao.updateById(sue);
		}
		
		List<SignAwardEntity> awards = signAwardDao.selectList(new EntityWrapper<SignAwardEntity>().eq("flag", 0).eq("group_id", groupId));
		if(awards != null){
			for(SignAwardEntity sae: awards){
				String lots[] = new String[]{
						sue.getKeepUpCount()+"",sue.getMKeepUpCount()+"",
						sue.getTotalResignNum()+"",sue.getTotalSignNum()+"",
						sue.getMKeepUpCount()+"",sue.getmMaxKeepUpCount()+""};
				String line = sae.getCondition().replaceAll("#keep_up_count", "#N1")
						.replaceAll("#m_keep_up_count", "#N2")
						.replaceAll("#total_resign_num", "#N3")
						.replaceAll("#total_sign_num", "#N4")
						.replaceAll("#m_keep_up_count", "#N5")
						.replaceAll("#m_max_keep_up_count", "#N6");
				boolean isAward = false;
				try{
					logger.debug("lots={},line={}",Arrays.asList(lots),line);
					isAward = FormulaCalculate.check(lots, line);
					logger.debug("isAward={}",isAward);
				}catch(Exception e){
					e.printStackTrace();
				}
				if(isAward){
					signAwardDao.executeSignAward(sae.getAwardSql(), userId);
				}
			}
		}
		return sue;
	}
	
	@Override
	public SignUinfoEntity findMonthSignLog(Long userId, String time) {
		// TODO Auto-generated method stub
		return signUinfoDao.queryMonthSignLog(userId, time);
	}

}
