CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `account` varchar(20) DEFAULT NULL COMMENT '用户账号',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `source` varchar(200) DEFAULT NULL COMMENT '注册来源',
  `type` varchar(200) DEFAULT NULL COMMENT '账号类型',
  `status` varchar(50) DEFAULT NULL COMMENT 'Y 正常 N 禁用  F 异常冻结',
  `host` varchar(50) DEFAULT NULL COMMENT '登录信息',
  `level` int(11) DEFAULT '0' COMMENT '账户等级',
  `balance` int(11) DEFAULT '0' COMMENT '账户金额',
	`realname` varchar(50) DEFAULT NULL COMMENT '真实姓名',
	`birthday` datetime DEFAULT NULL COMMENT '生日',
	`identity` varchar(50) DEFAULT NULL COMMENT '身份证号',
  `subscribe` varchar(50) DEFAULT NULL COMMENT '用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息',
  `openid` varchar(200) DEFAULT NULL COMMENT '微信openid',
  `unionid` varchar(200) DEFAULT NULL COMMENT '只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段',
  `nickname` varchar(200) DEFAULT NULL COMMENT '用户的昵称',
  `sex` int(10) DEFAULT '0' COMMENT '用户的性别，值为1时是男性，值为2时是女性，值为0时是未知',
  `city` varchar(200) DEFAULT NULL COMMENT '用户所在城市',
  `country` varchar(200) DEFAULT NULL COMMENT '用户所在国家',
  `province` varchar(200) DEFAULT NULL COMMENT '用户所在省份',
  `language` varchar(200) DEFAULT NULL COMMENT '用户的语言，简体中文为zh_CN',
  `headimgurl` varchar(500) DEFAULT NULL COMMENT '户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。',
  `subscribe_time` varchar(50) DEFAULT NULL COMMENT '用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注',
  `groupid` varchar(200) DEFAULT NULL COMMENT '用户所在的分组ID（兼容旧的用户分组接口）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_buy_time` datetime DEFAULT NULL COMMENT '最后购买时间',
  `last_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`),
  UNIQUE KEY `account` (`account`),
  UNIQUE KEY `mobile` (`mobile`),
  UNIQUE KEY `openid` (`openid`),
  KEY `type_index` (`type`),
  KEY `status_index` (`status`),
  KEY `level_index` (`level`),
  KEY `create_time_index` (`create_time`),
  KEY `last_login_time_index` (`last_login_time`),
  KEY `last_buy_time_index` (`last_buy_time`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户表';

INSERT INTO `yashua_db`.`user` (`id`, `user_id`, `account`, `mobile`, `password`, `source`, `type`, `status`, `host`, `level`, `balance`, `subscribe`, `openid`, `unionid`, `nickname`, `sex`, `city`, `country`, `province`, `language`, `headimgurl`, `subscribe_time`, `remark`, `groupid`, `create_time`, `last_login_time`, `last_buy_time`, `last_update_time`) VALUES ('1', '88731584', '15010602819', '15010602819', '59DF46BDE95A7A04DDA2222592BCC3E6', 'wechatOA', '0', 'Y', NULL, '0', '1101', '0', 'oI9MD0mwrw-V2nt94K-uk7X5q2Hw', NULL, '一蓑烟雨', '1', '海淀', '中国', '北京', 'zh_CN', 'http://localhost:81/user/88731584/1530177008495.png', NULL, NULL, NULL, '2017-12-25 13:50:20', NULL, NULL, '2018-06-28 17:10:10');



-- 菜单SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    VALUES ('1', '用户表', 'modules/account/user.html', NULL, '1', 'fa fa-file-code-o', '6');

-- 按钮父菜单ID
set @parentId = @@identity;

-- 菜单对应按钮SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '查看', null, 'account:user:list,account:user:info', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '新增', null, 'account:user:save', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '修改', null, 'account:user:update', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '删除', null, 'account:user:delete', '2', null, '6';
