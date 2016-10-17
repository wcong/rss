DROP table if EXISTS `tb_channel`;
create table `tb_channel`(
  `id` int(11) PRIMARY KEY not null AUTO_INCREMENT,
  `create_time` TIMESTAMP not null,
  `update_time` TIMESTAMP not null,
  `channel` VARCHAR(100) not null COMMENT 'rss channel',
  `title` VARCHAR(255) not null COMMENT 'rss title',
  `description` VARCHAR(255) not null COMMENT 'rss description',
  UNIQUE KEY `channel`(`channel`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='channel table';

DROP table if EXISTS `tb_user`;
create table `tb_user`(
  `id` int(11) PRIMARY KEY not null AUTO_INCREMENT,
  `create_time` TIMESTAMP not null,
  `update_time` TIMESTAMP not null,
  `account` VARCHAR(100) not null COMMENT 'user account',
  `password` VARCHAR(255) not null COMMENT 'user password',
   UNIQUE KEY `account`(`account`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='user table';

DROP table if EXISTS `tb_rss`;
create table `tb_rss`(
  `id` int(11) PRIMARY KEY not null AUTO_INCREMENT,
  `create_time` TIMESTAMP not null,
  `update_time` TIMESTAMP not null,
  `channel_id`  int(11) not null COMMENT 'id of channel',
  `user_id`  int(11) not null COMMENT 'id of user',
   UNIQUE KEY `user_channel`(`channel_id`,`user_id`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='rss table';

DROP table if EXISTS `tb_item`;
create table `tb_item`(
  `id` int(11) PRIMARY KEY not null AUTO_INCREMENT,
  `create_time` TIMESTAMP not null,
  `update_time` TIMESTAMP not null,
  `channel_id` int(11) not null COMMENT 'id of channel',
  `title`  VARCHAR(255) not null COMMENT 'item title',
  `link`  VARCHAR(100) not null COMMENT 'item link',
  `description`  VARCHAR(255) not null COMMENT 'item description',
  UNIQUE KEY `user_link`(`channel_id`,`link`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='link table';