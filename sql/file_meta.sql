/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.125.127
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : 192.168.125.127:3306
 Source Schema         : default_db

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 05/03/2021 15:36:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for file_meta
-- ----------------------------
DROP TABLE IF EXISTS `file_meta`;
CREATE TABLE `file_meta`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '文件ID',
  `creator_id` bigint(0) NULL DEFAULT NULL COMMENT '创建者ID',
  `biz_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '业务code',
  `file_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件名称',
  `file_type` tinyint(0) NULL DEFAULT NULL COMMENT '文件类型（1-图片，2-文本，3-音频，4-视频，5-其他）',
  `file_desc` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '文件描述',
  `file_status` tinyint(0) NOT NULL DEFAULT 0 COMMENT '文件状态（1-使用中，2-已上链，3-上链失败，4-已删除）',
  `suffix` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件后缀',
  `size` bigint(0) UNSIGNED NOT NULL COMMENT '文件大小',
  `path` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件路径',
  `md_hash` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '文件MD5哈希值',
  `sha_hash` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '文件SHA256哈希值',
  `storage_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '存储类型（Local、FastDFS、HDFS）',
  `is_encrypt` tinyint(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否加密（0：否 1：是）',
  `abe_secret_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '属性加密密钥',
  `is_temp` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否临时（0：否 1：是）',
  `is_deleted` tinyint(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除（0：未删除 1：删除）',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文件元数据' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
