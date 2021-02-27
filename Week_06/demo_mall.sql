CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `phone` int(11) NOT NULL COMMENT '手机号',
  `age` int(11) NOT NULL COMMENT '年龄',
  `password` char(32) NOT NULL DEFAULT '' COMMENT '密码',
  `name` varchar(16) NOT NULL DEFAULT '' COMMENT '姓名',
  `address` varchar(30) NOT NULL DEFAULT '' COMMENT '地址',
  `created_time` bigint(20) NOT NULL,
  `updated_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `goods` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` int(11) DEFAULT NULL COMMENT '商品名',
  `price` int(11) DEFAULT NULL COMMENT '价格',
  `img` int(11) DEFAULT NULL COMMENT '商品图片',
  `subtitle` int(11) DEFAULT NULL COMMENT '副标题',
  `description` int(11) DEFAULT NULL COMMENT '商品描述',
  `stock` int(11) DEFAULT NULL COMMENT '库存',
  `status` int(11) DEFAULT NULL COMMENT '商品状态',
  `created_time` int(11) DEFAULT NULL,
  `updated_time` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `order` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `order_no` bigint(20) NOT NULL COMMENT '订单号',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `address` varchar(30) NOT NULL DEFAULT '' COMMENT '收货地址',
  `phone` int(11) NOT NULL COMMENT '收货人手机号',
  `payment` int(11) NOT NULL COMMENT '实际支付金额',
  `payment_time` bigint(20) NOT NULL COMMENT '支付时间',
  `status` int(11) NOT NULL COMMENT '订单状态',
  `send_time` bigint(20) NOT NULL COMMENT '发货时间',
  `created_time` bigint(20) NOT NULL,
  `updated_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;