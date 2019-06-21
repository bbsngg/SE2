-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: 47.101.183.63    Database: cinema
-- ------------------------------------------------------
-- Server version	5.7.24

SET @@session.sql_mode ='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

--
-- Table structure for table `transaction_log`
--

DROP TABLE IF EXISTS `transaction_log`;

create table transaction_log(
  `id` int(10) not null AUTO_INCREMENT,
  `user_id` int(11) not null,
  `amount` double(10,2) not null,
  `method` VARCHAR(255) not null,
  `uses` VARCHAR(255) not null,
  `join_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY(`id`)
)engine=InnoDB AUTO_INCREMENT=8 default charset=utf8mb4;

-- Dump completed on 2019-06-05 21:20:52