CREATE TABLE `user_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `account` varchar(20) DEFAULT NULL COMMENT '用户账号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '日志时间',
  `log_type` varchar(50) DEFAULT 'P' COMMENT '日志类型',
  `trade_money` int(11) DEFAULT '0' COMMENT '交易金额',
  `money` int(11) DEFAULT '0' COMMENT '账户余额',
  `descr` varchar(500) DEFAULT NULL COMMENT '日志说明',
  `adjunct_info` varchar(500) DEFAULT NULL COMMENT '日志参数',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `account` (`account`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `log_type` (`log_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户日志表';





-- 菜单SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    VALUES ('1', '', 'modules/account/userlog.html', NULL, '1', 'fa fa-file-code-o', '6');

-- 按钮父菜单ID
set @parentId = @@identity;

-- 菜单对应按钮SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '查看', null, 'account:userlog:list,account:userlog:info', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '新增', null, 'account:userlog:save', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '修改', null, 'account:userlog:update', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '删除', null, 'account:userlog:delete', '2', null, '6';
