/*
Navicat MySQL Data Transfer

Source Server         : localhost(root)
Source Server Version : 50633
Source Host           : 192.168.0.199:3306
Source Database       : smartdb_dev

Target Server Type    : MYSQL
Target Server Version : 50633
File Encoding         : 65001

Date: 2017-03-15 11:34:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for checkproject
-- ----------------------------
DROP TABLE IF EXISTS `checkproject`;
CREATE TABLE `checkproject` (
  `checkProjectId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `checkContent` varchar(200) NOT NULL COMMENT '检查项目明细',
  `patrolTaskDetailId` bigint(20) DEFAULT NULL COMMENT '关联巡检详情',
  `checkResult` varchar(20) DEFAULT NULL COMMENT '检查结果：合格、不合格',
  PRIMARY KEY (`checkProjectId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='检查项目';

-- ----------------------------
-- Records of checkproject
-- ----------------------------

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `deptId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `deptName` varchar(50) NOT NULL COMMENT '部门名称',
  `deptCode` varchar(50) NOT NULL COMMENT '部门编号',
  PRIMARY KEY (`deptId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门';

-- ----------------------------
-- Records of department
-- ----------------------------

-- ----------------------------
-- Table structure for device
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device` (
  `deviceId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `deviceNum` varchar(30) NOT NULL COMMENT '资产号',
  `deviceName` varchar(30) DEFAULT NULL COMMENT '设备名称',
  `qrcode` varchar(128) DEFAULT NULL COMMENT '二维码',
  `serialnum` varchar(64) DEFAULT NULL COMMENT '资产序列号',
  `ipaddress` varchar(30) DEFAULT NULL COMMENT '设备IP',
  `assettag` varchar(64) DEFAULT NULL COMMENT '资产标记编号',
  `location` varchar(12) DEFAULT NULL COMMENT '资产位置',
  `description` varchar(100) DEFAULT NULL COMMENT '描述资产。要输入或查看附加信息，请单击“详细描述”按钮。',
  `vendor` varchar(12) DEFAULT NULL COMMENT '制造商 － 供应商代码',
  `manufacturer` varchar(12) DEFAULT NULL COMMENT '制造商编号',
  `installdate` datetime DEFAULT NULL COMMENT '安装日期',
  `warrantyexpdate` datetime DEFAULT NULL COMMENT '保修期截止日期',
  `statusdate` datetime DEFAULT NULL COMMENT '资产状态日期',
  `changedate` datetime NOT NULL COMMENT '最后修改日期',
  `changeby` varchar(30) NOT NULL COMMENT '上次修改人员',
  `priority` int(11) DEFAULT NULL COMMENT '资产优先级 － 输入时复制到工单',
  `assettype` varchar(15) DEFAULT NULL COMMENT '该资产的预定义类型。',
  `usaged` varchar(15) DEFAULT NULL COMMENT '使用情况',
  `devicestatus` varchar(20) DEFAULT NULL COMMENT '资产的状态,',
  `pluscmodelnum` varchar(8) DEFAULT NULL COMMENT '输入工具的型号（如果适用）。可从资产 (CAL) 应用程序填充此字段。',
  `pluscpmextdate` bit(1) NOT NULL DEFAULT b'0' COMMENT '如果关联的 PM 的日期已延长，将选中该复选框。',
  PRIMARY KEY (`deviceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备信息表';

-- ----------------------------
-- Records of device
-- ----------------------------

-- ----------------------------
-- Table structure for inventorydetail
-- ----------------------------
DROP TABLE IF EXISTS `inventorydetail`;
CREATE TABLE `inventorydetail` (
  `inventoryId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '盘点单ID',
  `inventoryDevice` varchar(50) NOT NULL COMMENT '盘点设备',
  `Column_3` bigint(20) NOT NULL COMMENT '所属盘点单',
  `inventoryData` int(11) NOT NULL COMMENT '盘点设备数量',
  `dataStatus` varchar(20) NOT NULL COMMENT '盘点设备数量状态：正常、异常',
  PRIMARY KEY (`inventoryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备盘点详情';

-- ----------------------------
-- Records of inventorydetail
-- ----------------------------

-- ----------------------------
-- Table structure for inventorydevice
-- ----------------------------
DROP TABLE IF EXISTS `inventorydevice`;
CREATE TABLE `inventorydevice` (
  `inventoryId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '盘点单ID',
  `inventoryName` varchar(50) NOT NULL COMMENT '盘点单名称',
  `inventoryCode` varchar(50) NOT NULL COMMENT '盘点单编号',
  `inventoryDate` datetime NOT NULL COMMENT '盘点日期',
  `inventoryStatus` varchar(20) NOT NULL COMMENT '盘点状态：未开始、异常、已完成',
  PRIMARY KEY (`inventoryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备盘点';

-- ----------------------------
-- Records of inventorydevice
-- ----------------------------

-- ----------------------------
-- Table structure for knowledgerep
-- ----------------------------
DROP TABLE IF EXISTS `knowledgerep`;
CREATE TABLE `knowledgerep` (
  `knowledgeRepId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `type` varchar(50) NOT NULL COMMENT '知识库分类：设备保养、设备维修',
  `content` varchar(500) NOT NULL COMMENT '知识库内容',
  `createDate` datetime NOT NULL COMMENT '知识库创建日期',
  `deviceId` varchar(50) DEFAULT NULL COMMENT '知识相关设备，关联设备表',
  `createUser` varchar(50) DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`knowledgeRepId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='知识库';

-- ----------------------------
-- Records of knowledgerep
-- ----------------------------

-- ----------------------------
-- Table structure for loginlog
-- ----------------------------
DROP TABLE IF EXISTS `loginlog`;
CREATE TABLE `loginlog` (
  `loginlogid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `userid` varchar(30) DEFAULT NULL COMMENT '用户标识',
  `name` varchar(62) DEFAULT NULL COMMENT '用户全名',
  `attemptdate` datetime NOT NULL COMMENT '尝试登录时的时间戳记',
  `attemptresult` varchar(15) NOT NULL COMMENT '登录尝试的结果。有效值为“成功”、“失败”、“注销”或“超时”。',
  `adminuserid` varchar(30) DEFAULT NULL COMMENT '当管理用户使另外一位用户注销时，这是管理用户的用户标识。',
  `sessionuid` varchar(36) DEFAULT NULL COMMENT '与该活动相关联的会话标识。',
  `loginid` varchar(50) DEFAULT NULL COMMENT '对成功或不成功的验证尝试所输入的登录标识。',
  `clienthost` varchar(100) DEFAULT NULL COMMENT '用户客户端（远程主机）的主机名。',
  `currentcount` int(11) DEFAULT NULL COMMENT '截止此尝试日期的活动会话的计数。该计数包括系统中的所有会话，而不仅仅是此用户标识的会话。',
  `clientaddr` varchar(100) DEFAULT NULL COMMENT '用户客户端的 IP 地址（远程地址）。',
  `servername` varchar(100) DEFAULT NULL COMMENT 'SERVER 名称',
  `serverhost` varchar(100) DEFAULT NULL COMMENT '该会话的 SERVER 的主机名或 IP 地址',
  `userType` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`loginlogid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='用户登陆日志';


-- ----------------------------
-- Table structure for maintainhis
-- ----------------------------
DROP TABLE IF EXISTS `maintainhis`;
CREATE TABLE `maintainhis` (
  `maintainHisId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `maintainResult` varchar(100) NOT NULL COMMENT '维修总结',
  `maintainContent` varchar(200) NOT NULL COMMENT '维修内容',
  `maintainDate` datetime NOT NULL COMMENT '维修日期',
  `maintainProbelm` varchar(100) DEFAULT NULL COMMENT '故障说明',
  `voiceUrl` varchar(50) DEFAULT NULL COMMENT '录音辅助',
  `imgUrl` varchar(50) DEFAULT NULL COMMENT '设备照片',
  `location` varchar(50) DEFAULT NULL COMMENT '位置',
  `maintainType` varchar(20) NOT NULL COMMENT '维护类型：设备维护、故障报修',
  `deviceNum` varchar(50) NOT NULL COMMENT '设备编号',
  PRIMARY KEY (`maintainHisId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备维护记录';

-- ----------------------------
-- Records of maintainhis
-- ----------------------------

-- ----------------------------
-- Table structure for patrolhis
-- ----------------------------
DROP TABLE IF EXISTS `patrolhis`;
CREATE TABLE `patrolhis` (
  `patrolHisId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `patrolDevice` varchar(50) NOT NULL COMMENT '巡检设备',
  `deviceStatus` varchar(20) NOT NULL COMMENT '设备状态',
  `patrolTime` datetime NOT NULL COMMENT '巡检时间',
  `patrolHisDetail` bigint(20) DEFAULT NULL COMMENT '巡检记录详情，关联详情表',
  PRIMARY KEY (`patrolHisId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='巡检记录';

-- ----------------------------
-- Records of patrolhis
-- ----------------------------

-- ----------------------------
-- Table structure for patrolhisdetail
-- ----------------------------
DROP TABLE IF EXISTS `patrolhisdetail`;
CREATE TABLE `patrolhisdetail` (
  `patrolHisDetailId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `patrolSummary` varchar(100) NOT NULL COMMENT '巡检总结',
  `patrolContent` varchar(200) DEFAULT NULL COMMENT '巡检内容',
  `patrolDate` datetime NOT NULL COMMENT '巡检日期',
  `deviceStatus` varchar(50) NOT NULL COMMENT '设备状态',
  `maintainExplain` varchar(200) DEFAULT NULL COMMENT '维护情况说明',
  `voiceUrl` varchar(200) DEFAULT NULL COMMENT '录音辅助说明',
  `imgUrl` varchar(200) DEFAULT NULL COMMENT '现场图片',
  PRIMARY KEY (`patrolHisDetailId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='巡检记录详情';

-- ----------------------------
-- Records of patrolhisdetail
-- ----------------------------

-- ----------------------------
-- Table structure for patroltask
-- ----------------------------
DROP TABLE IF EXISTS `patroltask`;
CREATE TABLE `patroltask` (
  `patrolTaskId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `patrolName` varchar(100) NOT NULL COMMENT '巡检名称',
  `patrolDate` datetime NOT NULL COMMENT '巡视日期',
  `patrolUser` varchar(32) NOT NULL COMMENT '巡检员，关联用户',
  `PatrolTaskDetail` varchar(32) NOT NULL COMMENT '巡检详情，关联巡检详情ID',
  `PatrolStatus` varchar(20) NOT NULL COMMENT '状态：未完成、进行中、漏检',
  PRIMARY KEY (`patrolTaskId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='巡检任务';

-- ----------------------------
-- Records of patroltask
-- ----------------------------

-- ----------------------------
-- Table structure for patroltaskdetail
-- ----------------------------
DROP TABLE IF EXISTS `patroltaskdetail`;
CREATE TABLE `patroltaskdetail` (
  `patrolTaskDetailId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `deviceName` varchar(50) NOT NULL COMMENT '设备名称',
  `deviceCode` varchar(50) NOT NULL COMMENT '设备编号',
  `deviceLocation` varchar(100) DEFAULT NULL COMMENT '所在位置',
  `installTime` datetime DEFAULT NULL COMMENT '安装时间',
  `lastPatrolTime` datetime DEFAULT NULL COMMENT '最新检验时间',
  `taskStatus` varchar(20) NOT NULL COMMENT '详情巡检状态：已巡检、未巡检',
  `checkProjectId` bigint(20) DEFAULT NULL COMMENT '检查项目，与检查项目表关联',
  PRIMARY KEY (`patrolTaskDetailId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='巡检详情';

-- ----------------------------
-- Records of patroltaskdetail
-- ----------------------------

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `permissionid` bigint(20) NOT NULL AUTO_INCREMENT,
  `permissionCode` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `roleCode` varchar(255) DEFAULT NULL,
  `parent` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`permissionid`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `roleid` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(255) DEFAULT NULL,
  `roleCode` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `userType` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`roleid`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------

-- ----------------------------
-- Table structure for sysinfo
-- ----------------------------
DROP TABLE IF EXISTS `sysinfo`;
CREATE TABLE `sysinfo` (
  `orgid` bigint(20) NOT NULL COMMENT '唯一标识',
  `sysname` varchar(20) DEFAULT NULL COMMENT '系统名称',
  `title` varchar(100) DEFAULT NULL COMMENT '显示标题',
  `longtitle` varchar(200) DEFAULT NULL COMMENT '长标题',
  `description` varchar(200) DEFAULT NULL COMMENT '系统描述',
  `shortcut` varchar(200) DEFAULT NULL COMMENT '显示图标',
  `homeurl` varchar(200) DEFAULT NULL COMMENT '主页URL',
  `logourl` varchar(200) DEFAULT NULL COMMENT 'LOGO地址',
  `copyright` varchar(200) DEFAULT NULL COMMENT '版权信息',
  `icp` varchar(100) DEFAULT NULL COMMENT '备案信息',
  `master` varchar(50) DEFAULT NULL COMMENT '管理员姓名',
  `masteremail` varchar(100) DEFAULT NULL COMMENT '管理员EMAIL'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统信息';

-- ----------------------------
-- Records of sysinfo
-- ----------------------------

-- ----------------------------
-- Table structure for sysuser
-- ----------------------------
DROP TABLE IF EXISTS `sysuser`;
CREATE TABLE `sysuser` (
  `sysuserid` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `userid` varchar(32) NOT NULL,
  `truename` varchar(30) NOT NULL COMMENT '真实姓名',
  `deptid` varchar(20) DEFAULT NULL,
  `status` varchar(12) NOT NULL,
  `type` varchar(30) NOT NULL,
  `pwexpiration` datetime DEFAULT NULL,
  `failedlogins` int(11) NOT NULL DEFAULT '0',
  `loginid` varchar(50) NOT NULL,
  `password` varchar(128) NOT NULL,
  `salt` varchar(128) NOT NULL,
  `passwordold` varchar(35) DEFAULT NULL,
  `saltold` varchar(35) DEFAULT NULL,
  `sysuser` bit(1) NOT NULL,
  `createdate` datetime DEFAULT NULL,
  `title` varchar(30) DEFAULT NULL,
  `employeetype` varchar(10) DEFAULT NULL,
  `jobcode` varchar(10) DEFAULT NULL,
  `supervisor` varchar(30) DEFAULT NULL,
  `birthdate` datetime DEFAULT NULL,
  `hiredate` datetime DEFAULT NULL,
  `terminationdate` datetime DEFAULT NULL,
  `city` varchar(36) DEFAULT NULL,
  `regiondistrict` varchar(36) DEFAULT NULL,
  `postalcode` varchar(12) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `lastevaldate` datetime DEFAULT NULL,
  `imgUrl` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`sysuserid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Records of sysuser
-- ----------------------------

-- ----------------------------
-- Table structure for workchange
-- ----------------------------
DROP TABLE IF EXISTS `workchange`;
CREATE TABLE `workchange` (
  `workChangeId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `currentUser` varchar(50) NOT NULL COMMENT '交接人',
  `changeTo` varchar(50) NOT NULL COMMENT '被交接人',
  `currentShift` varchar(20) NOT NULL COMMENT '交接班次',
  `changeShiftTo` varchar(20) NOT NULL COMMENT '被交接班次',
  `workContent` varchar(200) DEFAULT NULL COMMENT '工作内容',
  `changeWork` varchar(200) DEFAULT NULL COMMENT '交接事项',
  `leaderWork` varchar(200) DEFAULT NULL COMMENT '领导交办',
  `changeTime` datetime DEFAULT NULL COMMENT '交接时间',
  PRIMARY KEY (`workChangeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交接班';

-- ----------------------------
-- Records of workchange
-- ----------------------------

-- ----------------------------
-- Table structure for workchangehis
-- ----------------------------
DROP TABLE IF EXISTS `workchangehis`;
CREATE TABLE `workchangehis` (
  `workChangeId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `currentUser` varchar(50) NOT NULL COMMENT '交接人',
  `changeTo` varchar(50) NOT NULL COMMENT '被交接人',
  `currentShift` varchar(20) NOT NULL COMMENT '交接班次',
  `changeShiftTo` varchar(20) NOT NULL COMMENT '被交接班次',
  `workContent` varchar(200) DEFAULT NULL COMMENT '工作内容',
  `changeWork` varchar(200) DEFAULT NULL COMMENT '交接事项',
  `leaderWork` varchar(200) DEFAULT NULL COMMENT '领导交办',
  `changeTime` datetime DEFAULT NULL COMMENT '交接时间',
  PRIMARY KEY (`workChangeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交接班历史';

-- ----------------------------
-- Records of workchangehis
-- ----------------------------

-- ----------------------------
-- Table structure for workorder
-- ----------------------------
DROP TABLE IF EXISTS `workorder`;
CREATE TABLE `workorder` (
  `workOrderId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `workOrderContent` varchar(100) NOT NULL COMMENT '工单描述',
  `workOrderStatus` varchar(20) NOT NULL COMMENT '状态',
  `deviceLocation` varchar(50) NOT NULL COMMENT '所在位置',
  `deviceName` varchar(50) NOT NULL COMMENT '设备名称',
  `deviceCode` varchar(50) NOT NULL COMMENT '设备编号',
  `workOrderType` varchar(50) NOT NULL COMMENT '工单类型：紧急维修、普通维修',
  `priority` int(11) NOT NULL COMMENT '优先级',
  `createUser` varchar(50) NOT NULL COMMENT '报告人',
  `createTime` datetime NOT NULL COMMENT '报告时间',
  `maintainUser` varchar(50) NOT NULL COMMENT '指派维修人',
  `maintainTel` varchar(20) NOT NULL COMMENT '维修人电话',
  `leader` varchar(50) NOT NULL COMMENT '领导',
  `workPlan` varchar(50) DEFAULT NULL COMMENT '工作计划',
  `workOrderTask` bigint(20) DEFAULT NULL COMMENT '工单任务ID，关联任务ID',
  `workOrderActual` varchar(200) DEFAULT NULL COMMENT '工单实际情况',
  `enclosure` varchar(200) DEFAULT NULL COMMENT '工单附件',
  PRIMARY KEY (`workOrderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工单';

-- ----------------------------
-- Records of workorder
-- ----------------------------

-- ----------------------------
-- Table structure for workordertask
-- ----------------------------
DROP TABLE IF EXISTS `workordertask`;
CREATE TABLE `workordertask` (
  `workOrderTaskId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `TaskTimeCount` varchar(20) DEFAULT NULL COMMENT '任务持续时间',
  `TaskContent` varchar(100) NOT NULL COMMENT '任务内容',
  PRIMARY KEY (`workOrderTaskId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工单任务';

-- ----------------------------
-- Records of workordertask
-- ----------------------------
