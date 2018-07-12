DROP TABLE IF EXISTS `mall_category`;
CREATE TABLE `mall_category` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父分类ID',
  `name` varchar(64) DEFAULT NULL COMMENT '分类名称',
  `icon` varchar(200) DEFAULT NULL COMMENT '分类图标',
  `sort` int(9) DEFAULT NULL COMMENT '排序',
  `type` tinyint(2) DEFAULT NULL COMMENT '目录类型 2=二级目录/1=一级目录/0=总目录',
  `status` tinyint(2) DEFAULT '0' COMMENT '状态 1=显示/0=隐藏',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '更新者',
  `remarks` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='分类表';

DROP TABLE IF EXISTS `mall_category_product`;
CREATE TABLE `mall_category_product` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `category_id` bigint(20) DEFAULT NULL COMMENT '分类ID',
	`product_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='商品分类关联表';

DROP TABLE IF EXISTS `mall_product`;
CREATE TABLE `mall_product` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `product_number` bigint(20) unsigned DEFAULT NULL COMMENT '商品编号',
  `label_id` int(10) DEFAULT NULL COMMENT '标签ID',
  `name` varchar(64) DEFAULT NULL COMMENT '商品名称',
  `show_score` int(11) DEFAULT NULL COMMENT '显示积分',
  `show_price` decimal(10,2) DEFAULT NULL COMMENT '显示价格',
	`show_pic` varchar(255) DEFAULT NULL COMMENT '展示图片',
  `introduce` varchar(64) DEFAULT NULL COMMENT '商品简介',
	`description` text COMMENT '商品描述',
  `show_in_top` tinyint(2) DEFAULT '0' COMMENT '是否置顶 1=置顶/0=默认',
  `show_in_hot` tinyint(2) DEFAULT '0' COMMENT '是否热门 1=热门/0=默认',
  `show_in_shelve` tinyint(2) DEFAULT '0' COMMENT '是否上架：1=上架/0=下架',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '创建者',
  `shelve_time` datetime DEFAULT NULL COMMENT '上架时间',
  `shelve_by` varchar(64) DEFAULT NULL COMMENT '上架人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '更新者',
  `search_key` varchar(255) DEFAULT NULL COMMENT '搜索关键词',
  `share_title` varchar(64) DEFAULT NULL COMMENT '页面标题',
  `share_description` varchar(255) DEFAULT NULL COMMENT '页面描述',
  `remarks` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='商品表';


DROP TABLE IF EXISTS `mall_product_attr`;
CREATE TABLE `mall_product_attr` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '属性ID',
  `product_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  `stock` int(11) DEFAULT '0' COMMENT '总库存',
  `sales_volume` int(11) DEFAULT '0' COMMENT '销售量',
  `clicks` int(11) DEFAULT '0' COMMENT '游览量',
  `replies` int(11) DEFAULT '0' COMMENT '评论数量',
  `show_replies` int(11) DEFAULT '0' COMMENT '晒单评价数量',
  `comment_average` decimal(10,0) DEFAULT '0' COMMENT '平均评价',
  `favorite_number` int(11) DEFAULT '0' COMMENT '收藏数',
  `question_number` int(11) DEFAULT NULL COMMENT '提问数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='商品属性表';


