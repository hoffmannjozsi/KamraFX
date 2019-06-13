CREATE DATABASE  IF NOT EXISTS `kamra` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `kamra`;
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: kamra
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `alapanyag`
--

DROP TABLE IF EXISTS `alapanyag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `alapanyag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nev` varchar(45) DEFAULT NULL,
  `me` varchar(45) DEFAULT NULL,
  `minimum` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alapanyag`
--

LOCK TABLES `alapanyag` WRITE;
/*!40000 ALTER TABLE `alapanyag` DISABLE KEYS */;
INSERT INTO `alapanyag` VALUES (1,'méz','kg',5),(5,'tej','liter',20),(6,'kenyér','kg',10),(7,'krumpli','kg',50),(8,'zsír','kg',25),(9,'só','kg',9),(10,'cukor','kg',55),(11,'olaj','liter',50),(12,'tojás','db',100),(13,'bors','kg',0.5),(14,'csemege őrölt paprika','kg',1),(15,'vaj','kg',2),(16,'paprika','kg',10),(17,'vörös hagyma','kg',10),(18,'kolbász','kg',5),(19,'liszt','kg',30),(20,'gyalult tök','kg',5),(21,'kacsamell','kg',5),(22,'fokhagyma','kg',2),(23,'tőkehalfilé','kg',5),(24,'citrom','kg',4),(25,'rizs','kg',10),(26,'olivaolaj','liter',5),(27,'sárgarépa','kg',10),(28,'cukkini','kg',3),(29,'brokkoli','kg',5),(30,'búzadara','kg',5);
/*!40000 ALTER TABLE `alapanyag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `beszerzes`
--

DROP TABLE IF EXISTS `beszerzes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `beszerzes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `alap_id` int(11) NOT NULL,
  `mennyiseg` double DEFAULT NULL,
  `datum` date DEFAULT NULL,
  `atvezetve` tinyint(4) DEFAULT '0',
  `me` varchar(10) DEFAULT NULL,
  `egysegar` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `alap_id_idx` (`alap_id`),
  CONSTRAINT `alap_id` FOREIGN KEY (`alap_id`) REFERENCES `alapanyag` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `beszerzes`
--

LOCK TABLES `beszerzes` WRITE;
/*!40000 ALTER TABLE `beszerzes` DISABLE KEYS */;
INSERT INTO `beszerzes` VALUES (1,7,50,'2019-04-05',1,'kg',150),(2,19,100,'2019-04-05',1,'kg',95),(3,12,100,'2019-04-05',1,'db',30),(4,11,30,'2019-04-05',1,'l',295),(5,17,15,'2019-04-05',1,'kg',140),(6,5,10,'2019-04-05',1,'l',200),(7,13,0.5,'2019-04-11',1,'kg',1400),(8,29,8,'2019-04-11',1,'kg',850),(9,24,6,'2019-04-11',1,'kg',990),(10,14,5,'2019-04-11',1,'kg',2200),(11,28,6,'2019-04-11',1,'kg',650),(12,10,100,'2019-04-11',1,'kg',180),(13,22,4,'2019-04-11',1,'kg',1800),(14,20,10,'2019-04-11',1,'kg',600),(15,21,15,'2019-04-11',1,'kg',2400),(16,6,25,'2019-04-11',1,'kg',250),(17,18,10,'2019-04-11',1,'kg',1800),(18,7,100,'2019-04-11',1,'kg',260),(19,19,40,'2019-04-11',1,'kg',110),(20,1,5,'2019-04-11',1,'kg',2000),(21,11,30,'2019-04-11',1,'l',300),(22,26,10,'2019-04-11',1,'l',3500),(23,16,10,'2019-04-11',1,'kg',800),(24,25,30,'2019-04-11',1,'kg',270),(25,27,10,'2019-04-11',1,'kg',450),(26,9,15,'2019-04-11',1,'kg',150),(27,5,20,'2019-04-11',1,'l',205),(28,12,100,'2019-04-11',1,'db',35),(29,23,5,'2019-04-11',1,'kg',4000),(30,15,5,'2019-04-11',1,'kg',1500),(31,17,30,'2019-04-11',1,'kg',260),(32,8,20,'2019-04-11',1,'kg',180),(33,30,5,'2019-05-22',1,'kg',150);
/*!40000 ALTER TABLE `beszerzes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eladas`
--

DROP TABLE IF EXISTS `eladas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `eladas` (
  `id` int(11) NOT NULL,
  `etel_id` int(11) NOT NULL,
  `adag` int(11) DEFAULT NULL,
  `datum` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `etel_id_idx` (`etel_id`),
  CONSTRAINT `elad_etel_id` FOREIGN KEY (`etel_id`) REFERENCES `etel` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eladas`
--

LOCK TABLES `eladas` WRITE;
/*!40000 ALTER TABLE `eladas` DISABLE KEYS */;
/*!40000 ALTER TABLE `eladas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `etel`
--

DROP TABLE IF EXISTS `etel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `etel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nev` varchar(45) DEFAULT NULL,
  `elad_ar` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `etel`
--

LOCK TABLES `etel` WRITE;
/*!40000 ALTER TABLE `etel` DISABLE KEYS */;
INSERT INTO `etel` VALUES (5,'Paprikás krumpli',750),(6,'Tökfőzelék',455),(7,'Rántotta',325),(8,'Tőkehalfilé párolt rizzsel',2500),(9,'Sült kacsamell',1800),(10,'Tejbegríz',250);
/*!40000 ALTER TABLE `etel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `keszlet`
--

DROP TABLE IF EXISTS `keszlet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `keszlet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `beszerzes_id` int(11) NOT NULL,
  `alap_id` int(11) NOT NULL,
  `mennyiseg` double DEFAULT NULL,
  `ar` int(11) DEFAULT NULL,
  `elfogyott` tinyint(4) DEFAULT '0',
  `elfogy_datum` date DEFAULT NULL,
  `me` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `keszlet_alap_id_idx` (`alap_id`),
  KEY `beszerzes_id_idx` (`beszerzes_id`),
  CONSTRAINT `beszerzes_id` FOREIGN KEY (`beszerzes_id`) REFERENCES `beszerzes` (`id`),
  CONSTRAINT `keszlet_alap_id` FOREIGN KEY (`alap_id`) REFERENCES `alapanyag` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `keszlet`
--

LOCK TABLES `keszlet` WRITE;
/*!40000 ALTER TABLE `keszlet` DISABLE KEYS */;
INSERT INTO `keszlet` VALUES (1,1,7,50,150,0,NULL,'kg'),(2,2,19,100,95,0,NULL,'kg'),(3,3,12,100,30,0,NULL,'db'),(4,4,11,30,295,0,NULL,'l'),(5,5,17,15,140,0,NULL,'kg'),(6,6,5,10,200,0,NULL,'l'),(7,7,13,0.5,1400,0,NULL,'kg'),(8,8,29,8,850,0,NULL,'kg'),(9,9,24,6,990,0,NULL,'kg'),(10,10,14,5,2200,0,NULL,'kg'),(11,11,28,6,650,0,NULL,'kg'),(12,12,10,100,180,0,NULL,'kg'),(13,13,22,4,1800,0,NULL,'kg'),(14,14,20,10,600,0,NULL,'kg'),(15,15,21,15,2400,0,NULL,'kg'),(16,16,6,25,250,0,NULL,'kg'),(17,17,18,10,1800,0,NULL,'kg'),(18,18,7,100,260,0,NULL,'kg'),(19,19,19,40,110,0,NULL,'kg'),(20,20,1,5,2000,0,NULL,'kg'),(21,21,11,30,300,0,NULL,'l'),(22,22,26,10,3500,0,NULL,'l'),(23,23,16,10,800,0,NULL,'kg'),(24,24,25,30,270,0,NULL,'kg'),(25,25,27,10,450,0,NULL,'kg'),(26,26,9,15,150,0,NULL,'kg'),(27,27,5,20,205,0,NULL,'l'),(28,28,12,100,35,0,NULL,'db'),(29,29,23,5,4000,0,NULL,'kg'),(30,30,15,5,1500,0,NULL,'kg'),(31,31,17,30,260,0,NULL,'kg'),(32,32,8,20,180,0,NULL,'kg'),(33,33,30,5,150,0,NULL,'kg');
/*!40000 ALTER TABLE `keszlet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recept`
--

DROP TABLE IF EXISTS `recept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `recept` (
  `etel_id` int(11) NOT NULL,
  `alap_id` int(11) NOT NULL,
  `mennyiseg` double DEFAULT NULL,
  `me` varchar(10) DEFAULT NULL,
  KEY `etel_id_idx` (`etel_id`),
  KEY `alap_id_idx` (`etel_id`,`alap_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recept`
--

LOCK TABLES `recept` WRITE;
/*!40000 ALTER TABLE `recept` DISABLE KEYS */;
INSERT INTO `recept` VALUES (5,7,25,'dkg'),(5,18,5,'dkg'),(5,14,3,'g'),(5,17,10,'g'),(5,8,10,'g'),(5,9,1,'g'),(6,20,25,'dkg'),(6,19,10,'g'),(6,11,5,'cl'),(6,14,5,'g'),(6,9,2,'g'),(7,12,3,'db'),(7,11,5,'ml'),(7,9,0.3,'g'),(8,23,30,'dkg'),(8,25,20,'dkg'),(8,27,5,'dkg'),(8,28,5,'dkg'),(8,29,5,'dkg'),(8,26,10,'ml'),(8,9,1,'g'),(8,13,1,'g'),(9,21,30,'dkg'),(9,22,3,'g'),(9,9,0.5,'g'),(9,13,1,'g'),(10,5,3,'dl'),(10,30,20,'g'),(10,10,2,'g');
/*!40000 ALTER TABLE `recept` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-13 22:40:42
