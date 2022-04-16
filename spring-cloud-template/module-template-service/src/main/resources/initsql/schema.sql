
CREATE TABLE IF NOT EXISTS `wms_abc` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `name` varchar(32) DEFAULT NULL COMMENT '字段1',
  `sex` varchar(32) DEFAULT NULL COMMENT '字段2',
  `sex_abc` varchar(32) DEFAULT NULL COMMENT '字段3',
  `valid` smallint(6) DEFAULT '1' COMMENT '有效小计(1:有效，0:无效)',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_Time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `editor` varchar(32) DEFAULT NULL COMMENT '修改者',
  `edit_Time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `roleId` (`name`,`sex`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='前缀_表名称';


CREATE TABLE IF NOT EXISTS `test_module_manage` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `module_name` varchar(32) DEFAULT NULL COMMENT '程序包名称',
  `module_code` varchar(32) DEFAULT NULL COMMENT '服务code',
  `descr` varchar(200) DEFAULT NULL COMMENT '模块描述',
  `module_type` smallint(6) DEFAULT NULL COMMENT '模块类型（1后台服务，2前端web）',
  `valid` smallint(6) DEFAULT '0' COMMENT '发布状态（1已发布，2已下架）',
  `creator_id` varchar(32) DEFAULT NULL COMMENT '应用程序发布者创建者',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `editor_id` varchar(32) DEFAULT NULL COMMENT '修改者',
  `editor_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='程序包模块表';


commit;