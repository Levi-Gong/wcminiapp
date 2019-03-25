/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50611
Source Host           : localhost:3306
Source Database       : qc

Target Server Type    : MYSQL
Target Server Version : 50611
File Encoding         : 65001

Date: 2019-03-25 13:53:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for qc_role
-- ----------------------------
DROP TABLE IF EXISTS `qc_role`;
CREATE TABLE `qc_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键 自动增长',
  `role_name` varchar(255) NOT NULL COMMENT '角色名',
  `superior_role_id` int(11) DEFAULT NULL COMMENT '上级角色',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qc_user
-- ----------------------------
DROP TABLE IF EXISTS `qc_user`;
CREATE TABLE `qc_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键 自动递增',
  `nick_name` varchar(32) DEFAULT NULL COMMENT '昵称',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `actual_name` varchar(32) DEFAULT NULL COMMENT '真实名字',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机',
  `open_id` varchar(64) DEFAULT NULL COMMENT '微信用户ID',
  `session_key` varchar(128) DEFAULT NULL COMMENT '微信会话密钥',
  `referrer` int(11) DEFAULT NULL COMMENT '推荐人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `level` varchar(32) DEFAULT NULL COMMENT '代理等级',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `other_refrence` varchar(255) DEFAULT NULL COMMENT '备用字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qc_user_role
-- ----------------------------
DROP TABLE IF EXISTS `qc_user_role`;
CREATE TABLE `qc_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL COMMENT 'userId',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modity_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
