package com.lebaoxun.modules.mall.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;
import com.lebaoxun.modules.mall.dao.MallSpecificationAttributeDao;
import com.lebaoxun.modules.mall.dao.MallSpecificationDao;
import com.lebaoxun.modules.mall.entity.MallSpecificationAttributeEntity;
import com.lebaoxun.modules.mall.entity.MallSpecificationEntity;
import com.lebaoxun.modules.mall.service.MallSpecificationAttributeService;


@Service("mallSpecificationAttributeService")
public class MallSpecificationAttributeServiceImpl extends ServiceImpl<MallSpecificationAttributeDao, MallSpecificationAttributeEntity> implements MallSpecificationAttributeService {

	@Resource
	private MallSpecificationDao mallSpecificationDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<MallSpecificationAttributeEntity> page = this.selectPage(
                new Query<MallSpecificationAttributeEntity>(params).getPage(),
                new EntityWrapper<MallSpecificationAttributeEntity>()
        );
        List<MallSpecificationAttributeEntity> records = page.getRecords();
        for(MallSpecificationAttributeEntity record : records){
        	MallSpecificationEntity specification = mallSpecificationDao.selectById(record.getSpecificationId());
        	record.setSpecificationName(specification.getName());
        }
        return new PageUtils(page);
    }

}
