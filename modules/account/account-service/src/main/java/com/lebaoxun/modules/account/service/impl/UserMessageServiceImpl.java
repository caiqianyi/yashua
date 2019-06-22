package com.lebaoxun.modules.account.service.impl;

import com.lebaoxun.commons.utils.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;
import com.lebaoxun.modules.account.dao.UserDao;
import com.lebaoxun.modules.account.dao.UserMessageDao;
import com.lebaoxun.modules.account.dao.UserMessageMidDao;
import com.lebaoxun.modules.account.entity.UserEntity;
import com.lebaoxun.modules.account.entity.UserMessageEntity;
import com.lebaoxun.modules.account.entity.UserMessageMidEntity;
import com.lebaoxun.modules.account.service.UserMessageService;


@Service("userMessageService")
public class UserMessageServiceImpl extends ServiceImpl<UserMessageDao, UserMessageEntity> implements UserMessageService {

	
	@Resource
	private UserDao userDao;
	@Resource
	private UserMessageMidDao userMessageMidDao;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	String userId = (String)params.get("user_id");
    	String type = (String)params.get("type");
        Page<UserMessageEntity> page = this.selectPage(
                new Query<UserMessageEntity>(params).getPage(),
                new EntityWrapper<UserMessageEntity>()
                .eq(StringUtils.isNotBlank(userId) && StringUtils.isNumeric(userId), "user_id", userId)
                .eq(StringUtils.isNotBlank(type), "type", type)
        );

        return new PageUtils(page);
    }

	@Override
	public List<UserMessageEntity> findInformByUserId(Long userId,
			Integer size, Integer offset) {
		// TODO Auto-generated method stub
		return this.baseMapper.findInformByUserId(userId, size, offset);
	}

	@Override
	public UserMessageEntity findOneInformByUserId(Long userId, long id) {
		// TODO Auto-generated method stub
		userMessageMidDao.updateUserMsg(userId, id);
		return this.baseMapper.findOneInformByUserId(userId, id);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void create(UserMessageEntity userMessage) {
		// TODO Auto-generated method stub
		this.baseMapper.addUserMessage(userMessage);
    	UserMessageMidEntity userMessageMid = new UserMessageMidEntity();
    	Long userId = userMessage.getUserId();
    	if(userId == null || "".equals(null)){
    		List<UserEntity> list = userDao.selectList(new EntityWrapper<UserEntity>());
    		for (UserEntity userEntity : list) {
    			Long userId2 = userEntity.getUserId();
    			userMessageMid.setMessageId(userMessage.getId());
        		userMessageMid.setUserId(userId2);
            	userMessageMid.setStatus(0);
            	userMessageMid.setAddTime(new Date());
            	userMessageMidDao.insert(userMessageMid);
			}
    	}else{
    		userMessageMid.setMessageId(userMessage.getId());
    		userMessageMid.setUserId(userId);
        	userMessageMid.setStatus(0);
        	userMessageMid.setAddTime(new Date());
        	userMessageMidDao.insert(userMessageMid);
    	}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void delete(Long[] ids) {
		for (Long long1 : ids) {
			userMessageMidDao.deleteByMessage(long1);
		}
		
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public int messageS(Long userId) {
		return userMessageMidDao.queryUM(userId);
		
	}
		

}