DROP TABLE IF EXISTS `mall_product_image`;
CREATE TABLE `mall_product_image` (
  `pic_img_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '商品图片ID',
  `product_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  `pic_img` varchar(255) DEFAULT NULL COMMENT '展示图片',
  `sort` tinyint(2) DEFAULT NULL COMMENT '排序',
  `status` tinyint(2) DEFAULT '1' COMMENT '状态：1.显示；0.隐藏',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`pic_img_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='商品图片表';


DROP TABLE IF EXISTS `mall_product_parameter`;
CREATE TABLE `mall_product_parameter` (
  `product_parameter_id` bigint(20) NOT NULL COMMENT '参数ID',
  `product_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  `name` varchar(64) DEFAULT NULL COMMENT '参数名',
  `value` varchar(64) DEFAULT NULL COMMENT '参数值',
  `status` tinyint(2) DEFAULT NULL COMMENT '状态：1.显示；0.隐藏',
  `sort` int(9) DEFAULT NULL COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`product_parameter_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品参数表';


DROP TABLE IF EXISTS `mall_product_specification`;
CREATE TABLE `mall_product_specification` (
  `product_spec_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '商品规格ID',
  `product_spec_number` bigint(20) unsigned DEFAULT NULL COMMENT '商品规格编号',
  `product_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  `spec` varchar(64) DEFAULT NULL COMMENT '规格：规格ID，以“,”相隔',
  `stock` int(11) DEFAULT '0' COMMENT '库存',
  `sales_volume` int(11) DEFAULT '0' COMMENT '销售量',
  `price` decimal(10,2) DEFAULT NULL COMMENT '价格',
  `score` int(11) DEFAULT NULL COMMENT '积分',
  `default_status` tinyint(2) DEFAULT '0' COMMENT '是否默认状态：0,不默认；1,默认',
  `status` tinyint(2) DEFAULT '0' COMMENT '商品状态：0,新增；1,上架；2,下架',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`product_spec_id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COMMENT='商品规格表';

DROP TABLE IF EXISTS `mall_specification`;
CREATE TABLE `mall_specification` (
  `specification_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '规格ID',
  `category_id` bigint(20) DEFAULT NULL COMMENT '分类ID',
  `name` varchar(64) DEFAULT NULL COMMENT '规格名称',
  `status` tinyint(2) DEFAULT '1' COMMENT '状态：1.显示；0.隐藏',
  `sort` int(9) DEFAULT NULL COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`specification_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='规格表\r\n';

DROP TABLE IF EXISTS `mall_specification_attribute`;
CREATE TABLE `mall_specification_attribute` (
  `spec_attr_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '规格属性ID',
  `specification_id` bigint(20) DEFAULT NULL COMMENT '规格ID',
  `name` varchar(64) DEFAULT NULL COMMENT '规格属性名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '创建者',
  PRIMARY KEY (`spec_attr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='规格属性表';

DROP TABLE IF EXISTS `mall_cart`;
CREATE TABLE `mall_cart` (
  `cart_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '购物车ID',
  `product_spec_number` bigint(20) unsigned DEFAULT NULL COMMENT '商品规格编号',
  `user_id` bigint(20) unsigned DEFAULT NULL COMMENT '用户ID',
  `buy_number` int(11) DEFAULT '1' COMMENT '购买数量',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `check_status` tinyint(2) DEFAULT '1' COMMENT '购物车状态：0,未选中；1,选中',
  PRIMARY KEY (`cart_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='购物车表';

DROP TABLE IF EXISTS `mall_order`;
CREATE TABLE `mall_order` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_number` bigint(20) DEFAULT NULL COMMENT '订单编号,系统生成',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `pay_type` tinyint(2) DEFAULT '1' COMMENT '支付方式 0=积分兑换，1=在线支付',
  `shipment_time` tinyint(2) DEFAULT NULL COMMENT '配送时间 1=不限送货时间，2=工作日送货，3=双休日、假日送货',
  `shipment_type` tinyint(2) DEFAULT '0' COMMENT '配送方式 0=快递配送（免运费），1=快递配送（运费）',
  `shipment_amount` decimal(10,0) DEFAULT '0' COMMENT '快递费',
  `invoice_type` tinyint(2) DEFAULT NULL COMMENT '支付方式 1=不开发票，2=电子发票，3=普通发票',
  `invoice_title` varchar(64) DEFAULT NULL COMMENT '发票抬头',
  `order_status` tinyint(2) DEFAULT '0' COMMENT '订单状态 -1=已删除，0=待支付，1=已支付',
	`postid` varchar(200) DEFAULT NULL COMMENT '运单号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `order_amount` decimal(10,0) DEFAULT '0' COMMENT '订单金额',
  `order_score` int(11) DEFAULT '0' COMMENT '订单积分',
  `pay_amount` decimal(10,0) DEFAULT NULL COMMENT '支付金额 = 订单金额 + 快递费',
  `buy_number` int(11) DEFAULT NULL COMMENT '商品总数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='订单表';

DROP TABLE IF EXISTS `mall_order_product`;
CREATE TABLE `mall_order_product` (
  `order_product_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '订单商品ID',
  `order_id` bigint(20) unsigned DEFAULT NULL COMMENT '订单ID',
  `product_number` bigint(20) unsigned DEFAULT NULL COMMENT '商品编号',
  `name` varchar(64) DEFAULT NULL COMMENT '商品名称',
  `pic_img` varchar(255) DEFAULT NULL COMMENT '展示图片',
  `product_spec_number` bigint(20) unsigned DEFAULT NULL COMMENT '商品规格编号',
  `product_spec_name` varchar(64) DEFAULT NULL COMMENT '商品规格名称',
  `price` decimal(10,2) DEFAULT NULL COMMENT '价格',
  `score` int(11) DEFAULT NULL COMMENT '积分',
  `buy_number` int(11) DEFAULT NULL COMMENT '商品总数量',
  `product_score` int(11) DEFAULT NULL COMMENT '商品总积分',
  `product_amount` decimal(10,0) DEFAULT NULL COMMENT '商品总金额',
  `status` tinyint(2) DEFAULT '0' COMMENT '订单状态 0=待发货，2=已发货，3=待评价，4=已评价',
  PRIMARY KEY (`order_product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='订单明细表';


DROP TABLE IF EXISTS `mall_product_comment`;
CREATE TABLE `mall_product_comment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '评价ID',
  `product_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `nickname` varchar(30) DEFAULT NULL COMMENT '昵称',
  `headimgurl` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单ID',
  `star` tinyint(4) DEFAULT NULL COMMENT '评论星级：1,2,3,4,5',
  `content` varchar(255) DEFAULT NULL COMMENT '评论内容',
  `praises` int(11) DEFAULT '0' COMMENT '好评数',
  `status` tinyint(2) DEFAULT '1' COMMENT '状态：1.显示；0.隐藏',
  `type` tinyint(2) DEFAULT '0' COMMENT '评论类型：1,优质；0,普通',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='评价表';

DROP TABLE IF EXISTS `mall_product_comment_image`;
CREATE TABLE `mall_product_comment_image` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '评价ID',
  `product_comment_id` bigint(20) DEFAULT NULL COMMENT '评价ID',
  `pic_img` varchar(255) DEFAULT NULL COMMENT '展示图片',
  `sort` tinyint(2) DEFAULT NULL COMMENT '排序',
  `status` tinyint(2) DEFAULT '1' COMMENT '状态：1.显示；0.隐藏',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='评价图片表';