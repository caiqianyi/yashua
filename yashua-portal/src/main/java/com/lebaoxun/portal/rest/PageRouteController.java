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

package com.lebaoxun.portal.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统页面视图
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2016年11月24日 下午11:05:27
 */
@Controller
public class PageRouteController extends BaseController{
	
	@RequestMapping("/pages/{module}/{url}.html")
	public String pages(@PathVariable("module") String module, @PathVariable("url") String url){
		return "pages/" + module + "/" + url;
	}

	@RequestMapping(value="/")
	public String redirect(){
		return "redirect:/login.html";
	}
	@RequestMapping(value="index.html")
	public String index(){
		return "index";
	}

	@RequestMapping("login.html")
	public String login(){
		return "login";
	}

	@RequestMapping("404.html")
	public String notFound(){
		return "404";
	}

}
