package com.lebaoxun.modules.account.dao;

import com.lebaoxun.modules.account.entity.UserAddressEntity;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 用户地址
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-26 10:20:24
 */
@Mapper
public interface UserAddressDao extends BaseMapper<UserAddressEntity> {
	
}
