package com.lebaoxun.admin.rest.account;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.account.entity.UserMessageEntity;
import com.lebaoxun.modules.account.service.IUserMessageService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;



/**
 * 用户消息
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-20 15:44:50
 */
@RestController
public class UserMessageController {
    @Autowired
    private IUserMessageService userMessageService;

    /**
     * 列表
     */
    @RequestMapping("/account/usermessage/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        return userMessageService.list(params);
    }


    /**
     * 信息
     */
    @RequestMapping("/account/usermessage/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id){
        return userMessageService.info(id);
    }

    /**
     * 保存
     */
    @RequestMapping("/account/usermessage/save")
    ResponseMessage save(@RequestBody UserMessageEntity userMessage){
        return userMessageService.save(userMessage);
    }

    /**
     * 修改
     */
    @RequestMapping("/account/usermessage/update")
    ResponseMessage update(@RequestBody UserMessageEntity userMessage){
        return userMessageService.update(userMessage);
    }

    /**
     * 删除
     */
    @RequestMapping("/account/usermessage/delete")
    ResponseMessage delete(@RequestBody Integer[] ids){
        return userMessageService.delete(ids);
    }

}
