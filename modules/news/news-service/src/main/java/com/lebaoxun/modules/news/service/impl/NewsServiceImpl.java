package com.lebaoxun.modules.news.service.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;
import com.lebaoxun.commons.utils.StringUtils;
import com.lebaoxun.modules.news.dao.NewsDao;
import com.lebaoxun.modules.news.entity.NewsEntity;
import com.lebaoxun.modules.news.service.NewsService;

@Service("newsService")
public class NewsServiceImpl extends ServiceImpl<NewsDao, NewsEntity> implements NewsService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${application.web.baseDir:/www/upload}")
	private String baseDir;

	@Value("${application.web.host:http://localhost:81}")
	private String staticsHost;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String u_id = (String) params.get("u_id"), is_top = (String) params.get("is_top");
		Integer check_status = (Integer) params.get("check_status"), class_id = (Integer) params.get("class_id");
		Page<NewsEntity> page = this.selectPage(new Query<NewsEntity>(params).getPage(),
				new EntityWrapper<NewsEntity>().eq(StringUtils.isNotBlank(u_id), "u_id", u_id)
						.eq(class_id != null, "class_id", class_id).eq(StringUtils.isNotBlank(is_top), "is_top", is_top)
						.eq(check_status != null, "check_status", check_status).orderBy("is_top", false)
						.orderBy("create_time", false).orderBy("check_status", true));

		return new PageUtils(page);
	}

	@Override
	public List<NewsEntity> queryReleaseNews(Integer size, Integer offset, Integer class_id) {
		// TODO Auto-generated method stub
		return this.baseMapper.queryReleaseNews(size, offset, class_id);
	}

	@Override
	public NewsEntity queryReleaseNewsInfo(Long id) {
		// TODO Auto-generated method stub
		return this.baseMapper.queryReleaseNewsInfo(id);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void modifyClicks(Long id, boolean flag) {
		// TODO Auto-generated method stub
		this.baseMapper.modifyClicks(id, flag);
	}


}
