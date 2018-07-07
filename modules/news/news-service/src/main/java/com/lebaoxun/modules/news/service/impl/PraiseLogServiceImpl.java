package com.lebaoxun.modules.news.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;
import com.lebaoxun.modules.news.dao.PraiseLogDao;
import com.lebaoxun.modules.news.entity.PraiseLogEntity;
import com.lebaoxun.modules.news.service.PraiseLogService;


@Service("praiseLogService")
public class PraiseLogServiceImpl extends ServiceImpl<PraiseLogDao, PraiseLogEntity> implements PraiseLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<PraiseLogEntity> page = this.selectPage(
                new Query<PraiseLogEntity>(params).getPage(),
                new EntityWrapper<PraiseLogEntity>().orderBy("create_time", false)
        );

        return new PageUtils(page);
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean delete(Wrapper<PraiseLogEntity> wrapper) {
    	// TODO Auto-generated method stub
    	PraiseLogEntity pl = (PraiseLogEntity) this.baseMapper.selectObjs(wrapper);
    	this.baseMapper.deductPraises(pl.getType(), pl.getRecordId());
    	return super.delete(wrapper);
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean deleteBatchIds(Collection<? extends Serializable> idList) {
    	// TODO Auto-generated method stub
    	List<PraiseLogEntity> list = this.baseMapper.selectBatchIds(idList);
    	for(PraiseLogEntity pl : list){
    		this.baseMapper.deductPraises(pl.getType(), pl.getRecordId());
    	}
    	return super.deleteBatchIds(idList);
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean insert(PraiseLogEntity entity) {
    	// TODO Auto-generated method stub
    	this.baseMapper.addPraises(entity.getType(), entity.getRecordId());
    	return super.insert(entity);
    }

}
