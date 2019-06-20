package com.lebaoxun.modules.account.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;
import com.lebaoxun.commons.utils.StringUtils;
import com.lebaoxun.modules.account.dao.UserLogDao;
import com.lebaoxun.modules.account.entity.UserLogEntity;
import com.lebaoxun.modules.account.service.UserLogService;
import com.lebaoxun.soa.core.redis.IRedisSorted;


@Service("userLogService")
public class UserLogServiceImpl extends ServiceImpl<UserLogDao, UserLogEntity> implements UserLogService {
	
	@Resource
	private IRedisSorted redisSorted;
	@Autowired
	private UserLogDao userLogDao;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	String userId = (String)params.get("userId");
    	String account = (String)params.get("account");
    	String type = (String)params.get("type");
        Page<UserLogEntity> page = this.selectPage(
                new Query<UserLogEntity>(params).getPage(),
                new EntityWrapper<UserLogEntity>()
                .eq(StringUtils.isNotBlank(userId) && StringUtils.isNumeric(userId), "user_id", userId)
                .eq(StringUtils.isNotBlank(account), "account", account)
                .eq(StringUtils.isNotBlank(type), "log_type", type)
        );

        return new PageUtils(page);
    }

    @Override
    public List<Map<String, Object>> queryAllLogType() {
    	// TODO Auto-generated method stub
    	return this.baseMapper.queryAllLogType();
    }

	@Override
	public List<UserLogEntity> queryAccountLogByUserId(Long userId,
			Integer size, Integer offset) {
		// TODO Auto-generated method stub
		return this.baseMapper.queryAccountLogByUserId(userId, size, offset);
	}

	@Override
	public void zRange(Long userId, String logType, String time) {
		// TODO Auto-generated method stub
		String key = "account:tradeMoney:"+logType+":ranks"+":"+time;
		Long total = this.baseMapper.sumTradeMoneyByUser(userId, time, logType);
		redisSorted.zAdd(key, total, userId);
	}

	@Override
	public Map<String, Object> zRank(Long userId, String logType, String time) {
		// TODO Auto-generated method stub
		String key = "account:tradeMoney:"+logType+":ranks"+":"+time;
		if(!redisSorted.exists(key)){
			return null;
		}
		Long rank = redisSorted.zRank(key, userId),
				size = redisSorted.zSize(key),
					score = redisSorted.zScore(key,userId).longValue();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("score", score);
		result.put("rank", rank);
		result.put("size", size);
		return result;
	}

	@Override
	public Map<String, Long> zRankWeek(Long userId, String logType) {
		 long countById=0,countDayu=0,count=0;
		 try {
			
			 Calendar calendar = Calendar.getInstance();  
			 calendar.add(Calendar.DATE, -1*7);
			 calendar.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
			 Date  startDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(calendar.getTime()));
			 calendar.add(Calendar.DAY_OF_MONTH,6);
		     Date endDate=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(calendar.getTime()));
		     countById  = userLogDao.countByUserId(userId,logType,startDate,endDate);//我上周的签到次数
		     countDayu=userLogDao.countDayu(logType, startDate, endDate,countById);//上周牵到比我少的人
		     count= userLogDao.count(logType, startDate, endDate);//总的人数
		 } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Map<String,Long> map = new HashMap<String,Long>();
	    map.put("countById", countById);
	    map.put("countDayu", countDayu);
	    map.put("count", count);
	    System.out.println(map.get("countById")+"dwsdwdewqedqweq "+map.get("count"));
		return map;
	}
}
