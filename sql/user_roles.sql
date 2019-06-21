-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: 47.101.183.63    Database: cinema
-- ------------------------------------------------------
-- Server version	5.7.24

SET @@session.sql_mode ='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

--
-- Table structure for table `user_roles`
--

drop table if exists `user_roles`;
create table user_roles(
  `id` int not null auto_increment,
  `username` varchar(50) not null,
  `role_name` varchar(50) not null,
  primary key(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1, "001", "用户");
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

-- Dump completed on 2019-06-05 21:20:52