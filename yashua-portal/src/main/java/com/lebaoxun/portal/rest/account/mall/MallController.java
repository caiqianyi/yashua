package com.lebaoxun.portal.rest.account.mall;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.mall.service.IMallCategoryService;
import com.lebaoxun.modules.mall.service.IMallProductService;
import com.lebaoxun.portal.rest.BaseController;

@Controller
public class MallController extends BaseController {
	
	@Resource
	private IMallCategoryService mallCategoryService;
	
	@Resource
	private IMallProductService mallProductService;

	@RequestMapping("/mall/list.html")
	public String list(Map<String,Object> map){
		map.put("categorys", mallCategoryService.release());
		return "/mall/list";
	}
	
	@RequestMapping("/mall/product/list")
	@ResponseBody
    ResponseMessage showList(@RequestParam("categoryId")Long categoryId, 
    		@RequestParam("size")Integer size, 
    		@RequestParam("offset")Integer offset){
		return mallProductService.findShowProdcutByCategory(categoryId, size, offset);
	}
}
