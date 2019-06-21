-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: 47.101.183.63    Database: cinema
-- ------------------------------------------------------
-- Server version	5.7.24

SET @@session.sql_mode ='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

--
-- Table structure for table `refund_strategy`
--

drop table if exists `refund_strategy`;

create table refund_strategy(
`id` int(11) not null auto_increment,
`start_time` double(10,1) not null,
`end_time` double(10,1) not null,
`percent` double(10,2) not null default 1,
primary key(id)
)engine=InnoDB default charset=utf8mb4;


-- Dump completed on 2019-06-05 21:20:52