package com.lebaoxun.modules.news.service.hystrix;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lebaoxun.modules.news.entity.NewsEntity;
import com.lebaoxun.modules.news.service.INewsService;
import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.exception.ResponseMessage;

/**
 * 新闻表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-05 16:39:42
 */

@Component
public class NewsServiceHystrix implements INewsService {

	@Override
	public ResponseMessage list(Map<String, Object> params) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage info(Integer id) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage save(Long adminId,NewsEntity news) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage update(Long adminId,NewsEntity news) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage delete(Long adminId,Integer[] ids) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public List<NewsEntity> list(Integer size, 
    		Integer offset,
    		Integer class_id) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage praise(Long id, Long userId, String host) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage toReply(Long id, Long userId, String content,
			Integer toReplyId, String host) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage replys(Map<String, Object> params) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public NewsEntity releaseInfo(Long id) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}
    
	@Override
	public ResponseMessage modifyClicks(Long id, boolean flag) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}
}

