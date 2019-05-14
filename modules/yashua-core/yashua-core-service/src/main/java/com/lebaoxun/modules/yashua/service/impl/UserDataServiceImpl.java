package com.lebaoxun.modules.yashua.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.lebaoxun.modules.yashua.dao.UserDataDao;
import com.lebaoxun.modules.yashua.entity.UserDataEntity;
import com.lebaoxun.modules.yashua.service.UserDataService;

@Service("userDataService")
public class UserDataServiceImpl extends ServiceImpl<UserDataDao,UserDataEntity> implements UserDataService{ 
	@Resource
	private UserDataDao userDataDao;
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Long user_id = Long.parseLong((String) params.get("user_id"));
		 //获取近期三天的口气数据，供前端显示
        Date nowDate = new Date();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(nowDate);
        calendar.add(Calendar.DAY_OF_YEAR,-2);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date beforeData =  calendar.getTime();
        Page<UserDataEntity> page = this.selectPage(
                new Query<UserDataEntity>(params).getPage(),
                new EntityWrapper<UserDataEntity>().eq("userid", user_id).orderBy("adddate", true).le("adddate",nowDate).gt("adddate",beforeData));
       return new PageUtils(page);
     }

	

	/**
	 * 获取历史口气数据
	 * @throws ParseException 
	 */
	@Override
	public PageUtils queryByConditgions(Map<String, Object> params){
		Long user_id = Long.parseLong((String) params.get("user_id"));
		Integer id = Integer.parseInt((String) params.get("id"));
		Calendar calendar = null;
		Date startDate=null;
		Date endDate=null;
		try {
			if(id==0){//查询当前一周的起止时间
				 calendar = Calendar.getInstance();  
				 calendar.setFirstDayOfWeek(Calendar.MONDAY);  
				 calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);  
				 startDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(calendar.getTime()));
				 calendar = Calendar.getInstance();
				 calendar.setFirstDayOfWeek(Calendar.MONDAY);  
		         calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);  
				 endDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(calendar.getTime()));
			} 
			else if(id==1){//查询一个月的起止时间
				calendar = Calendar.getInstance();
				calendar.set(Calendar.DATE, 1);
				startDate =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(calendar.getTime()));
				calendar = Calendar.getInstance();
				calendar.set(Calendar.DATE, 1); 
				calendar.add(Calendar.MONTH, 1); 
				calendar.add(Calendar.DATE, -1); 
				endDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(calendar.getTime()));
			} 
			else if(id==2){//查询一个季度的起止时间
				calendar=Calendar.getInstance();
				int currentMonth=calendar.get(Calendar.MONTH)+1;
				if (currentMonth >= 1 && currentMonth <= 3) 
					calendar.set(Calendar.MONTH, 0); 
				else if (currentMonth >= 4 && currentMonth <= 6) 
					calendar.set(Calendar.MONTH, 3); 
				else if (currentMonth >= 7 && currentMonth <= 9) 
					calendar.set(Calendar.MONTH, 6); 
				else if (currentMonth >= 10 && currentMonth <= 12) 
					calendar.set(Calendar.MONTH, 9); 
				calendar.set(Calendar.DATE, 1); 
				startDate =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(calendar.getTime()));
				calendar  = Calendar.getInstance(); 
			    currentMonth = calendar.get(Calendar.MONTH) + 1; 
			    if (currentMonth >= 1 && currentMonth <= 3) { 
			    	calendar.set(Calendar.MONTH, 2); 
			    	calendar.set(Calendar.DATE, 31); 
			    } 
			    else if (currentMonth >= 4 && currentMonth <= 6) { 
			        calendar.set(Calendar.MONTH, 5); 
			        calendar.set(Calendar.DATE, 30); 
			    }
			    else if (currentMonth >= 7 && currentMonth <= 9) { 
			        calendar.set(Calendar.MONTH,8); 
			        calendar.set(Calendar.DATE, 30); 
			    } 
			    else if (currentMonth >= 10 && currentMonth <= 12) { 
			    	calendar.set(Calendar.MONTH, 11); 
			    	calendar.set(Calendar.DATE, 31); 
			    } 
			        endDate  =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(calendar.getTime()));
		}
			}catch (Exception e) { 
            e.printStackTrace(); 
        } 
		 Page<UserDataEntity> page = this.selectPage(
	                new Query<UserDataEntity>(params).getPage(),
	                new EntityWrapper<UserDataEntity>().eq("userid", user_id).orderBy("adddate", true).le("adddate",endDate).gt("adddate",startDate));
	       return new PageUtils(page);
	}

   
	/**
	 * 保存口气数据
	 */

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void save(Long kouqi,Long user_id) {
		if(kouqi==null || kouqi<=0 || kouqi>=10)
			throw new I18nMessageException("-1","口气数据无效");
		try {
			UserDataEntity userDataEntity = new UserDataEntity();
			userDataEntity.setAdddate(new Date());
			userDataEntity.setFenshu(kouqi);
			userDataEntity.setUserid(user_id);
		
			Calendar calendar=Calendar.getInstance();
			Date date = calendar.getTime();
			Date newDate = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(date));
			calendar.set(Calendar.HOUR_OF_DAY,0);
	        calendar.set(Calendar.MINUTE,0);
	        calendar.set(Calendar.SECOND,0);
	        Date date0 = calendar.getTime();
	        calendar.add(Calendar.HOUR_OF_DAY,12);
	        Date date12 = calendar.getTime();
	        calendar.add(Calendar.HOUR_OF_DAY,12);
	        Date date24 = calendar.getTime();
			
		    if(date.compareTo(date0)>=0 && date.compareTo(date12)<0){
				List<UserDataEntity> list6 = userDataDao.lookList(newDate, user_id,1L);
				if(list6.size()<=0){
					userDataEntity.setBiaoshi(1L);
					userDataDao.insert(userDataEntity);
				}
			}
		    if(date.compareTo(date12)>=0 && date.compareTo(date24)<0){
				List<UserDataEntity> list10 = userDataDao.lookList(newDate, user_id,2L);
				userDataEntity.setBiaoshi(2L);
				if(list10.size()<=0){
					userDataDao.insert(userDataEntity);
				}
				else{
					userDataEntity.setId(list10.get(0).getId());
					userDataDao.updateById(userDataEntity);
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
