-- 菜单
CREATE TABLE `sys_menu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `pid` int NOT NULL DEFAULT 0 COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) COMMENT '菜单名称',
  `url` varchar(200) COMMENT '菜单URL',
  `perms` varchar(256) COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `icon` varchar(128) COMMENT '菜单图标',
  `order` int COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单管理';


-- 系统用户
CREATE TABLE `sys_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) COMMENT '密码',
  `salt` varchar(32) COMMENT '盐',
  `email` varchar(100) COMMENT '邮箱',
  `status` tinyint DEFAULT 1 COMMENT '状态  0：禁用   1：正常',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- 角色
CREATE TABLE `sys_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COMMENT '角色名称',
  `remark` varchar(100) COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色';

-- 用户与角色对应关系
CREATE TABLE `sys_user_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT '用户ID',
  `role_id` int NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

-- 角色与菜单对应关系
CREATE TABLE `sys_role_menu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_id` int NOT NULL COMMENT '角色ID',
  `menu_id` int NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';



-- 系统日志
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COMMENT '用户名',
  `operation` varchar(50) COMMENT '用户操作',
  `method` varchar(200) COMMENT '请求方法',
  `params` varchar(500) COMMENT '请求参数',
  `time` bigint NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) COMMENT 'IP地址',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=`InnoDB` DEFAULT CHARSET=utf8 COMMENT='系统日志';



-- 初始数据
INSERT INTO `sys_user` (`id`, `username`, `password`, `salt`, `email`, `status`,`create_time`) VALUES ('1', 'admin', 'e1153123d7d180ceeb820d577ff119876678732a68eef4e6ffc0b1f06a01f91b', 'YzcmCZNvbXocrsz9dm8e', 'root@renren.io', '13612345678', '1', '1', '2016-11-11 11:11:11');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perms`, `type`, `icon`, `order`) VALUES ('1', '0', '系统管理', NULL, NULL, '0', 'fa fa-cog', '0');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perms`, `type`, `icon`, `order`) VALUES ('2', '1', '管理员管理', 'modules/sys/user', NULL, '1', 'fa fa-user', '1');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perms`, `type`, `icon`, `order`) VALUES ('3', '1', '角色管理', 'modules/sys/role', NULL, '1', 'fa fa-user-secret', '2');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perms`, `type`, `icon`, `order`) VALUES ('4', '1', '菜单管理', 'modules/sys/menu', NULL, '1', 'fa fa-th-list', '3');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perms`, `type`, `icon`, `order`) VALUES ('5', '1', 'SQL监控', 'druid/sql.html', NULL, '1', 'fa fa-bug', '4');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perms`, `type`, `icon`, `order`) VALUES ('15', '2', '查看', NULL, 'sys:user:list,sys:user:info', '2', NULL, '0');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perms`, `type`, `icon`, `order`) VALUES ('16', '2', '新增', NULL, 'sys:user:save,sys:role:select', '2', NULL, '0');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perms`, `type`, `icon`, `order`) VALUES ('17', '2', '修改', NULL, 'sys:user:update,sys:role:select', '2', NULL, '0');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perms`, `type`, `icon`, `order`) VALUES ('18', '2', '删除', NULL, 'sys:user:delete', '2', NULL, '0');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perms`, `type`, `icon`, `order`) VALUES ('19', '3', '查看', NULL, 'sys:role:list,sys:role:info', '2', NULL, '0');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perms`, `type`, `icon`, `order`) VALUES ('20', '3', '新增', NULL, 'sys:role:save,sys:menu:perms', '2', NULL, '0');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perms`, `type`, `icon`, `order`) VALUES ('21', '3', '修改', NULL, 'sys:role:update,sys:menu:perms', '2', NULL, '0');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perms`, `type`, `icon`, `order`) VALUES ('22', '3', '删除', NULL, 'sys:role:delete', '2', NULL, '0');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perms`, `type`, `icon`, `order`) VALUES ('23', '4', '查看', NULL, 'sys:menu:list,sys:menu:info', '2', NULL, '0');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perms`, `type`, `icon`, `order`) VALUES ('24', '4', '新增', NULL, 'sys:menu:save,sys:menu:select', '2', NULL, '0');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perms`, `type`, `icon`, `order`) VALUES ('25', '4', '修改', NULL, 'sys:menu:update,sys:menu:select', '2', NULL, '0');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perms`, `type`, `icon`, `order`) VALUES ('26', '4', '删除', NULL, 'sys:menu:delete', '2', NULL, '0');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perms`, `type`, `icon`, `order`) VALUES ('29', '1', '系统日志', 'modules/sys/log', 'sys:log:list', '1', 'fa fa-file-text-o', '7');


INSERT INTO `sys_menu`(`id`, `pid`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (36, 1, '字典管理', 'modules/sys/dict.html', NULL, 1, 'fa fa-bookmark-o', 6);
INSERT INTO `sys_menu`(`id`, `pid`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (37, 36, '查看', NULL, 'sys:dict:list,sys:dict:info', 2, NULL, 6);
INSERT INTO `sys_menu`(`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (38, 36, '新增', NULL, 'sys:dict:save', 2, NULL, 6);
INSERT INTO `sys_menu`(`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (39, 36, '修改', NULL, 'sys:dict:update', 2, NULL, 6);
INSERT INTO `sys_menu`(`id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (40, 36, '删除', NULL, 'sys:dict:delete', 2, NULL, 6);



