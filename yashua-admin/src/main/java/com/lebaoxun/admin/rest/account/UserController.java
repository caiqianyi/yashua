package com.lebaoxun.admin.rest.account;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.commons.utils.ExportExcelUtil;
import com.lebaoxun.commons.utils.GenerateCode;
import com.lebaoxun.modules.account.entity.UserEntity;
import com.lebaoxun.modules.account.service.IUserService;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;

/**
 * 用户表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-19 20:14:31
 */
@RestController
public class UserController {

	@Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;

	@Autowired
	private IUserService userService;

	/**
	 * 列表
	 */
	@RequestMapping("/account/user/list")
	ResponseMessage list(@RequestParam Map<String, Object> params) {
		return userService.list(params);
	}

	@RequestMapping(value = "/account/export/user.xlsx", method = RequestMethod.GET)
	@ResponseBody
	void exportExcel(HttpServletResponse response) {
		List<UserEntity> users = userService.all();
		ExportExcelUtil<UserEntity> excel = new ExportExcelUtil<UserEntity>();
		ServletOutputStream out = null;
		try {
			response.setCharacterEncoding("UTF-8");
    		response.setContentType("application/x-download");
 
    		String fileName = "会员列表-"+DateFormatUtils.format(new Date(), "yyyy-MM-dd")+".xls";
    		fileName = URLEncoder.encode(fileName, "UTF-8");
    		response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
    		
    		out = response.getOutputStream();
    		String[][] headermaps = new String[2][];
    		headermaps[0] = new String[]{
    				"用户ID","账号","注册来源","状态","账户余额","是否关注","openid","昵称","生日","身份证号","真实姓名",
    				"性别","城市","国家","省份","头像","注册时间","最后登录时间","最后购买时间"};
    		headermaps[1] = new String[]{
    				"userId","account","source","status","balance","subscribe","openid","nickname","birthday","identity","realname",
    				"sex","city","country","province","headimgurl","createTime","lastLoginTime","lastBuyTime"};
			excel.exportExcelPlus(
					"会员列表",
					headermaps, users, out, null,
					40000);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(out != null){
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 信息
	 */
	@RequestMapping("/account/user/info/{id}")
	ResponseMessage info(@PathVariable("id") Long id) {
		return userService.info(id);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/account/user/save")
	ResponseMessage save(@RequestBody UserEntity user) {
		user.setBalance(0);
		user.setLevel(0);
		user.setSource("ADMIN");
		user.setType("A");
		user.setUserId(GenerateCode.gen16(9));
		user.setMobile(user.getAccount());
		return userService.save(oauth2SecuritySubject.getCurrentUser(), user);
	}

	/**
	 * 删除
	 */
	@RequestMapping("/account/user/delete")
	ResponseMessage delete(@RequestBody String[] ids) {
		return userService.delete(oauth2SecuritySubject.getCurrentUser(), ids);
	}

	/**
	 * 设置用户账户状态
	 * 
	 * @param adminId
	 *            管理员
	 * @param userId
	 *            用户ID
	 * @param scope
	 *            获取管理员host使用
	 * @return
	 */
	@RequestMapping("/account/user/disabled")
	ResponseMessage disabled(@RequestParam(value = "userId") Long userId) {
		return userService.disabled(oauth2SecuritySubject.getCurrentUser(),
				userId);
	}

	/**
	 * 修改密码
	 * 
	 * @param userId
	 *            用户ID
	 * @param newPasswd
	 *            新密码（非加密）
	 * @param adminId
	 *            操作人
	 */
	@RequestMapping("/account/user/modifyPassword")
	ResponseMessage modifyPassword(@RequestParam(value = "userId") Long userId,
			@RequestParam(value = "newPasswd") String newPasswd) {
		return userService.modifyPassword(userId, newPasswd,
				oauth2SecuritySubject.getCurrentUser());
	}

	/**
	 * 修改账户金额
	 * 
	 * @param userId
	 *            用户ID
	 * @param amount
	 *            变更数量
	 * @param adminId
	 *            操作人
	 * @param logType
	 *            带字母U开头，为用户本人操作产生的日志
	 * @param descr
	 *            操作说明
	 */
	@RequestMapping("/account/user/modifyBalance")
	ResponseMessage modifyBalance(@RequestParam(value = "userId") Long userId,
			@RequestParam(value = "amount") Integer amount,
			@RequestParam(value = "descr", required = false) String descr) {
		return userService.modifyBalance(userId, amount,
				oauth2SecuritySubject.getCurrentUser(), descr);
	}
}
