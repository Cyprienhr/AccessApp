CREATE DATABASE  IF NOT EXISTS `access_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `access_db`;
-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: access_db
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `audit_logs`
--

DROP TABLE IF EXISTS `audit_logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audit_logs` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `action` varchar(50) NOT NULL,
  `client_id` bigint DEFAULT NULL,
  `details` varchar(500) DEFAULT NULL,
  `entity_id` bigint DEFAULT NULL,
  `entity_type` varchar(100) DEFAULT NULL,
  `ip_address` varchar(255) DEFAULT NULL,
  `timestamp` datetime(6) DEFAULT NULL,
  `user_agent` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit_logs`
--

LOCK TABLES `audit_logs` WRITE;
/*!40000 ALTER TABLE `audit_logs` DISABLE KEYS */;
INSERT INTO `audit_logs` (`id`, `action`, `client_id`, `details`, `entity_id`, `entity_type`, `ip_address`, `timestamp`, `user_agent`, `user_id`, `username`) VALUES (1,'LOGIN',1,'User logged in',NULL,NULL,NULL,'2025-05-29 09:03:45.617961','PostmanRuntime/7.44.0',1,'Cyprienhr'),(2,'LOGIN',1,'User logged in',NULL,NULL,NULL,'2025-05-29 09:08:51.969383','PostmanRuntime/7.44.0',1,'Cyprienhr'),(3,'REGISTER',1,'New user registered: Hagena',NULL,NULL,NULL,'2025-05-29 09:25:31.368099','PostmanRuntime/7.44.0',2,'Hagena'),(4,'REGISTER',1,'New user registered: Cyprien',NULL,NULL,NULL,'2025-05-29 10:07:41.499360','PostmanRuntime/7.44.0',3,'Cyprien'),(5,'LOGIN',1,'User logged in',NULL,NULL,NULL,'2025-05-29 10:09:06.143077','PostmanRuntime/7.44.0',3,'Cyprien'),(6,'REGISTER',1,'New user registered: Pie',NULL,NULL,NULL,'2025-05-29 10:23:57.353607','PostmanRuntime/7.44.0',4,'Pie'),(7,'LOGIN',1,'User logged in',NULL,NULL,NULL,'2025-05-29 10:25:29.330832','PostmanRuntime/7.44.0',4,'Pie'),(8,'REGISTER',1,'New user registered: Paul',NULL,NULL,NULL,'2025-05-29 10:28:44.912080','PostmanRuntime/7.44.0',5,'Paul'),(9,'LOGIN',1,'User logged in',NULL,NULL,NULL,'2025-05-29 10:48:21.661796','PostmanRuntime/7.44.0',4,'Pie'),(10,'LOGIN',1,'User logged in',NULL,NULL,NULL,'2025-05-29 12:01:12.650073','PostmanRuntime/7.44.0',1,'Cyprienhr'),(11,'LOGOUT',1,'User logged out',NULL,NULL,NULL,'2025-05-29 12:01:37.646998','PostmanRuntime/7.44.0',1,'Cyprienhr'),(12,'LOGIN',1,'User logged in',NULL,NULL,NULL,'2025-05-29 12:41:12.597809','PostmanRuntime/7.44.0',1,'Cyprienhr'),(13,'LOGIN_FAILED',1,'Failed login attempt: User is disabled',NULL,NULL,NULL,'2025-05-29 12:59:36.207522','PostmanRuntime/7.44.0',NULL,'Paul'),(14,'LOGIN_FAILED',1,'Failed login attempt: User is disabled',NULL,NULL,NULL,'2025-05-29 13:00:16.130372','PostmanRuntime/7.44.0',NULL,'Cyprienhr'),(15,'LOGIN',1,'User logged in',NULL,NULL,NULL,'2025-05-29 13:05:10.589405','PostmanRuntime/7.44.0',1,'Cyprienhr'),(16,'LOGIN',1,'User logged in',NULL,NULL,NULL,'2025-05-29 13:30:09.583689','PostmanRuntime/7.44.0',1,'Cyprienhr'),(17,'LOGIN',1,'User logged in',NULL,NULL,NULL,'2025-05-29 13:30:41.982963','PostmanRuntime/7.44.0',1,'Cyprienhr'),(18,'LOGIN',1,'User logged in',NULL,NULL,NULL,'2025-05-29 13:32:26.731474','PostmanRuntime/7.44.0',5,'Paul'),(19,'LOGOUT',1,'User logged out',NULL,NULL,NULL,'2025-05-29 13:32:58.905378','PostmanRuntime/7.44.0',1,'Cyprienhr'),(20,'LOGOUT',1,'User logged out',NULL,NULL,'0:0:0:0:0:0:0:1','2025-05-29 06:09:16.391186','PostmanRuntime/7.44.0',1,'Cyprienhr'),(21,'LOGIN',1,'User logged in',NULL,NULL,'0:0:0:0:0:0:0:1','2025-05-29 06:11:24.933593','PostmanRuntime/7.44.0',5,'Paul'),(22,'LOGIN',1,'User logged in',NULL,NULL,'0:0:0:0:0:0:0:1','2025-05-29 06:38:09.298705','PostmanRuntime/7.44.0',5,'Paul'),(23,'LOGOUT',1,'User logged out',NULL,NULL,'0:0:0:0:0:0:0:1','2025-05-29 06:38:20.548830','PostmanRuntime/7.44.0',1,'Cyprienhr');
/*!40000 ALTER TABLE `audit_logs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clients`
--

DROP TABLE IF EXISTS `clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clients` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `api_key` varchar(100) NOT NULL,
  `client_id` varchar(100) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK8e3pw9lj92a9u9ktpxc0ebsu6` (`api_key`),
  UNIQUE KEY `UK2og8x0i6lngghy4cqupje9dki` (`client_id`),
  UNIQUE KEY `UKk49t374y88hquc0xxwg5iudfv` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients`
--

LOCK TABLES `clients` WRITE;
/*!40000 ALTER TABLE `clients` DISABLE KEYS */;
INSERT INTO `clients` (`id`, `active`, `api_key`, `client_id`, `created_at`, `description`, `name`, `updated_at`) VALUES (1,_binary '','comza','comza','2025-05-29 10:20:45.705388','Software Campany','ComzAfrica','2025-05-29 10:20:45.705388');
/*!40000 ALTER TABLE `clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permissions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `client_id` bigint DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `is_predefined` bit(1) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKpnvtwliis6p05pn6i3ndjrqt2` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissions`
--

LOCK TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refresh_tokens`
--

DROP TABLE IF EXISTS `refresh_tokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refresh_tokens` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `client_id` bigint DEFAULT NULL,
  `expiry_date` datetime(6) NOT NULL,
  `token` varchar(255) NOT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKghpmfn23vmxfu3spu3lfg4r2d` (`token`),
  UNIQUE KEY `UK7tdcd6ab5wsgoudnvj7xf1b7l` (`user_id`),
  CONSTRAINT `FK1lih5y2npsf8u5o3vhdb9y0os` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refresh_tokens`
--

LOCK TABLES `refresh_tokens` WRITE;
/*!40000 ALTER TABLE `refresh_tokens` DISABLE KEYS */;
INSERT INTO `refresh_tokens` (`id`, `client_id`, `expiry_date`, `token`, `user_id`) VALUES (3,1,'2025-06-05 17:09:06.130835','4ea9e7b8-0838-4a88-805d-a52d18787d2e',3),(5,1,'2025-06-05 17:48:21.647496','895a05f7-a41a-45d2-9c1f-0381ad0fdfbd',4),(13,1,'2025-06-05 13:38:09.262647','f60c835b-a2f7-45e0-b20b-59a103aa7c40',5);
/*!40000 ALTER TABLE `refresh_tokens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permissions`
--

DROP TABLE IF EXISTS `role_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_permissions` (
  `assigned_at` datetime(6) DEFAULT NULL,
  `assigned_by` bigint DEFAULT NULL,
  `client_id` bigint DEFAULT NULL,
  `permission_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`permission_id`,`role_id`),
  KEY `FKn5fotdgk8d1xvo8nav9uv3muc` (`role_id`),
  CONSTRAINT `FKegdk29eiy7mdtefy5c7eirr6e` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`id`),
  CONSTRAINT `FKn5fotdgk8d1xvo8nav9uv3muc` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permissions`
--

LOCK TABLES `role_permissions` WRITE;
/*!40000 ALTER TABLE `role_permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `role_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `client_id` bigint DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `is_predefined` bit(1) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKofx66keruapi6vyqpv6f2or37` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` (`id`, `client_id`, `description`, `is_predefined`, `name`) VALUES (1,1,'SUPER_ADMIN role',_binary '\0','SUPER_ADMIN'),(2,1,'ADMIN role',_binary '\0','ADMIN'),(3,1,'USER role',_binary '\0','USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `token_blacklist`
--

DROP TABLE IF EXISTS `token_blacklist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `token_blacklist` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `blacklisted_at` datetime(6) DEFAULT NULL,
  `client_id` bigint DEFAULT NULL,
  `expiry_date` datetime(6) DEFAULT NULL,
  `token` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `token_blacklist`
--

LOCK TABLES `token_blacklist` WRITE;
/*!40000 ALTER TABLE `token_blacklist` DISABLE KEYS */;
/*!40000 ALTER TABLE `token_blacklist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `assigned_at` datetime(6) DEFAULT NULL,
  `assigned_by` bigint DEFAULT NULL,
  `client_id` bigint DEFAULT NULL,
  `role_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`role_id`,`user_id`),
  KEY `FKhfh9dx7w3ubf1co1vdev94g3f` (`user_id`),
  CONSTRAINT `FKh8ciramu9cc9q3qcqiv4ue8a6` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` (`assigned_at`, `assigned_by`, `client_id`, `role_id`, `user_id`) VALUES ('2025-05-28 21:17:26.076547',NULL,NULL,1,1),('2025-05-29 10:23:57.349938',NULL,NULL,2,4),('2025-05-29 09:25:31.355902',NULL,NULL,3,2),('2025-05-29 10:28:44.907532',NULL,NULL,3,5);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `names` varchar(100) DEFAULT NULL,
  `client_id` bigint DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `password` varchar(120) NOT NULL,
  `phone_number` varchar(15) DEFAULT NULL,
  `is_super_admin` bit(1) DEFAULT NULL,
  `username` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UK9q63snka3mdh91as4io72espi` (`phone_number`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`, `names`, `client_id`, `email`, `enabled`, `password`, `phone_number`, `is_super_admin`, `username`) VALUES (1,'Cyprien Hagenimana',1,'rwendere@gmail.com',_binary '','$2a$12$7gEEmgbE.3Scg0RtNZo34OuMYFARfFsUsTgpj9/GBx.62oD9hK3Ce','0780000000',_binary '','Cyprienhr'),(2,'Kamana Alex',1,'hagena01@gmail.com',_binary '','$2a$12$eccofjxXLt/tdFU524mE0udpWnL6mnNgEBGs1oxt2T1qXvrEYzT1q','0781000000',_binary '\0','Hagena'),(3,'Augustin  Kalisa',1,'cyprien@gmail.com',_binary '','$2a$12$XUvXX0kpaSF5nPBTjHYDBONMNEXgDdphZ5k6.wXCwje4.kgsHjK.q','0782000000',_binary '\0','Cyprien'),(4,'Pie  Masomo',1,'pie@comza.com',_binary '','$2a$12$LcTsE4WK.UenM.A6YREYHuX04FuFznKlvh7Tpg51/DboaZlu5zxXC','0783000000',_binary '\0','Pie'),(5,'Jean Paul',1,'paul@gmail.com',_binary '','$2a$12$AQ2wlUg5Oj5a5zZMal4BxewhEj2.vfNg4Q/ySoMi4vgzsfI8vd2PW','0784000000',_binary '\0','Paul');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-05 14:45:21
