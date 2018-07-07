package com.lebaoxun.modules.news.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;
import com.lebaoxun.modules.news.dao.ReplysDao;
import com.lebaoxun.modules.news.entity.ReplysEntity;
import com.lebaoxun.modules.news.service.ReplysService;


@Service("replysService")
public class ReplysServiceImpl extends ServiceImpl<ReplysDao, ReplysEntity> implements ReplysService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	String type = (String) params.get("type"),
    			record_id = (String) params.get("record_id");
        Page<ReplysEntity> page = this.selectPage(
                new Query<ReplysEntity>(params).getPage(),
                new EntityWrapper<ReplysEntity>().orderBy("create_time", false)
                .eq(StringUtils.isNotBlank(type), "type", type)
                .eq(StringUtils.isNotBlank(record_id), "record_id", record_id)
                .orderBy("praises", false)
                .orderBy("create_time", false)
        );
        return new PageUtils(page);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean delete(Wrapper<ReplysEntity> wrapper) {
    	// TODO Auto-generated method stub
    	ReplysEntity pl = (ReplysEntity) this.baseMapper.selectObjs(wrapper);
    	this.baseMapper.deductReplys(pl.getType(), pl.getRecordId()+"");
    	return super.delete(wrapper);
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean deleteBatchIds(Collection<? extends Serializable> idList) {
    	// TODO Auto-generated method stub
    	List<ReplysEntity> list = this.baseMapper.selectBatchIds(idList);
    	for(ReplysEntity pl : list){
    		this.baseMapper.deductReplys(pl.getType(), pl.getRecordId()+"");
    	}
    	return super.deleteBatchIds(idList);
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean insert(ReplysEntity entity) {
    	// TODO Auto-generated method stub
    	this.baseMapper.addReplys(entity.getType(), entity.getRecordId()+"");
    	return super.insert(entity);
    }
}
