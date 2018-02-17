CREATE DATABASE  IF NOT EXISTS `mycafeteria` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `mycafeteria`;
-- MySQL dump 10.13  Distrib 5.6.24, for Win32 (x86)
--
-- Host: localhost    Database: mycafeteria
-- ------------------------------------------------------
-- Server version	5.6.26-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mailid` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `smtpmailid` varchar(45) DEFAULT NULL,
  `smtppassword` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `mailid_UNIQUE` (`mailid`),
  UNIQUE KEY `smtpmailid_UNIQUE` (`smtpmailid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'mycafeteria_admin@gmail.com','admin','mycafeteria.chennai.cts','2013ht78070');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Breakfast'),(2,'Lunch'),(3,'Snacks'),(4,'Juices'),(5,'Dinner'),(6,'Chinese'),(7,'Beverages'),(8,'IceCream'),(9,'Others'),(17,'Tiffin'),(24,'tn'),(25,'newcat');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `price` double(6,2) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `vendorid` int(11) DEFAULT NULL,
  `categoryid` int(11) DEFAULT NULL,
  `isactive` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_vendorid_idx` (`vendorid`),
  KEY `fk_categoryid_idx` (`categoryid`),
  CONSTRAINT `fk_categoryid` FOREIGN KEY (`categoryid`) REFERENCES `category` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_vendorid` FOREIGN KEY (`vendorid`) REFERENCES `vendor` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (1,'South Indian meals',50.00,100,3,1,1),(2,'North Indian Meals',62.00,0,1,2,1),(3,'Mini meals',70.00,100,1,2,1),(4,'Samosa',10.00,0,3,3,1),(5,'Idly',30.00,100,1,1,1),(6,'Lemon Juice',15.00,99,1,4,1),(9,'Andhra Meal',48.00,90,2,2,0),(11,'Puree',34.00,22,1,1,1),(12,'Chola Puri',98.00,22,1,5,1),(13,'Tiffin Combo',30.00,50,1,1,1),(14,'Dosai',48.00,70,1,1,1),(15,'GheeDosai',58.00,70,1,1,1),(16,'Potato Chips',15.00,20,1,3,1),(17,'Pomogranate Juice',48.00,20,1,4,1),(18,'Parota',50.00,50,1,5,1),(19,'Idly',30.00,100,1,5,1),(20,'Puree',34.00,22,1,5,1),(22,'Dosai',48.00,70,1,5,1),(23,'GheeDosai',58.00,70,1,5,1),(25,'Pistha',30.00,20,1,8,1),(27,'Coffee',30.00,50,1,7,1),(28,'Tea',30.00,50,1,7,1),(29,'Gobi Manchurian',50.00,20,1,9,1),(30,'Idly',30.00,100,2,1,1),(31,'Puree',34.00,22,2,1,1),(41,'Aloo Bhujia',45.00,97,3,3,1),(42,'Chola Puri',98.00,22,8,5,1),(43,'Coffee',30.00,48,3,7,1),(44,'Tiffin Combo',30.00,50,8,1,1),(45,'Tea',30.00,50,3,7,1),(47,'Lemon Juice',15.00,100,6,4,1),(49,'Pomogranate Juice',48.00,20,6,4,1),(51,'Pomogranate Juice',48.00,20,1,4,1),(56,'Dosai',48.00,70,1,5,1),(58,'Veg.Noodles',60.00,30,1,6,1),(60,'Vanilla',30.00,20,1,8,1),(64,'North Indian Meals',62.00,100,8,2,1),(65,'Chola Puri',98.00,18,2,5,1),(66,'GheeDosai',58.00,67,2,5,1),(67,'Mini meals',70.00,100,8,2,1),(68,'Idly',30.00,100,8,1,1),(69,'Lemon Juice',15.00,100,8,4,1),(71,'Dosai',48.00,70,2,1,1),(73,'Puree',34.00,22,8,1,1),(76,'Pomogranate Juice',48.00,20,7,4,1),(77,'Lemon Juice',15.00,100,7,4,1),(92,'Pomogranate Juice',48.00,16,13,4,1),(93,'Lemon Juice',15.00,100,13,4,1),(96,'Idly',30.00,100,11,1,1),(97,'Lemon Juice',15.00,96,11,4,1),(98,'Puree',34.00,22,11,1,1),(99,'Chola Puri',98.00,22,11,5,1),(100,'Parota',50.00,50,11,5,1),(101,'North Indian Meals',62.00,100,11,2,1),(103,'Samosa',10.00,99,10,3,1),(104,'Aloo Bhujia',45.00,99,10,3,1),(105,'Parota',50.00,50,9,5,1),(106,'Idly',30.00,100,9,1,1),(107,'Puree',34.00,22,9,1,1),(108,'Chola Puri',98.00,22,9,5,1),(109,'North Indian Meals',62.00,100,9,2,1),(110,'Samosa',10.00,100,9,3,1),(112,'nr',4.70,1,19,24,1),(113,'it1',33.00,0,20,25,1);
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `location` varchar(45) NOT NULL,
  `code` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (1,'SRISEZ1','SRI'),(2,'ASV IT PARK','ASV'),(3,'DLF IT PARK','DLF'),(4,'GMR PERUNGUDI','GMR'),(5,'VARALAKSHMI TECH PARK','VTP'),(9,'thoraipallam','TCO'),(10,'MEPZ','MEPZ');
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderdetails`
--

DROP TABLE IF EXISTS `orderdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orderdetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` double(6,2) DEFAULT NULL,
  `itemscount` varchar(45) DEFAULT NULL,
  `secretcode` varchar(45) DEFAULT NULL,
  `userid` int(11) DEFAULT NULL,
  `vendorid` int(11) DEFAULT NULL,
  `orderstatus` varchar(45) DEFAULT NULL,
  `timestamp` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `secretcode_UNIQUE` (`secretcode`),
  KEY `userif_fnkey_idx` (`userid`),
  KEY `fk_vendorid_idx` (`vendorid`),
  CONSTRAINT `fk_userid` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_vendor` FOREIGN KEY (`vendorid`) REFERENCES `vendor` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderdetails`
--

LOCK TABLES `orderdetails` WRITE;
/*!40000 ALTER TABLE `orderdetails` DISABLE KEYS */;
INSERT INTO `orderdetails` VALUES (45,45.00,'93:3','C40SE3YZ',1,13,'expired','2015-09-27 00:05:20'),(47,48.00,'92:1','N52SRWQL',1,13,'expired','2015-09-27 00:05:42'),(48,132.00,'2:1,3:1','1B6YYGJP',1,1,'expired','2015-09-27 14:09:45'),(49,15.00,'6:1','6VDB7BGQ',3,1,'expired','2015-10-24 10:24:35'),(50,30.00,'43:1','6I2J4WBV',3,3,'expired','2015-10-24 10:33:15'),(51,62.00,'2:1','0GAK7RCR',3,1,'expired','2015-10-24 10:36:00'),(52,48.00,'9:1','9AP6OR47',3,2,'expired','2015-10-24 10:38:16'),(54,288.00,'9:6','RG2G9QM7',3,2,'expired','2015-10-24 10:41:14'),(55,55.00,'103:1,104:1','CTSU5QEB',3,10,'expired','2015-10-24 10:45:30'),(56,10.00,'4:1','KV8FIUXU',1,3,'expired','2015-10-25 12:33:19'),(57,98.00,'65:1','GXVBEXN9',1,2,'expired','2015-10-25 12:37:48'),(58,96.00,'92:2','WBJZHLA3',2,6,'expired','2015-11-15 20:50:50'),(59,10.00,'4:1','ZH08G9SW',2,6,'expired','2015-11-15 20:53:10'),(60,96.00,'92:2','IXIXFV37',2,6,'expired','2015-11-15 20:53:10'),(61,15.00,'97:1','VZMF7Q77',5,6,'expired','2015-11-17 23:12:21'),(62,174.00,'66:3','59DISIAR',2,2,'expired','2015-12-12 13:30:27'),(63,96.00,'9:2','8P0ARRVL',2,2,'expired','2015-12-12 13:42:41'),(64,48.00,'9:1','XN84V1PC',2,2,'paid','2015-12-12 13:45:59'),(66,9.40,'112:2','0EM128WE',6,19,'expired','2013-01-04 09:34:16'),(67,4.70,'112:1','WRPXWQW6',6,19,'expired','2013-01-04 09:41:59'),(68,10.00,'4:1','9PONFKOK',4,3,'expired','2013-01-05 07:41:19'),(69,20.00,'4:2','TDEP0G5H',4,3,'expired','2013-01-05 07:42:20'),(70,15.00,'97:1','BTKGTXDK',4,11,'expired','2013-01-05 07:42:20'),(71,10.00,'4:1','VOP1D634',4,3,'expired','2013-01-05 07:44:37'),(72,15.00,'97:1','ILRFSA4N',4,11,'paid','2013-01-05 07:44:37'),(73,10.00,'4:1','TUKBFCE2',4,3,'expired','2013-01-05 07:49:11'),(74,15.00,'97:1','ZYR7EVH6',4,11,'paid','2013-01-05 07:49:11'),(75,10.00,'4:1','KMC0HCSA',4,3,'paid','2013-01-05 07:54:20'),(76,196.00,'65:2','C8U9W4NA',4,2,'expired','2013-01-05 08:06:57'),(77,135.00,'41:3','WL0FF6BV',4,3,'paid','2013-01-05 08:06:57'),(78,4.70,'112:1','5Y8SYV32',4,19,'paid','2013-01-05 08:31:46'),(79,33.00,'113:1','CPK2BNOF',4,20,'expired','2013-01-05 08:55:40'),(80,75.00,'41:1,43:1','NOPOX3DV',4,3,'paid','2013-01-05 08:57:42'),(81,33.00,'113:1','JX2GRGC5',4,20,'paid','2013-01-05 08:57:42');
/*!40000 ALTER TABLE `orderdetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `declaredamount` double(6,2) DEFAULT NULL,
  `availablebalance` double(6,2) DEFAULT NULL,
  `mailid` varchar(45) DEFAULT NULL,
  `favorites` varchar(45) DEFAULT NULL,
  `token` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`userid`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `mailid_UNIQUE` (`mailid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'355228','7ISAWVPU',245.00,1036.43,'krishnakumar.gopal@yahoo.com','4,1,6,114','NJWL0L3SS2OJ'),(2,'355229','new',2000.00,1436.00,'sangita.manoharan@gmail.com','4,113','IYJHGWH235NB'),(3,'125439','ishaan',0.00,2000.00,'ishaan@gmail.com','2','F6DJ4LT5118E'),(4,'g','g',34.00,53.71,'new@gmail.com',NULL,'CSNNNYEB0Z57'),(5,'kk','k',0.00,913.50,'krishnakumar9113@gmail.com','125','P8TR4DC8TYXV'),(6,'a','a',0.00,985.90,'g@gmail.com','30','F1EDR3PCW01L');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vendor`
--

DROP TABLE IF EXISTS `vendor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vendor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `mail` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `locationid` int(11) NOT NULL,
  `isactive` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `mail_UNIQUE` (`mail`),
  KEY `location_idx` (`locationid`),
  CONSTRAINT `fk_locationid` FOREIGN KEY (`locationid`) REFERENCES `location` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vendor`
--

LOCK TABLES `vendor` WRITE;
/*!40000 ALTER TABLE `vendor` DISABLE KEYS */;
INSERT INTO `vendor` VALUES (1,'Hotel Saravana Bhavan','hsb_sri@gmail.com','4XX16HWN',3,1),(2,'FoodExo','fex_sri@gmail.com','O86LMWPV',1,1),(3,'SK Snacks','sk_sri@gmail.com','sks',1,1),(6,'Jugo Juices','jugo_sri@gmail.com','jug',3,1),(7,'Hotel Saravana Bhavan','hsb_vtp@gmail.co','put',5,1),(8,'Hotel Saravana Bhavan','hsb_asv@gmail.com','put',2,1),(9,'FoodExo','fex_vtp@gmail.com','fex',5,1),(10,'SK Snacks','sk_asv@gmail.com','sks',2,1),(11,'Hotel Saravana Bhavan','hsb_gmr@gmail.com','put',4,1),(13,'JUGO','jugo_dlf@gmail.com','jug',3,1),(18,'mm_dlf','krishnakumar.gopal@yahoo.com','a',3,1),(19,'th','th@gmail.com','a',9,1),(20,'mepzven','mepz_ven@gmail.com','a',10,1);
/*!40000 ALTER TABLE `vendor` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-01-05 23:08:35
