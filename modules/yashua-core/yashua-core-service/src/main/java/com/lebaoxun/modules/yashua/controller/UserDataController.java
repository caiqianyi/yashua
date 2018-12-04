package com.lebaoxun.modules.yashua.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.modules.yashua.service.UserDataService;

/**
 * 用户口气表
 */
@RestController
public class UserDataController {
	@Autowired
	private UserDataService userDataService;
	/**
     * 列表
     */
    @RequestMapping("/yashua/userdata/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        PageUtils page = userDataService.queryPage(params);
        return ResponseMessage.ok(page);
    }

}
