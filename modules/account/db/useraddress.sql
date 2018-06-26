CREATE TABLE `user_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `default_flag` varchar(500) DEFAULT NULL COMMENT '默认首选',
  `consignee` varchar(20) DEFAULT NULL COMMENT '收货人',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `area_code` varchar(20) DEFAULT NULL COMMENT '所在地区',
  `address` varchar(200) NOT NULL COMMENT '详细地址',
  `name` varchar(500) DEFAULT NULL COMMENT '地址别名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `default_flag` (`default_flag`) USING BTREE,
  KEY `consignee` (`consignee`) USING BTREE,
  KEY `mobile` (`mobile`) USING BTREE,
  KEY `name` (`name`(255)) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户地址';
-- 菜单SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    VALUES ('1', '用户地址', 'modules/account/useraddress.html', NULL, '1', 'fa fa-file-code-o', '6');

-- 按钮父菜单ID
set @parentId = @@identity;

-- 菜单对应按钮SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '查看', null, 'account:useraddress:list,account:useraddress:info', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '新增', null, 'account:useraddress:save', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '修改', null, 'account:useraddress:update', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '删除', null, 'account:useraddress:delete', '2', null, '6';
