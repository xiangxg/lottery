CREATE TABLE `lottery` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `area` varchar(20) DEFAULT NULL COMMENT '地区',
  `name` varchar(20) DEFAULT NULL COMMENT '彩种',
  `lottery_num` varchar(200) DEFAULT NULL COMMENT '中奖号码',
  `day_periods` varchar(100) DEFAULT NULL COMMENT '日期数',
  `toal_periods` varchar(100) DEFAULT NULL COMMENT '总期数',
  `open_time` date DEFAULT NULL COMMENT '开奖日期',
  `prize_cache` varchar(20) DEFAULT NULL COMMENT '奖池缓存',
  PRIMARY KEY (`id`),
  UNIQUE KEY `area` (`area`,`name`,`toal_periods`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL COMMENT '标题',
  `type` tinyint(4) DEFAULT NULL COMMENT '类型 1 资讯 2预测',
  `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
  `info` text COMMENT '详情',
  PRIMARY KEY (`id`),
  UNIQUE KEY `a_title` (`title`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `set_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL COMMENT '名称',
  `is_start` tinyint(1) DEFAULT NULL COMMENT '是否启动 1启动 0关闭',
  `remark` varchar(2000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `area` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

