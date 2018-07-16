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
import com.lebaoxun.modules.mall.dao.MallCategoryDao;
import com.lebaoxun.modules.mall.dao.MallProductAttrDao;
import com.lebaoxun.modules.mall.dao.MallSpecificationDao;
import com.lebaoxun.modules.mall.entity.MallCategoryEntity;
import com.lebaoxun.modules.mall.entity.MallSpecificationEntity;
import com.lebaoxun.modules.mall.service.MallSpecificationService;


@Service("mallSpecificationService")
public class MallSpecificationServiceImpl extends ServiceImpl<MallSpecificationDao, MallSpecificationEntity> implements MallSpecificationService {

	@Resource
	private MallCategoryDao mallCategoryDao;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<MallSpecificationEntity> page = this.selectPage(
                new Query<MallSpecificationEntity>(params).getPage(),
                new EntityWrapper<MallSpecificationEntity>()
        );
        List<MallSpecificationEntity> records = page.getRecords();
        for(MallSpecificationEntity record : records){
        	MallCategoryEntity category = mallCategoryDao.selectById(record.getCategoryId());
        	record.setCategoryName(category.getName());
        }
        return new PageUtils(page);
    }
	@Override
	public List<MallSpecificationEntity> queryAllList() {
		// TODO Auto-generated method stub
		return this.baseMapper.queryAllList();
	}

}
