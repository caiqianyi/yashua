package com.lebaoxun.portal.rest.account.mall;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.mall.entity.MallProductEntity;
import com.lebaoxun.modules.mall.entity.MallProductSpecificationEntity;
import com.lebaoxun.modules.mall.service.IMallCategoryService;
import com.lebaoxun.modules.mall.service.IMallProductCommentService;
import com.lebaoxun.modules.mall.service.IMallProductService;
import com.lebaoxun.modules.mall.service.IMallProductSpecificationService;
import com.lebaoxun.portal.rest.BaseController;

@Controller
public class MallController extends BaseController {
	
	@Resource
	private IMallCategoryService mallCategoryService;
	
	@Resource
	private IMallProductService mallProductService;
	
	@Resource
	private IMallProductSpecificationService mallProductSpecification;
	
	@Resource
	private IMallProductCommentService mallProductCommentService;

	@RequestMapping("/mall/list.html")
	public String list(Map<String,Object> map){
		map.put("categorys", mallCategoryService.release());
		return "/mall/list";
	}
	
	@RequestMapping("/mall/info/{id}.html")
	public String info(@PathVariable("id")Long id,Map<String,Object> map){
		MallProductEntity product = mallProductService.findShowProdcutInfo(id);
		if(product == null){
			return "redirect:/404.html";
		}
		List<MallProductSpecificationEntity> specs = mallProductSpecification.queryByProductId(id);
		map.put("product", product);
		map.put("specs", specs);
		map.put("lastComment", mallProductCommentService.selectLastByProduct(id));
		return "/mall/info";
	}
	
	@RequestMapping("/mall/product/list")
	@ResponseBody
    ResponseMessage showList(@RequestParam("categoryId")Long categoryId, 
    		@RequestParam("size")Integer size, 
    		@RequestParam("offset")Integer offset){
		return mallProductService.findShowProdcutByCategory(categoryId, size, offset);
	}
	
	@RequestMapping("/mall/product/info/specs")
	@ResponseBody
    ResponseMessage specs(@RequestParam("id")Long id){
		return ResponseMessage.ok(mallProductSpecification.queryByProductId(id));
	}
}
