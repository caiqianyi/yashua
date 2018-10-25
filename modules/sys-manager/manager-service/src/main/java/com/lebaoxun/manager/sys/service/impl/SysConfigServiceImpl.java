/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.lebaoxun.manager.sys.service.impl;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.lebaoxun.commons.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;
import com.lebaoxun.manager.sys.dao.SysConfigDao;
import com.lebaoxun.manager.sys.entity.SysConfigEntity;
import com.lebaoxun.manager.sys.service.SysConfigService;

@Service("sysConfigService")
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfigEntity> implements SysConfigService {
	
	@Autowired
    private RedisTemplate redisTemplate;
    @Resource(name="redisTemplate")
    private ValueOperations<String, String> valueOperations;
	
	/**  默认过期时长，单位：秒 */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24;
    /**  不设置过期时长 */
    public final static long NOT_EXPIRE = -1;
    
    /**
     * Object转成JSON数据
     */
    private String toJson(Object object){
        if(object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String){
            return String.valueOf(object);
        }
        return JSON.toJSONString(object);
    }
    
    /**
     * JSON数据，转成Object
     */
    private <T> T fromJson(String json, Class<T> clazz){
        return JSON.parseObject(json, clazz);
    }

    public void set(String key, Object value, long expire){
        valueOperations.set(key, toJson(value));
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }
    
    public void set(String key, Object value){
        set(key, value, DEFAULT_EXPIRE);
    }
    
    public void saveOrUpdate(SysConfigEntity config) {
        if(config == null){
            return ;
        }
        String key = getSysConfigKey(config.getParamKey());
        set(key, config);
    }

    public void delete(String configKey) {
        String key = getSysConfigKey(configKey);
        redisTemplate.delete(key);
    }
    
    
    public <T> T get(String key, Class<T> clazz, long expire) {
        String value = valueOperations.get(key);
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value == null ? null : fromJson(value, clazz);
    }
    
    public SysConfigEntity get(String configKey){
        String key = getSysConfigKey(configKey);
        return get(key, SysConfigEntity.class,NOT_EXPIRE);
    }
	
	public static String getSysConfigKey(String key){
        return "sys:config:" + key;
    }

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String paramKey = (String)params.get("paramKey");

		Page<SysConfigEntity> page = this.selectPage(
				new Query<SysConfigEntity>(params).getPage(),
				new EntityWrapper<SysConfigEntity>()
					.like(StringUtils.isNotBlank(paramKey),"param_key", paramKey)
					.eq("status", 1)
		);

		return new PageUtils(page);
	}
	
	@Override
	public void save(SysConfigEntity config) {
		this.insert(config);
		saveOrUpdate(config);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(SysConfigEntity config) {
		this.updateAllColumnById(config);
		saveOrUpdate(config);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateValueByKey(String key, String value) {
		baseMapper.updateValueByKey(key, value);
		delete(key);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteBatch(Long[] ids) {
		for(Long id : ids){
			SysConfigEntity config = this.selectById(id);
			delete(config.getParamKey());
		}

		this.deleteBatchIds(Arrays.asList(ids));
	}

	@Override
	public String getValue(String key) {
		SysConfigEntity config = get(key);
		if(config == null){
			config = baseMapper.queryByKey(key);
			saveOrUpdate(config);
		}

		return config == null ? null : config.getParamValue();
	}
	
	@Override
	public <T> T getConfigObject(String key, Class<T> clazz) {
		String value = getValue(key);
		if(StringUtils.isNotBlank(value)){
			return new Gson().fromJson(value, clazz);
		}

		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new I18nMessageException("-1","获取参数失败");
		}
	}
}
