CREATE TABLE `user_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '日志时间',
  `log_type` varchar(50) DEFAULT 'P' COMMENT '日志类型',
  `trade_money` int(11) DEFAULT '0' COMMENT '交易金额',
  `money` int(11) DEFAULT '0' COMMENT '账户余额',
  `platform` varchar(500) NOT NULL COMMENT '操作平台标识',
  `host_ip` varchar(500) DEFAULT NULL COMMENT '日志发生IP',
  `descr` varchar(500) DEFAULT NULL COMMENT '日志说明',
  `adjunct_info` varchar(500) DEFAULT NULL COMMENT '日志参数',
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `log_type` (`log_type`) USING BTREE,
  KEY `platform` (`platform`(255)) USING BTREE,
  KEY `host_ip` (`host_ip`(255)) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



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
