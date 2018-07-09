DROP TABLE IF EXISTS `sign_log`;
CREATE TABLE `sign_log` (
`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
`user_id` bigint(20) NOT NULL COMMENT '用户ID',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '签到时间',
PRIMARY KEY (`id`),
KEY `user_id` (`user_id`) USING BTREE,
KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='签到记录表';

DROP TABLE IF EXISTS `sign_uinfo`;
CREATE TABLE `sign_uinfo` (
`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
`user_id` bigint(20) NOT NULL COMMENT '用户ID',
`keep_up_count` int(11) DEFAULT 0 COMMENT '连续签到数',
`m_keep_up_count` int(11) DEFAULT 0 COMMENT '月签到数',
`total_sign_num` int(11) DEFAULT 0 COMMENT '总签到数',
`total_resign_num` int(11) DEFAULT 0 COMMENT '总未签到数',
`sign_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后签到时间',
PRIMARY KEY (`id`),
KEY `user_id` (`user_id`) USING BTREE,
KEY `sign_time` (`sign_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='签到用户表';


DROP TABLE IF EXISTS `sign_award`;
CREATE TABLE `sign_award` (
`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
`user_id` bigint(20) NOT NULL COMMENT '用户ID',
`condition` varchar(200) NOT NULL COMMENT '自定义条件值',
`award_sql` varchar(500) NOT NULL COMMENT '自定义奖励SQL',
`name` varchar(200) DEFAULT NULL COMMENT '奖励名称',
`group_id` varchar(200) NOT NULL COMMENT '分组名',
`flag` tinyint(4) DEFAULT '-1' COMMENT '是否启用  -1：未启用  0：启用',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY (`id`),
KEY `user_id` (`user_id`) USING BTREE,
KEY `name` (`name`) USING BTREE,
KEY `group_id` (`group_id`) USING BTREE,
KEY `flag` (`flag`) USING BTREE,
KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='签到奖励规则表';
