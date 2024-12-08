/*
 Navicat MySQL Data Transfer

 Source Server         : huangchangjun
 Source Server Type    : MySQL
 Source Server Version : 50720 (5.7.20-log)
 Source Host           : localhost:3306
 Source Schema         : duplicate_db

 Target Server Type    : MySQL
 Target Server Version : 50720 (5.7.20-log)
 File Encoding         : 65001

 Date: 08/12/2024 16:15:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES (1, 'it', NULL, NULL);
INSERT INTO `department` VALUES (2, 'sale', NULL, NULL);
INSERT INTO `department` VALUES (5, 'seal', NULL, NULL);
INSERT INTO `department` VALUES (6, 'seal', NULL, NULL);
INSERT INTO `department` VALUES (7, 'seal', NULL, NULL);
INSERT INTO `department` VALUES (8, 'seal', NULL, NULL);
INSERT INTO `department` VALUES (9, 'seal', NULL, NULL);
INSERT INTO `department` VALUES (10, 'seal', '2023-04-10 19:28:47', '2023-04-10 19:28:47');
INSERT INTO `department` VALUES (11, 'seal', '2023-04-10 19:46:51', '2023-04-10 19:46:51');
INSERT INTO `department` VALUES (12, 'it', '2023-05-04 01:09:33', '2023-05-04 01:09:33');
INSERT INTO `department` VALUES (13, 'it', '2023-05-04 01:35:55', '2023-05-04 01:35:55');

SET FOREIGN_KEY_CHECKS = 1;
