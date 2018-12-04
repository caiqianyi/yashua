package com.lebaoxun.modules.yashua.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;
import com.lebaoxun.modules.yashua.dao.UserDataDao;
import com.lebaoxun.modules.yashua.entity.UserDataEntity;
import com.lebaoxun.modules.yashua.service.UserDataService;

@Service("userDataService")
public class UserDataServiceImpl extends ServiceImpl<UserDataDao,UserDataEntity> implements UserDataService{

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Long user_id = Long.parseLong((String) params.get("user_id"));
		
        Page<UserDataEntity> page = this.selectPage(
                new Query<UserDataEntity>(params).getPage(),
                new EntityWrapper<UserDataEntity>().eq("userid", user_id));
        //获取近期三天的口气数据，供前端显示
        Date nowDate = new Date();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(nowDate);
        calendar.add(Calendar.DAY_OF_YEAR,-3);
        Date beforeData =  calendar.getTime();
        List<UserDataEntity> list = page.getRecords();
        List<UserDataEntity> list1 = new ArrayList<UserDataEntity>();
        if(list!=null ){
        	for(UserDataEntity userDataEntity :list){
        		if(userDataEntity.getAdddate().getTime()<=nowDate.getTime() && userDataEntity.getAdddate().getTime()>=beforeData.getTime())
        			list1.add(userDataEntity);
        	}
        }
        page.setRecords(list1);
        return new PageUtils(page);
      
	}

}
