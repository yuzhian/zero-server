/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : 127.0.0.1:3306
 Source Schema         : zero_server

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 01/11/2020 23:51:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_account
-- ----------------------------
DROP TABLE IF EXISTS `sys_account`;
CREATE TABLE `sys_account`
(
    `user_id`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '用户主键',
    `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '用户名',
    `email`    varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '邮箱',
    `phone`    varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '手机号',
    `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '密码',
    `salt`     varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '密码盐',
    `del_flag` tinyint(1)                                              NULL DEFAULT 0 COMMENT '删除标记(0正常 1删除)',
    PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '帐号'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_account
-- ----------------------------
INSERT INTO `sys_account`
VALUES ('0', 'string', 'string@me.com', '11111111111', '6c3b266b4ac00e1dce3dd65fd0a757d0',
        '6183bc0cb501bc4ee9160686659149fe', 0);

-- ----------------------------
-- Table structure for sys_account_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_account_role`;
CREATE TABLE `sys_account_role`
(
    `id`      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户角色主键',
    `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户主键',
    `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色主键',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '帐号-角色'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_account_role
-- ----------------------------
INSERT INTO `sys_account_role`
VALUES ('', '0', '0');

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`
(
    `permission_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '权限主键',
    `parent_id`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '父权限主键',
    `permission`    varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '权限名称',
    `description`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '权限备注',
    `del_flag`      tinyint(1)                                              NULL DEFAULT 0 COMMENT '删除标记(0正常 1删除)',
    PRIMARY KEY (`permission_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '权限'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission`
VALUES ('11', '', 'test:test:test1', '测试权限1', 0);
INSERT INTO `sys_permission`
VALUES ('12', '', 'test:test:test2', '测试权限2', 0);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `role_id`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '角色主键',
    `role`        varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '角色名称',
    `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '角色介绍',
    `del_flag`    tinyint(1)                                              NULL DEFAULT 0 COMMENT '删除标记(0正常 1删除)',
    PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '角色'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role`
VALUES ('0', '系统管理员', '系统管理员', 0);

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`
(
    `id`            varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色权限主键',
    `role_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色主键',
    `permission_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限主键',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '角色-权限'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission`
VALUES ('', '0', '11');

SET FOREIGN_KEY_CHECKS = 1;
