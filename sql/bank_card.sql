-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: 47.101.183.63    Database: cinema
-- ------------------------------------------------------
-- Server version	5.7.24

SET @@session.sql_mode ='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

--
-- Table structure for table `bank_card`
--

drop table if exists `bank_card`;
create table bank_card(
  `id` int(11) not null auto_increment,
  `account_number` int(11) not null,
  `password` int(11) not null,
  `balance` double(10,2) not null default 0,
  primary key(id)
)engine=InnoDB default charset=utf8mb4;


LOCK TABLES `bank_card` WRITE;
/*!40000 ALTER TABLE `bank_card` DISABLE KEYS */;
INSERT INTO `bank_card` VALUES (1, 1, 111, 1000);
/*!40000 ALTER TABLE `bank_card` ENABLE KEYS */;
UNLOCK TABLES;

-- Dump completed on 2019-06-05 21:20:52