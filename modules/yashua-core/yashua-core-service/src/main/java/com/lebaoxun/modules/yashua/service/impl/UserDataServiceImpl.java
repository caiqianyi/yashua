package com.lebaoxun.modules.yashua.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.lebaoxun.modules.yashua.entity.AppData;
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
			else if(id==3){//查询上一周的起止时间
				 calendar = Calendar.getInstance();  
				 calendar.add(Calendar.DATE, -1*7);
				 calendar.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
				 startDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(calendar.getTime()));
			     calendar.add(Calendar.DAY_OF_MONTH,6);
			     endDate=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(calendar.getTime()));
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

   /**
    * 拼装App的口气显示数据
    */
	@Override
	public AppData createAppData(List<UserDataEntity> list, int id) {
		AppData appData=  new AppData();
		if(list!=null){
			if(id==0){
				appData= weekData(list);
			}
			if(id==1){
				appData= monthData(list);
			}
			if(id==2){
				appData= jiduData(list);
			}
		}
		return appData;
		
	}

	 /**
     *拼装一个季度内的数据
     */
    private AppData jiduData(List<UserDataEntity> list) {
    	Map<Double,Long> amap = new LinkedHashMap<Double,Long>();
		Map<Double,Long> pmap = new LinkedHashMap<Double,Long>();
		int[] az=new int[13],bz=new int[13];
		int acount=0,//早上的 口气和
			bcount=0,//晚上的口气和
			av1=0,//早上口气的检测数
			bv1=0;//晚上口气的检测数
		
		for(int i=1;i<=12;i++){
			amap.put((double)(i), 0l);
			pmap.put((double)(i), 0l);
		}
		for(UserDataEntity userDataEntity:list){
			Calendar calendar=Calendar.getInstance();
    		calendar.setTime(userDataEntity.getAdddate());
    		int month=(calendar.get(Calendar.MONTH)+1)%3;
			if(month==0) month=3;//本季度的第几个月
    	    int day=getDay(calendar);
    	    int ind= (month-1)*4+day;
    		double index=(double)(ind);//日期对应的下标值
    		if(ind<13){
	    		if(userDataEntity.getBiaoshi()==1){//早上的口气
	    			amap.put(index, amap.get(index)+userDataEntity.getFenshu());
	    			az[ind]=az[ind]+1;
	    			acount+=userDataEntity.getFenshu();
	    			av1++;
	    		}
	    		if(userDataEntity.getBiaoshi()==2){//晚上的口气
	    			pmap.put(index, pmap.get(index)+userDataEntity.getFenshu());
	    			bz[ind]=bz[ind]+1;
	    			bcount+=userDataEntity.getFenshu();
	    			bv1++;
	    		}
			}
		}
		for(int i=1;i<=12;i++){
			double index=(double)i;
			if(az[i]>0){
				amap.put(index, (long)(Math.ceil((float)amap.get(index)/az[i])));
			}
			if(bz[i]>0){
				pmap.put(index, (long)(Math.ceil((float)pmap.get(index)/bz[i])));
			}
		}
		
		AppData appData = new AppData();
		appData.setAlist(new ArrayList<>(amap.values()));
		appData.setPlist(new ArrayList<>(pmap.values()));
		appData.setZp((int) (av1>0 ? Math.ceil((float)acount/av1) : 0));
		appData.setWp((int) (bv1>0 ? Math.ceil((float)bcount/bv1) : 0));
		return appData;
		
    }
    /**
     * 拼装一个月内的数据
     */
	private AppData monthData(List<UserDataEntity> list) {
		Map<Double,Long> amap = new LinkedHashMap<Double,Long>();
		Map<Double,Long> pmap = new LinkedHashMap<Double,Long>();
		int[] az=new int[11],bz=new int[11];
		int acount=0,//早上的 口气和
			bcount=0,//晚上的口气和
			av1=0,//早上口气的检测数
			bv1=0;//晚上口气的检测数
		
		for(int i=1;i<=10;i++){
			amap.put((double)(i), 0l);
			pmap.put((double)(i), 0l);
		}
		for(UserDataEntity userDataEntity:list){
			Calendar calendar=Calendar.getInstance();
    		calendar.setTime(userDataEntity.getAdddate());
    		int day=calendar.get(Calendar.DAY_OF_MONTH);
    		double index= Math.ceil((float)day/3);
    		int ind=(int)index;
    		if(userDataEntity.getBiaoshi()==1){//早上的口气
    			amap.put(index, amap.get(index)+userDataEntity.getFenshu());
    			az[ind]=az[ind]+1;
    			acount+=userDataEntity.getFenshu();
    			av1++;
    		}
    		if(userDataEntity.getBiaoshi()==2){//晚上的口气
    			pmap.put(index, pmap.get(index)+userDataEntity.getFenshu());
    			bz[ind]=bz[ind]+1;
    			bcount+=userDataEntity.getFenshu();
    			bv1++;
    		}
		}
		for(int i=1;i<11;i++){
			double index=(double)i;
			if(az[i]>0){
				amap.put(index, (long)(Math.ceil((float)amap.get(index)/az[i])));
			}
			if(bz[i]>0){
				pmap.put(index, (long)(Math.ceil((float)pmap.get(index)/bz[i])));
			}
		}
		
		AppData appData = new AppData();
		appData.setAlist(new ArrayList<>(amap.values()));
		appData.setPlist(new ArrayList<>(pmap.values()));
		appData.setZp((int) (av1>0 ? Math.ceil((float)acount/av1) : 0));
		appData.setWp((int) (bv1>0 ? Math.ceil((float)bcount/bv1) : 0));
		return appData;
	}
	/**
	 * 拼装一个星期内的数据
	 */
	private AppData weekData(List<UserDataEntity> list) {
		long[] along=new long[7],blong=new long[7];
    	int acount=0,bcount=0,az=0,bz=0;
    	for(UserDataEntity userDataEntity:list){
    		Calendar calendar=Calendar.getInstance();
    		calendar.setTime(userDataEntity.getAdddate());
    		int day=calendar.get(Calendar.DAY_OF_WEEK);
    		if(day==1){//当期是星期天  记在一周的最后一天
    			if(userDataEntity.getBiaoshi()==1){//早上的口气记录
    				along[6]=userDataEntity.getFenshu();
    				acount+=along[6];
    				az++;
    			}
    			if(userDataEntity.getBiaoshi()==2){//晚上的口气记录
    				blong[6]=userDataEntity.getFenshu();
    				bcount+=blong[6];
    				bz++;
    			}
    		}
    		if(day>=2){//当期是星期天  记在一周的最后一天
    			if(userDataEntity.getBiaoshi()==1){//早上的口气记录
    				along[day-2]=userDataEntity.getFenshu();
    				acount+=along[day-2];
    				az++;
    			}
    			if(userDataEntity.getBiaoshi()==2){//晚上的口气记录
    				blong[day-2]=userDataEntity.getFenshu();
    				bcount+=blong[day-2];
    				bz++;
    			}
    		}
    	}
    	
    	az = (int) (az>0 ? Math.ceil((float)acount/az) : 0);
    	bz = (int) (bz>0 ? Math.ceil((float)bcount/bz) : 0);
    	AppData appData = new AppData();
    	appData.setAlist(changeToList(along));
    	appData.setPlist(changeToList(blong));
    	appData.setZp(az);
    	appData.setWp(bz);
    	return appData;
	}
	
	/**
	 * 判断当前星期一个月内第几周
	 */
	 public static int getDay(Calendar calendar) {
	        int day = calendar.get(Calendar.DAY_OF_MONTH);
	        if (day < 7) {
	            int ifsunday = calendar.get(Calendar.DAY_OF_WEEK);
	            if (ifsunday == 1)
	                ifsunday = 7;
	            if (ifsunday > day)
	                return 0;//当前月的前几天不是周一，返回0，不计入当前月的口气统计
	        }
	        System.out.println(day);
	        int weekday = (int) Math.ceil((float) day / 7);
	        return  weekday;
	    }
	
	public List<Long> changeToList(long[] arry){
		List<Long> list = new ArrayList<Long>();
		if(arry!=null &&  arry.length>0){
			for(int i=0;i<arry.length;i++)
				list.add(arry[i]);
		}
		return list;
	}
	
}
