DROP TABLE IF EXISTS `praise_log`;
CREATE TABLE `praise_log` (
`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
`user_id` bigint(20) NOT NULL COMMENT '用户ID',
`type` varchar(50) DEFAULT NULL COMMENT '赞类型',
`host` varchar(500) DEFAULT NULL COMMENT 'host',
`record_id` varchar(200) DEFAULT NULL COMMENT '点赞记录ID',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY (`id`),
UNIQUE KEY `uuid` (`user_id`,`type`,`record_id`) USING BTREE,
KEY `user_id` (`user_id`) USING BTREE,
KEY `record_id` (`record_id`) USING BTREE,
KEY `type` (`type`) USING BTREE,
KEY `host` (`host`) USING BTREE,
KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='点赞表';

DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
`u_id` bigint(20) NOT NULL COMMENT '作者ID',
`author` varchar(255) DEFAULT NULL COMMENT '作者',
`title` varchar(255) DEFAULT NULL COMMENT '标题',
`content` longtext NOT NULL COMMENT '内容',
`class_id` int(11) NOT NULL COMMENT '分类ID',
`pic_items` varchar(500) DEFAULT NULL COMMENT '图片',
`clicks` int(11) NOT NULL COMMENT '点击数',
`praises` int(11) NOT NULL COMMENT '点赞数',
`replies` int(11) NOT NULL COMMENT '回复数',
`last_reply_id` bigint(20) DEFAULT NULL COMMENT '最后回复',
`is_top` tinyint(4) DEFAULT '0' COMMENT '是否置顶  0：否   1：是',
`check_status` tinyint(4) DEFAULT '0' COMMENT '审核状态  0：未通过   1：已通过',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY (`id`),
KEY `u_id` (`u_id`) USING BTREE,
KEY `title` (`title`) USING BTREE,
KEY `class_id` (`class_id`) USING BTREE,
KEY `last_reply_id` (`last_reply_id`) USING BTREE,
KEY `is_top` (`is_top`) USING BTREE,
KEY `check_status` (`check_status`) USING BTREE,
KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='新闻表';

DROP TABLE IF EXISTS `replys`;
CREATE TABLE `replys` (
`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
`content` varchar(500) NOT NULL COMMENT '内容',
`user_id` bigint(20) NOT NULL COMMENT '回复人',
`type` varchar(50) DEFAULT NULL COMMENT '评论分类',
`record_id` int(11) NOT NULL COMMENT '回帖ID',
`to_reply_id` int(11) DEFAULT NULL COMMENT '给谁回复',
`praises` int(11) DEFAULT '0' COMMENT '点赞数',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY (`id`),
KEY `user_id` (`user_id`) USING BTREE,
KEY `record_id` (`record_id`) USING BTREE,
KEY `to_reply_id` (`to_reply_id`) USING BTREE,
KEY `praises` (`praises`) USING BTREE,
KEY `type` (`type`) USING BTREE,
KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='回复表';