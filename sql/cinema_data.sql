-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: 47.101.183.63    Database: cinema
-- ------------------------------------------------------
-- Server version	5.7.24

SET @@session.sql_mode ='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';

--
-- Table structure for table `vip_type`
-- id	name	target_amount目标价	discount_amount打折价	charge_amount到达升级金额
--

DROP TABLE IF EXISTS `vip_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vip_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `target_amount` float DEFAULT NULL,
  `discount_amount` float DEFAULT NULL,
  `charge_amount` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vip_type`
--

LOCK TABLES `vip_type` WRITE;
/*!40000 ALTER TABLE `vip_type` DISABLE KEYS */;
INSERT INTO `vip_type` VALUES (1,'普通卡',200,30,25),(2,'金卡',150,30,1000);
/*!40000 ALTER TABLE `vip_type` ENABLE KEYS */;
UNLOCK TABLES;

-- 为每个会员卡增加会员卡种类属性
ALTER TABLE `vip_card`
  ADD COLUMN `type` INT(11) NOT NULL DEFAULT '1'

-- 保存历史充值总额
ALTER TABLE `vip_card`
  ADD COLUMN `amount` FLOAT NOT NULL DEFAULT '0'



-- Dump completed on 2019-06-05 21:20:52