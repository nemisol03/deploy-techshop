-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: myshop
-- ------------------------------------------------------
-- Server version	8.0.34

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


use myshop;


--
-- Table structure for table `brands`
--

DROP TABLE IF EXISTS `brands`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brands` (

                          `id` int NOT NULL AUTO_INCREMENT,
                          `logo` varchar(255) DEFAULT NULL,
                          `name` varchar(255) NOT NULL,
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 ;

  `id` int NOT NULL AUTO_INCREMENT,
  `logo` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brands`
--

LOCK TABLES `brands` WRITE;
/*!40000 ALTER TABLE `brands` DISABLE KEYS */;
INSERT INTO `brands` VALUES (1,'apple.png','Apple'),(2,'Lenovo_Global_Corporate_Logo.png','Lenovo'),(3,'Samsung_logo.avif','Sam sung'),(4,'Dell_Logo.svg.png','Dell'),(5,'canon-logo-canon-icon-free-free-vector.jpg','Canon'),(6,'Sony-logo.png','Sony'),(7,'01-nvidia-logo-horiz-500x200-2c50-p@2x.png','NVIDIA'),(8,'xiaomi-logo.jpg','Xiaomi'),(9,'Asus-Logo.png','Asus'),(10,'png-clipart-acer-logo-laptop-acer-aspire-computer-logo-lenovo-logo-electronics-text.png','Acer'),(20,'logitech_brand_lo.jpg','Logitech'),(21,'tech21logo.png','Tech 21');
/*!40000 ALTER TABLE `brands` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brands_categories`
--

DROP TABLE IF EXISTS `brands_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brands_categories` (

                                     `brand_id` int NOT NULL,
                                     `category_id` int NOT NULL,
                                     PRIMARY KEY (`brand_id`,`category_id`),
                                     KEY `FK6x68tjj3eay19skqlhn7ls6ai` (`category_id`),
                                     CONSTRAINT `FK58ksmicdguvu4d7b6yglgqvxo` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`),
                                     CONSTRAINT `FK6x68tjj3eay19skqlhn7ls6ai` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

  `brand_id` int NOT NULL,
  `category_id` int NOT NULL,
  PRIMARY KEY (`brand_id`,`category_id`),
  KEY `FK6x68tjj3eay19skqlhn7ls6ai` (`category_id`),
  CONSTRAINT `FK58ksmicdguvu4d7b6yglgqvxo` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`),
  CONSTRAINT `FK6x68tjj3eay19skqlhn7ls6ai` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brands_categories`
--

LOCK TABLES `brands_categories` WRITE;
/*!40000 ALTER TABLE `brands_categories` DISABLE KEYS */;
INSERT INTO `brands_categories` VALUES (1,1),(2,1),(3,1),(4,1),(6,1),(8,1),(10,1),(1,2),(3,2),(5,2),(6,2),(10,2),(1,3),(2,3),(3,3),(4,3),(9,3),(10,3),(1,4),(2,4),(3,4),(8,4),(10,4),(20,4),(21,4),(1,5),(2,5),(3,5),(4,5),(9,5),(10,5),(1,6),(2,6),(3,6),(4,6),(9,6),(10,6),(1,7),(2,7),(3,7),(4,7),(6,7),(8,7),(9,7),(10,7),(1,8),(2,8),(3,8),(4,8),(9,8),(10,8),(3,9),(5,9),(9,9),(1,10),(5,10),(5,11),(6,11),(5,12),(6,12),(1,14),(3,14),(6,14),(8,14),(1,15),(3,15),(6,15),(8,15),(1,16),(2,16),(3,16),(8,16),(20,16),(21,16),(1,17),(3,17),(6,17),(21,17),(8,18),(4,20),(6,20),(8,20),(21,20),(1,21),(3,21),(6,21),(8,21),(10,21),(20,21),(1,22),(2,22),(4,22),(9,22),(7,23),(9,24),(9,25),(3,26),(9,27),(9,28),(1,29),(3,29),(4,29),(9,29),(10,29),(3,30),(9,30),(9,31),(10,34),(20,34),(20,35);
/*!40000 ALTER TABLE `brands_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_items`
--

DROP TABLE IF EXISTS `cart_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_items` (

                              `id` int NOT NULL AUTO_INCREMENT,
                              `customer_id` int DEFAULT NULL,
                              `product_id` int DEFAULT NULL,
                              `quantity` int NOT NULL,
                              PRIMARY KEY (`id`),
                              KEY `FKdagcsk6v6x4n1kxw3rkp57921` (`customer_id`),
                              KEY `FK1re40cjegsfvw58xrkdp6bac6` (`product_id`),
                              CONSTRAINT `FK1re40cjegsfvw58xrkdp6bac6` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
                              CONSTRAINT `FKdagcsk6v6x4n1kxw3rkp57921` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 ;

  `id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdagcsk6v6x4n1kxw3rkp57921` (`customer_id`),
  KEY `FK1re40cjegsfvw58xrkdp6bac6` (`product_id`),
  CONSTRAINT `FK1re40cjegsfvw58xrkdp6bac6` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `FKdagcsk6v6x4n1kxw3rkp57921` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_items`
--

LOCK TABLES `cart_items` WRITE;
/*!40000 ALTER TABLE `cart_items` DISABLE KEYS */;
INSERT INTO `cart_items` VALUES (4,1,3,1),(14,11,12,3),(15,11,13,2);
/*!40000 ALTER TABLE `cart_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (

                              `id` int NOT NULL AUTO_INCREMENT,
                              `name` varchar(128) NOT NULL,
                              `alias` varchar(128) NOT NULL,
                              `image` varchar(64) NOT NULL,
                              `enabled` bit(1) NOT NULL,
                              `parent_id` int DEFAULT NULL,
                              `all_parent_ids` varchar(256) DEFAULT NULL,
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `UK_jx1ptm0r46dop8v0o7nmgfbeq` (`alias`),
                              UNIQUE KEY `UK_t8o6pivur7nn124jehx7cygw5` (`name`),
                              KEY `FKsaok720gsu4u2wrgbk10b5n8d` (`parent_id`),
                              CONSTRAINT `FKsaok720gsu4u2wrgbk10b5n8d` FOREIGN KEY (`parent_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 ;
=======
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `alias` varchar(128) NOT NULL,
  `image` varchar(64) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `parent_id` int DEFAULT NULL,
  `all_parent_ids` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_jx1ptm0r46dop8v0o7nmgfbeq` (`alias`),
  UNIQUE KEY `UK_t8o6pivur7nn124jehx7cygw5` (`name`),
  KEY `FKsaok720gsu4u2wrgbk10b5n8d` (`parent_id`),
  CONSTRAINT `FKsaok720gsu4u2wrgbk10b5n8d` FOREIGN KEY (`parent_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Electronics','electronics','electronics.png',_binary '',NULL,NULL),(2,'Camera & Photo','camera','camera.jpg',_binary '',1,'-1-'),(3,'Computers','computers','computers.png',_binary '',NULL,NULL),(4,'Cell Phones & Accessories','cellphones','cellphones.png',_binary '',1,'-1-'),(5,'Desktops','desktop_computers','desktop computers.png',_binary '',3,'-3-'),(6,'Laptops','laptop_computers','laptop computers.png',_binary '',3,'-3-'),(7,'Tablets','tablet_computers','tablets.png',_binary '',3,'-3-'),(8,'Computer Components','computer_components','computer components.png',_binary '',3,'-3-'),(9,'Bags & Cases','camera_bags_cases','bags_cases.png',_binary '',2,'-1-2-'),(10,'Digital Cameras','digital_cameras','digital cameras.png',_binary '',2,'-1-2-'),(11,'Flashes','camera_flashes','flashes.png',_binary '',2,'-1-2-'),(12,'Lenses','camera_lenses','lenses.png',_binary '',2,'-1-2-'),(13,'Tripods & Monopods','camera_tripods_monopods','tripods_monopods.png',_binary '',2,'-1-2-'),(14,'Carrier Cell Phones','carrier_cellphones','carrier cellphones.png',_binary '',4,'-1-4-'),(15,'Unlocked Cell Phones','unlocked_cellphones','unlocked cellphones.png',_binary '',4,'-1-4-'),(16,'Accessories','cellphone_accessories','cellphone accessories.png',_binary '',4,'-1-4-'),(17,'Cables & Adapters','cellphone_cables_adapters','cables and adapters.png',_binary '',16,'-1-4-16-'),(18,'MicroSD Cards','microsd_cards','microsd cards.png',_binary '',16,'-1-4-16-'),(19,'Stands','cellphone_stands','cellphone_stands.png',_binary '',16,'-1-4-16-'),(20,'Cases','cellphone_cases','cellphone cases.png',_binary '',16,'-1-4-16-'),(21,'Headphones','headphones','headphones.png',_binary '',16,'-1-4-16-'),(22,'CPU Processors Unit','computer_processors','computer processors.png',_binary '',8,'-3-8-'),(23,'Graphic Cards','computer_graphic_cards','graphic cards.png',_binary '',8,'-3-8-'),(24,'Internal Hard Drives','hard_drive','internal hard drive.png',_binary '',8,'-3-8-'),(25,'Internal Optical Drives','computer_optical_drives','internal optical drives.png',_binary '',8,'-3-8-'),(26,'Power Supplies','computer_power_supplies','power supplies.png',_binary '',8,'-3-8-'),(27,'Solid State Drives','solid_state_drives','solid state drives.png',_binary '',8,'-3-8-'),(28,'Sound Cards','computer_sound_cards','sound cards.png',_binary '',8,'-3-8-'),(29,'Memory','computer_memory','computer memory.png',_binary '',8,'-3-8-'),(30,'Motherboard','computer_motherboard','motherboards.png',_binary '',8,'-3-8-'),(31,'Network Cards','computer_network_cards','network cards.png',_binary '',8,'-3-8-');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (

                             `id` int NOT NULL AUTO_INCREMENT,
                             `email` varchar(128) NOT NULL,
                             `password` varchar(64) NOT NULL,
                             `first_name` varchar(255) NOT NULL,
                             `last_name` varchar(255) NOT NULL,
                             `address` varchar(255) DEFAULT NULL,
                             `image` varchar(255) DEFAULT NULL,
                             `phone_number` varchar(255) DEFAULT NULL,
                             `enabled` bit(1) NOT NULL,
                             `verification_code` varchar(255) DEFAULT NULL,
                             `created_at` datetime(6) DEFAULT NULL,
                             `modified_at` datetime(6) DEFAULT NULL,
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `UK_rfbvkrffamfql7cjmen8v976v` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 ;

  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(128) NOT NULL,
  `password` varchar(64) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `verification_code` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_rfbvkrffamfql7cjmen8v976v` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'Vuhoainam@gmail.com','$2a$12$Z1lF2L0Gu.0ogINaD5Pdme7pRMw2klUJY7mA8q/vngsf6CS0ynOnO','vu hoai','John','456 Elm Avenue',NULL,'5555555555',_binary '',NULL,NULL,'2023-10-11 00:21:52.470730'),(2,'as@gmail.com','$2a$12$Z1lF2L0Gu.0ogINaD5Pdme7pRMw2klUJY7mA8q/vngsf6CS0ynOnO','vu hoai','John','456 Elm Avenue',NULL,'5555555555',_binary '',NULL,'2023-10-07 01:24:58.457290','2023-10-11 00:24:41.748043'),(11,'vuhoainamndt@gmail.com','$2a$10$XeHhi1aIMwVyCmXHjkKJZuWtMXDev0oW0Cl.RxnfHzE0Q6nphLJ3q','vu','nam','456 Elm Avenue','download.jpg','5555555555',_binary '',NULL,NULL,'2023-10-11 00:19:08.433271');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_details`
--

DROP TABLE IF EXISTS `product_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_details` (

                                   `id` int NOT NULL AUTO_INCREMENT,
                                   `name` varchar(64) NOT NULL,
                                   `value` varchar(128) NOT NULL,
                                   `product_id` int DEFAULT NULL,
                                   PRIMARY KEY (`id`),
                                   KEY `FKnfvvq3meg4ha3u1bju9k4is3r` (`product_id`),
                                   CONSTRAINT `FKnfvvq3meg4ha3u1bju9k4is3r` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8mb4 ;

  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `value` varchar(128) NOT NULL,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnfvvq3meg4ha3u1bju9k4is3r` (`product_id`),
  CONSTRAINT `FKnfvvq3meg4ha3u1bju9k4is3r` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_details`
--

LOCK TABLES `product_details` WRITE;
/*!40000 ALTER TABLE `product_details` DISABLE KEYS */;
INSERT INTO `product_details` VALUES (1,'CPU speed','2.1 GHz',1),(2,'Operating system','Windows 11 Home',1),(3,'Hard disk size','256 GB',1),(4,'Brand','Dell',1),(5,'Resolution','1080p',1),(6,'Ram memory installed size','8 GB',1),(7,'Screen size','15.6 inches',1),(8,'Brand','Apple',2),(9,'Screen size','13.3 inches',2),(10,'Color','Space gray',2),(11,'Model name','Macbook Air',2),(12,'Ram memory installed size','8 GB',2),(13,'Operating system','Mac OS',2),(14,'CPU model','Apple M1',2),(15,'Hard disk size','512 GB',2),(16,'Model name','Acer Swift 3',3),(17,'CPU Model ','Intel core i7-1165G7',3),(18,'Brand','Acer',3),(19,'Screen size','14 inches',3),(20,'Color','Silver',3),(21,'Hard disk size','521 GB',3),(22,'Operation system','Windows 11 Home',3),(23,'Personal computer design type','Mini PC',4),(24,'Operating system','Windows 11 Home',4),(25,'Screen size','15.6 inches',4),(26,'CPU model','Intel core i7',4),(27,'Memory storage capacity','512 GB',4),(28,'Brand','Acer',4),(29,'Brand','Acer',5),(30,'Special feature','wireless',5),(31,'Number of button','3',5),(32,'Color','Gray',5),(33,'Connectivity technology','wireless',5),(34,'Movement detection technology','optional',6),(35,'Color','graphite',6),(36,'Brand','Logitech',6),(37,'Compatible devices','laptop, PC, tablet',7),(38,'Brand','Logitech',7),(39,'Number of keys','64',7),(40,'Color ','pale grey',7),(41,'Brand','Lenovo',8),(42,'Operating system ','Win 11',8),(43,'Screen size ','27 inches',8),(44,'CPU model','Core i7',8),(45,'pc design type','all in one',8),(46,'RAM','DDR4',9),(47,'Chipset type','AMD B550',9),(48,'CPU Socket','Socket AM4',9),(49,'Brand ','Asus',9),(50,'Memory storage capacity','128 GB',9),(51,'Operating system','Mac OS X',10),(52,'Brand','Apple',10),(53,'Memory storage capacity','8 GB',10),(54,'Color','Silver',10),(55,'CPU model','Core i5',10),(56,'Screen size','21.5 inches',11),(57,'Operating system','Mac OS',11),(58,'Brand ','Apple',11),(59,'Memory storage capacity','8 GB',11),(60,'Specific uses for product','business',11),(61,'Color','Purple',12),(62,'Battery capacity','4 milliamp hours',12),(63,'Brand','Never Run Out',12),(64,'Connector type','USB Type C',12),(65,'Model name','iphone X',13),(66,'Operating system','IOS',13),(67,'Brand','Apple',13),(68,'Screen size','5.8 inches',13),(69,'Cellular technology','4G',13),(70,'Screen size','6.7 inches',14),(71,'RAM','256 GB',14),(72,'Model name','Samsung galaxy flip5',14),(73,'Operating system','android 13.0',14),(74,'Brand','SAMSUNG',14),(75,'Color','clear',15),(76,'Brand','Tech 21',15),(77,'Material','Biodegradable, TPU, PET Film, Magnet',15),(78,'Compatible phone models','Iphone 15 plus',15),(79,'Color','BALLET WAY (PINK SALT/BLUSH)',16),(80,'Compatible Phone Models',' Models	iPhone 8 Plus & iPhone 7 Plus',16),(81,'Material','Synthetic Rubber, Polycarbonate',16),(82,'Brand','Tech 21',16);
/*!40000 ALTER TABLE `product_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_images`
--

DROP TABLE IF EXISTS `product_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_images` (
                                  `id` int NOT NULL AUTO_INCREMENT,
                                  `name` varchar(256) DEFAULT NULL,
                                  `product_id` int DEFAULT NULL,
                                  PRIMARY KEY (`id`),
                                  KEY `FKqnq71xsohugpqwf3c9gxmsuy` (`product_id`),
                                  CONSTRAINT `FKqnq71xsohugpqwf3c9gxmsuy` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 ;

  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqnq71xsohugpqwf3c9gxmsuy` (`product_id`),
  CONSTRAINT `FKqnq71xsohugpqwf3c9gxmsuy` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_images`
--

LOCK TABLES `product_images` WRITE;
/*!40000 ALTER TABLE `product_images` DISABLE KEYS */;
INSERT INTO `product_images` VALUES (1,'61O73sIJ+sL._AC_SL1500_.jpg',1),(2,'615QtGYw-zL._AC_SL1500_.jpg',1),(3,'61IFxGpAoSL._AC_SL1500_.jpg',1),(4,'61sbM5hn5PL._AC_SL1500_.jpg',1),(5,'61CGtutjG2L._AC_SL1500_.jpg',1),(6,'61uNK7su24L._AC_SL1500_.jpg',2),(7,'71LG4S9ZB9L._AC_SL1500_.jpg',2),(8,'91YEUMzK8cL._AC_SL1500_.jpg',2),(9,'61ChHwbxObL._AC_SL1500_.jpg',2),(10,'712v9WGWDBL._AC_SL1500_.jpg',2),(11,'61zOfqWwPML._AC_SL1500_.jpg',3),(12,'41f0K9D+UvL._AC_SL1194_.jpg',3),(13,'51dF0lOI6tS._AC_SL1500_.jpg',3),(14,'41CCqJLxUfL._AC_SL1200_.jpg',3),(15,'51Vi4UqcY8S._AC_SL1500_.jpg',3),(16,'61eAtsdQIhS._AC_SL1500_.jpg',3),(17,'61RRWgcocHS._AC_SL1500_.jpg',3),(20,'61IIPP4jqTL._AC_SL1440_.jpg',4),(21,'91S1E7nLORL._AC_SL1500_.jpg',4),(22,'71NVZrAKVuL._AC_SL1500_.jpg',4),(23,'61Oe4c2SERL._AC_SL1000_.jpg',5),(24,'41qBXd7oGrL._AC_SL1500_.jpg',5),(25,'41PjajEaJdL._AC_SL1482_.jpg',5),(26,'61cvu055iAL._AC_SL1500_.jpg',6),(27,'71pVZmATalL._AC_SL1500_.jpg',6),(28,'61GrcsohulL._AC_SL1500_.jpg',6),(29,'71r1IL6YAUL._AC_SL1500_.jpg',7),(30,'71FEGmu11RL._AC_SL1500_.jpg',7),(31,'618KergKfuL._AC_SL1500_.jpg',7),(32,'61bNKCfWZ0L._AC_SL1500_.jpg',8),(33,'711auo+uOnL._AC_SL1500_.jpg',8),(34,'81YBXqzu0BL._AC_SL1500_.jpg',8),(35,'815QA5oEulL._AC_SL1500_.jpg',8),(36,'813ioquHBuL._AC_SL1500_.jpg',9),(37,'81el0avh4kL._AC_SL1500_.jpg',9),(38,'81MxrqCaXqL._AC_SL1500_.jpg',9),(39,'91wI71QuefL._AC_SL1500_.jpg',9),(40,'5159wx9GYrL._AC_SL1024_.jpg',10),(41,'51o2ldY7SmL._AC_.jpg',10),(42,'61MyE2+sQOL._AC_SL1274_.jpg',10),(43,'41lXDHh5FqL._AC_SL1024_.jpg',11),(44,'61zjlaTjBdL._AC_SL1000_.jpg',12),(45,'61eJEjVrUiL._AC_SL1000_.jpg',12),(46,'515SsJRorgL._AC_SL1000_.jpg',12),(47,'61kk5pJ2WZL._AC_SL1000_.jpg',12),(48,'51npU6wOqFL._AC_SL1500_.jpg',13),(49,'51TvxwoHdWL._AC_SL1500_.jpg',13),(50,'51nkavEZAZL._AC_SL1000_.jpg',14),(51,'71QOij1LstL._AC_SL1500_.jpg',14),(52,'51NtH5wdKgL._AC_SL1500_.jpg',14),(53,'7192rNX4N9L._AC_SL1500_.jpg',15),(54,'61pAGybm0AL._AC_SL1500_.jpg',15),(55,'7170Zw0wixL._AC_SL1500_.jpg',16),(56,'81Ixb6WucHL._AC_SL1500_.jpg',16),(57,'71wHUA0z8tL._AC_SL1500_.jpg',16),(58,'91M+TT-6b5L._AC_SL1500_.jpg',16),(59,'81a63G8m7YL._AC_SL1500_.jpg',16),(62,'extra2.jpg',4),(63,'extra3.jpg',4);
/*!40000 ALTER TABLE `product_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `name` varchar(512) NOT NULL,
                            `alias` varchar(512) NOT NULL,
                            `main_image` varchar(128) NOT NULL,
                            `price` float NOT NULL,
                            `list_price` float DEFAULT '0',
                            `short_description` text,
                            `full_description` text,
                            `in_stock` bit(1) DEFAULT b'0',
                            `enabled` bit(1) NOT NULL DEFAULT b'0',
                            `discount_percent` float DEFAULT '0',
                            `created_at` datetime(6) DEFAULT NULL,
                            `modified_at` datetime(6) DEFAULT NULL,
                            `brand_id` int DEFAULT NULL,
                            `category_id` int DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `UK_8qwq8q3hk7cxkp9gruxupnif5` (`alias`),
                            UNIQUE KEY `UK_o61fmio5yukmmiqgnxf8pnavn` (`name`),
                            KEY `FKa3a4mpsfdf4d2y6r8ra3sc8mv` (`brand_id`),
                            KEY `FKog2rp4qthbtt2lfyhfo32lsw9` (`category_id`),
                            FULLTEXT KEY `product_FTS` (`name`,`short_description`,`full_description`),
                            CONSTRAINT `FKa3a4mpsfdf4d2y6r8ra3sc8mv` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`),
                            CONSTRAINT `FKog2rp4qthbtt2lfyhfo32lsw9` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 ;

  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(512) NOT NULL,
  `alias` varchar(512) NOT NULL,
  `main_image` varchar(128) NOT NULL,
  `price` float NOT NULL,
  `list_price` float DEFAULT '0',
  `short_description` text,
  `full_description` text,
  `in_stock` bit(1) DEFAULT b'0',
  `enabled` bit(1) NOT NULL DEFAULT b'0',
  `discount_percent` float DEFAULT '0',
  `created_at` datetime(6) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `brand_id` int DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_8qwq8q3hk7cxkp9gruxupnif5` (`alias`),
  UNIQUE KEY `UK_o61fmio5yukmmiqgnxf8pnavn` (`name`),
  KEY `FKa3a4mpsfdf4d2y6r8ra3sc8mv` (`brand_id`),
  KEY `FKog2rp4qthbtt2lfyhfo32lsw9` (`category_id`),
  FULLTEXT KEY `product_FTS` (`name`,`short_description`,`full_description`),
  CONSTRAINT `FKa3a4mpsfdf4d2y6r8ra3sc8mv` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`),
  CONSTRAINT `FKog2rp4qthbtt2lfyhfo32lsw9` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Dell Inspiron 7000 14\" FHD 2-in-1 Touchscreen Laptop | AMD Ryzen 5 5500U ( Beat i7-1165G7)','Dell--Inspiron--7000--14\"--FHD--2-in-1--Touchscreen--Laptop--|--AMD--Ryzen--5--5500U--(--Beat--i7-1165G7)','main.jpg',642,650,'<div>8GB RAM | 256GB SSD | Backlit Keyboard | Windows 11 Home | Blue<br></div>','【RAM &amp; Storage】This computer comes with 8GB RAM and 256GB SSD. 【AMD Ryzen 5 5500U Processor 】The AMD Ryzen 5 5500U is a processor for thin and light laptops based on the Lucienne generation. The R5 5500U integrates six cores based on the Zen 2 microarchitecture. They are clocked at 2.1 (guaranteed base clock) to 4 GHz (Turbo) and support SMT / Hyperthreading (12 threads). The chip is manufactured on the modern 7 nm TSMC process. Compared to the older and similar Ryzen 5 4500U (Renoir), the Ryzen 5 5500U offers Hyperthreading. 【14 inches 1920 x 1080 (Full HD) touchscreen display 】Equipped with a FHD IPS touch screen, the border-less visual effect minimizes distractions offering better cinema, document and web browsing experience 【Other Features】Integrated AMD Radeon Graphic, 65watts 4-cell Lithium-ion battery , backlit keyboard, Fingerprint reader, Privacy camera , 12.66x8.32x0.71inch, 3.31lbs. 【Operating System】Windows 11 home OS<br>',_binary '',_binary '',3.32,NULL,'2023-09-30 21:36:18.185020',4,6),(2,'Apple 2020 MacBook Air Laptop M1 Chip','Apple--2020--MacBook--Air--Laptop--M1--Chip','main.jpg',1195.95,1195.95,'<div>13” Retina Display, 8-Core CPU, 7-Core GPU, 8GB RAM, 512GB SSD Storage, Backlit Keyboard, FaceTime HD Camera. Works with iPhone/iPad; Space Gray<br></div>','<div>MacBook AirPower. It\'s in the Air.Our thinnest, lightest notebook, completely transformed by the Apple M1 chip. CPU speeds up to 3.5x faster. GPU speeds up to 5x faster. Our most advanced Neural Engine for up to 9x faster machine learning. The longest battery life ever in a MacBook Air. And a silent, fanless design. This much power has never been this ready to go.Small chip. Giant leap.It\'s here. Our first chip designed specifically for Mac. Packed with an astonishing 16 billion transistors, the Apple M1 system on a chip (SoC) integrates the CPU, GPU, Neural Engine, I/O, and so much more onto a single tiny chip. With incredible performance, custom technologies, and industry-leading power efficiency, M1 is not just a next step for Mac - it\'s another level entirely.CPU8-core CPU Devours tasks. Sips battery.M1 has the fastest CPU we\'ve ever made. With that kind of processing speed, MacBook Air can take on new extraordinarily intensive tasks like professional-quality editing and action-packed gaming. But the 8-core CPU on M1 isn\'t just up to 3.5x faster than the previous generation - it balances high-performance cores with efficiency cores that can still crush everyday jobs while using just a tenth of the power.All-day battery lifeUp to 18 hours of battery life. That\'s 6 more hours, free of charge.Thermal efficiency No fan. No noise. Just Air.<br></div>',_binary '',_binary '',0,NULL,'2023-09-30 21:25:06.423379',1,6),(3,'acer Swift 3 Thin & Light Laptop','acer--Swift--3--Thin--&--Light--Laptop','main.jpg',678,680,'<div>14\" FHD Display, Intel EVO Core i7-1165G7 Processor, 8GB RAM, 512GB NVMe SSD, Wi-Fi 6, Fingerprint Reader, Backlit Keyboard, Windows 11 Home<br></div>','<div>[Processor]:Intel EVO Core i7-1165G7 Quad-core 2.80 GHz up to 4.70 GHz (turbo);Intel Iris Xe Graphics supporting Microsoft DirectX 12. [Display]:14\" Full HD (1920 x 1080) IPS Widescreen LED-backlit 100% sRGB Display; Pure Silver; Intel Wireless Wi-Fi 6 | Acer Bio-Protection Fingerprint Solution | Up to 16 Hours Battery Life. [Memory&amp; Hard drive]:8GB RAM to smoothly run multiple applications and browser tabs all at once,512GB Solid State Drive ideal for faster bootup and data transfer. [Operating System]:Windows 11 Home; At 0.63-inches thin &amp; only 2.65 pounds, this slender laptop can go anywhere and do so with grace. [Ports &amp; Slots]:1 USB 3.2 Gen 2 Type C (up to 10 Gbps) with DisplayPort over USB Type C, Thunderbolt 4, and USB Charging;1 USB 3.2 Gen 1 (featuring power-off charging);1 USB 2.0;1 HDMI 2.0;1 Mic/Headphone combo jack.<br></div>',_binary '',_binary '',12,NULL,'2023-09-30 21:24:31.030244',10,6),(4,'Acer Aspire Vero AV15-51-7617 Green PC ','Acer--Aspire--Vero--AV15-51-7617--Green--PC--','main.jpg',677,680,'<div>15.6\" FHD IPS 100% sRGB-Display | 11th Gen Intel Core i7-1195G7 | Intel Iris Xe Graphics | 16GB DDR4 | 512GB NVMe SSD | Wi-Fi 6 | PCR Materials | Vero-Sleeve<br></div>','<div>Intel Core i7-1195G7 Processor - up to 5.0 GHz, 4 cores, 8 threads, 12 MB Intel Smart Cache 802.11ax Wi-Fi 6 | Backlit Keyboard | Up to 7.0 Hours Battery Life (MobileMark 2018) | Up to 9.5 Hours Battery Life (Video Playback) | EPEAT Silver Certified | Includes Acer Vero Protective Sleeve | Comes With Windows 11 15.6\" Full HD (1920 x 1080) Widescreen LED-backlit IPS Display (100% sRGB &amp; 300nit Brightness) | Intel Iris Xe Graphics | 16GB DDR4 Memory | 512GB NVMe SSD 1 - USB 3.2 (Type-C) Gen 1 port (up to 5 Gbps) | 2 - USB 3.2 Gen 1 Ports (one with Power-off Charging) | 1 - USB 2.0 Port | 1 - HDMI 2.0 Port with HDCP support | 1 - Ethernet (RJ-45) Port Acer Bio-Protection Fingerprint Solution, featuring Computer Protection and Windows Hello Certification Earthion is our mission to help tackle our generation’s environmental challenges through innovative and integrated solutions<br></div>',_binary '',_binary '',1,NULL,'2023-10-13 22:44:57.899397',10,6),(7,'Logitech MX Keys Mini for Mac Minimalist Wireless Illuminated Keyboard','Logitech--MX--Keys--Mini--for--Mac--Minimalist--Wireless--Illuminated--Keyboard','main.jpg',78.49,99.99,'<div>Compact, Bluetooth, Backlit Keys, USB-C, for MacBook Pro, Macbook Air, iMac, iPad - With Free Adobe Creative Cloud Subscription<br></div>','Mastered for Mac: MX Keys Mini for Mac is optimized for macOS, iPadOS(2), and iOS(2) and features a keyboard with a layout for Mac for maximum productivity Designed for Mac: Compatible with Apple MacBook Pro, MacBook Air, iMac &amp; iPad models Perfect Stroke Typing with Smart Keys (1): Type on keys shaped for your fingertips, with voice to text Dictation (4), Mic Mute/Unmute, and Emoji keys Mini Size, Mighty Powerful: A layout designed for effortless precision, with a minimalist form for an ergonomic keyboard that\'s portable and can travel wherever you get work done Smart Illumination: The wireless keyboard\'s backlit keys light up the moment your hands approach, and automatically adjust to suit changing lighting conditions Multi Mac Connectivity: Pair MX Keys Mini for Mac compact keyboard with multiple Mac computers and iPads via Bluetooth Low Energy and switch between them seamlessly USB-C Rechargeable: MX Keys Mini for Mac Bluetooth keyboard stays powered up to 10 days on a full charge or up to 5 months with the backlighting turned off(3) Plastic parts in MX Keys Mini for Mac include certified post consumer recycled plastic (Pale Gray: 12%(5)). Certified carbon neutral.<br>',_binary '',_binary '',22,NULL,'2023-10-01 00:01:30.753023',20,16),(8,'Lenovo IdeaCentre AIO 5i - 2022 - All-in-One Desktop','Lenovo--IdeaCentre--AIO--5i-----2022-----All-in-One--Desktop','main.jpg',1249.99,1250,'<div>27\" QHD Touch Display - 5MP + IR Camera - Windows 11 Home - 8GB Memory - 256 GB Storage - Intel Core i7-12700H - Mouse &amp; Keyboard Included<br></div>','Create, work, or watch Netflix with crystal clarity from any angle with an exceptionally high-quality QHD IPS 27″ display featuring three thin bezels for more screen and the complete color accuracy of 99% sRGB. Enjoy high quality audio: With two built-in JBL certified 5W speakers, you can stream Spotify and fill a spacious room with fatter, resounding audio three decibels louder than previous generations and enriched with fuller bass. Superior performance: Video chat with friends, share your vacation photos, and explore all your open browser tabs all at once, thanks to the Intel 12th Gen Core processor with integrated Intel Iris Xe graphics. IR webcam for added protection: Effortlessly login in with facial recognition with the 5MP infrared camera which can dim the screen when you look away, adjust the screen’s brightness to ambient lighting or respond to hand gestures. Work seamlessly: View both the screens of your smartphone and AIO conveniently by docking your phone on the stand. Amplify the sound of your favorite music list on the allin-one’s speaker.<br>',_binary '',_binary '',1,NULL,'2023-09-30 21:37:02.244025',2,5),(9,'Asus ROG Strix B550-F Gaming WiFi II AMD AM4 (3rd Gen Ryzen) ATX Motherboard','Asus--ROG--Strix--B550-F--Gaming--WiFi--II--AMD--AM4--(3rd--Gen--Ryzen)--ATX--Motherboard','main.jpg',180,189.99,'<div>PCIe 4.0,WiFi 6E, 2.5Gb LAN, BIOS Flashback, HDMI 2.1, Addressable Gen 2 RGB Header and Aura Sync<br></div>','<div>AM4 socket: Ready for AMD Ryzen 3000 and 5000 series, plus 5000 and 4000 G-series desktop processors.Bluetooth v5.2 Best gaming connectivity: PCIe 4.0-ready, dual M.2 slots, USB 3.2 Gen 2 Type-C, plus HDMI 2.1 and DisplayPort 1.2 output Smooth networking: On-board WiFi 6E (802.11ax) and Intel 2.5 Gb Ethernet with ASUS LANGuard Robust power solution: 12+2 teamed power stages with ProCool power connector, high-quality alloy chokes and durable capacitors Renowned software: Bundled 60 days AIDA64 Extreme subscription and intuitive UEFI BIOS dashboard<br></div>',_binary '',_binary '',1,NULL,'2023-09-30 21:28:48.979070',9,30),(10,'Apple iMac MK472LL/A 27-Inch Retina 5K Desktop','Apple--iMac--MK472LL/A--27-Inch--Retina--5K--Desktop','main.jpg',1054.95,1054.95,'3.2GHz quad-core Intel Core i5 Turbo Boost up to 3.6GHz 8GB Onboard Memory (Configurable up to 32GB) 1TB Fusion Drive','<div>AMD Radeon R9 M390 graphics processor with 2GB of GDDR5 memory 27\" Retina 5K Display 5120 x 2880 Resolution 3.2 GHz Intel Core i5 8GB of 1867 MHz DDR3 Onboard Memory 1TB Fusion Drive AMD Radeon R9 M390 graphics processor with 2GB of GDDR5 memory 802.11ac Wi-Fi, Bluetooth 4.0 2x Thunderbolt 2 and 4x USB 3.0 Ports FaceTime HD Camera, Dual Mics, Speakers Mac OS X El Capitan<br></div>',_binary '',_binary '',0,NULL,'2023-09-30 21:27:34.816220',1,5),(11,'Apple iMac (21.5-inch, 8GB RAM, 1TB Storage) - Silver (Previous Model)','Apple--iMac--(21.5-inch,--8GB--RAM,--1TB--Storage)-----Silver--(Previous--Model)','main.jpg',446.2,446.2,'<div>21.5-inch, 8GB RAM, 1TB Storage) - Silver (Previous Model<br></div>','<div>21. 5-Inch (diagonal) 1920-by-1080 resolution display Stunning 5-mm-thin design Dual-core 7th-generation Intel Core i5 Processor Intel Iris Plus Graphics 640 Two Thunderbolt 3 (USB-C) ports 802.11AC Wi-Fi Magic mouse 2 Magic keyboard macOS, inspired by pros but designed for everyone, with dark mode, stacks, easier screenshots, new built-in apps, and more<br></div>',_binary '',_binary '',0,NULL,'2023-09-30 21:26:36.225464',1,5),(12,'Never Run Out Kryptall K-i-Phone 14 Pro','Never--Run--Out--Kryptall--K-i-Phone--14--Pro','main.jpg',4799.95,4799.95,'<div>Never Run Out Kryptall K-i-Phone 14 Pro Encrypted Kryptall K-iPhone Secure Mobile Cellphone Factory Unlocked Encrypted Smartphone Anti-Surveillance Secure Phone<br></div>','A TRULY SECURED GLOBAL NETWORK - When privacy is utmost important, contact Kryptall. No other provider has a patent pending user initiated system that guarantee your privacy like Kryptall. TOTAL FREEDOM - Call anyone, anywhere in the world within the Kryptall network. Be certain your calls are safe, not recorded or tapped to. Full encryption end to end. INDUSTRY STANDARD ENCRYPTION - Suitable for use by the military, heads of states, fortune 500 companies, Executives and privacy Advocates throughout the word. SAFE AND SECURED - Kryptall starts with hardened, modifies, encrypted VoIP or cellular phone that provides a secure. 256-bit AES, encrypted VoiP call ends to end through a secured network. FULL ALL YEAR SUPPORT -Whenever you need an update, support of any kind, troubleshooting or an upgrade, we are here for you. A dedicated professional will always be there to help.<br>',_binary '',_binary '',5.4,NULL,'2023-09-30 22:07:00.768613',1,15),(13,'Apple iPhone X, US Version','Apple--iPhone--X,--US--Version','main.jpg',218,218.29,'<div>US Version, 64GB, Silver - Unlocked (Renewed)<br></div>','<div>- This pre-owned product has been professionally inspected, tested and cleaned by Amazon qualified vendors. It is not certified by Apple.</div><div><span style=\"font-size: var(--bs-body-font-size); font-weight: var(--bs-body-font-weight); text-align: var(--bs-body-text-align);\">- This product is in \"Good condition\". The screen has no scratches; body shows light scratches barely visible from 12 inches away.</span></div><div>- This product will have a battery that exceeds 80% capacity relative to new.&nbsp;</div><div>- Accessories may not be original, but will be compatible and fully functional. Product may come in generic box.&nbsp;</div><div>- Product will come with a SIM removal tool, a charger and a charging cable. Headphone and SIM card are not included.&nbsp;</div><div>- This product is eligible for a replacement or refund within 90-day of receipt if it does not work as expected.&nbsp;</div><div>- Refurbished phones are not guaranteed to maintain their waterproof sea<br></div>',_binary '',_binary '',0,NULL,'2023-09-30 21:23:00.320657',1,4),(14,'SAMSUNG Galaxy Z Flip 5 Cell Phone','SAMSUNG--Galaxy--Z--Flip--5--Cell--Phone','main.jpg',999.99,999.99,'<div>Factory Unlocked Android Smartphone, 256GB, Compact, Foldable Design, One-Hand Control, Best Selfies, Full Cover Screen, Hands-Free Use, US Version, 2023, Lavender<br></div>','PURSE, POCKET &amp; PALM PERFECT: Flex the pocket-perfect and powerful Galaxy Z Flip5; With its innovative design, this compact phone is packed with a big personality DO MORE SINGLE-HANDEDLY: With Flex Window, the large cover screen you can use while compact, Galaxy Z Flip5 single-handedly takes convenience to a whole new level; Simply reply to texts, change songs and snap photos with just one hand SHOW OFF YOUR BEST SELFIE: Meet your new selfie bestie; Find the perfect angle with a cover screen that lets you clearly preview pics live before capturing; Then, snap your share-worthy shot with the best camera on Galaxy Z Flip5 LARGE SCREEN, EASIER ACCESS: With customizable widgets and a full cover screen, Galaxy Z Flip5 is always open — even when it’s closed; Complete tasks in a flash, like sending texts, answering calls, accessing your calendar and even snapping selfies PERSONALIZED FROM THE OUTSIDE IN: Unfold ultimate personalization with Flex Window on Galaxy Z Flip5; Decorate your cover screen with cute pet selfies, unique frames and customizable clock styles; Plus, access your favorite widgets in a flash HOLD A CONVERSATION, NOT YOUR PHONE: Put your Z Flip5 in Flex Mode and do more at once with hands-free video calling; Catch up with family while fixing your favorite recipe, or get ready for a night out while chatting with friends MULTIPLE ANGLES. PERFECT SHOTS: Take stunning selfies hands-free with Galaxy Z Flip5 open or closed; Capture more angles more ways — either in Flex Mode or with the full cover screen that allows you to use the best camera on Z Flip5<br>',_binary '',_binary '',0,NULL,'2023-09-30 21:40:25.178382',3,4),(15,'Tech 21 Evo Clear case for iPhone 15 Plus','Tech--21--Evo--Clear--case--for--iPhone--15--Plus','main.jpg',49.99,53,'<div>Compatible with MagSafe - Impact Protection Case - Clear<br></div>','<div>CERTIFIED DROP PROTECTION: Shock-absorbing multi drop phone protection up to 12ft – independently tested using methods approved by the UK’s National Physical Laboratory</div><div>UV-RESISTANT: Embedded in the case, our UV agent protects against material breakdown and yellowing&nbsp;</div><div>MAGSAFE COMPATIBLE: Embedded with a magnetic ring, this case is compatible with all your Apple MagSafe accessories, from battery chargers to car holders&nbsp;</div><div>ENHANCED CAMERA PROTECTION: Subtle raised bevel detailing around the camera lenses ensures they’re kept scratch free DESIGNED: for iPhone 15 Plus<br></div>',_binary '',_binary '',2,NULL,'2023-10-01 19:48:41.253451',21,16),(16,'OtterBox COMMUTER SERIES Case for iPhone 8 PLUS & iPhone 7 PLUS (ONLY)','OtterBox--COMMUTER--SERIES--Case--for--iPhone--8--PLUS--&--iPhone--7--PLUS--(ONLY)','main.jpg',49.95,49.95,'<div>Frustration Free Packaging - BALLET WAY (PINK SALT/BLUSH)<br></div>','<div>Compatible with iPhone 8 PLUS &amp; iPhone 7 PLUS (ONLY)&nbsp;</div><div>DOES NOT come with a screen protector&nbsp;</div><div>Thin, lightweight 2-piece case provides protection against drops, bumps and shock (port covers block entry of dust and debris)&nbsp;</div><div>Slides easily in and out of pockets Includes OtterBox limited lifetime warranty (see website for details)<br></div>',_binary '',_binary '',60,NULL,'2023-10-01 00:06:26.304279',21,16);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `name` varchar(64) NOT NULL,
                         `description` varchar(564) NOT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `UK_ofx66keruapi6vyqpv6f2or37` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 ;
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `description` varchar(564) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ofx66keruapi6vyqpv6f2or37` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'Admin','manage everything'),(2,'Salesperson','manage product price, customers, shipping, orders and sales report'),(3,'Editor','manage categories, brands, products, articles and menus'),(4,'Shipper','view products, view orders and update order status'),(5,'Assistant','manage questions and reviews');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `settings`
--

DROP TABLE IF EXISTS `settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `settings` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `category` varchar(255) DEFAULT NULL,
                            `key` varchar(255) NOT NULL,
                            `value` varchar(1024) NOT NULL,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `UK_lxci3mkgc9fvvdnq2qlx189cl` (`key`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 ;

  `id` int NOT NULL AUTO_INCREMENT,
  `category` varchar(255) DEFAULT NULL,
  `key` varchar(255) NOT NULL,
  `value` varchar(1024) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_lxci3mkgc9fvvdnq2qlx189cl` (`key`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `settings`
--

LOCK TABLES `settings` WRITE;
/*!40000 ALTER TABLE `settings` DISABLE KEYS */;
INSERT INTO `settings` VALUES (1,'GENERAL','SITE_NAME','MyShop'),(2,'GENERAL','SITE_LOGO','/site-logo/logo2.png'),(3,'GENERAL','COPYRIGHT','&copy; 2023 My Shop Copyright All Rights Reserved'),(4,'MAIL_SERVER','MAIL_HOST','smtp.gmail.com'),(5,'MAIL_SERVER','MAIL_PORT','587'),(6,'MAIL_SERVER','MAIL_USERNAME','yazaonion@gmail.com'),(7,'MAIL_SERVER','MAIL_PASSWORD','xbyjexjttfsjbtdq'),(8,'MAIL_SERVER','MAIL_SENDER_NAME','My Shop'),(9,'MAIL_SERVER','MAIL_AUTH','true'),(10,'MAIL_SERVER','MAIL_SECURED','true'),(11,'MAIL_SERVER','MAIL_FROM','yazaonion@gmail.com'),(12,'MAIL_TEMPLATE','CUSTOMER_VERIFY_SUBJECT','Verify Your Email to Continue Shopping'),(13,'MAIL_TEMPLATE','CUSTOMER_VERIFY_CONTENT','<div><div>Dear [[name]],</div><div>In order to continue shopping, you must verify your email. Please click \"VERIFY\" to confirm your email address.</div><div><br></div><div><br></div><a href=\"[[URL]]\" target=\"_self\"><b>VERIFY</b></a><div><font color=\"#0000ff\"><u><b></b></u></font></div><div><br></div><div><br></div><div>Sincerely,&nbsp;</div><div><i><font color=\"#ff0000\"><b>My Shop</b></font></i></div></div>');
/*!40000 ALTER TABLE `settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `email` varchar(128) NOT NULL,
                         `enabled` bit(1) NOT NULL,
                         `first_name` varchar(128) NOT NULL,
                         `last_name` varchar(128) NOT NULL,
                         `password` varchar(64) NOT NULL,
                         `photo` varchar(256) DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 ;

  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(128) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `first_name` varchar(128) NOT NULL,
  `last_name` varchar(128) NOT NULL,
  `password` varchar(64) NOT NULL,
  `photo` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'nam@codejava.net',_binary '','John','Dou','$2a$10$fRGr.SSBDL62DfUYa7seu.6GavHU9toNDbpLt5PVGd2hGAv1hbvNC','images.jpg'),(3,'aecllc.bnk@gmail.com',_binary '\0','Bruce','Kitchell','$2a$10$GINThwCjVZAbGnmOe9BIeuDuvDlyfuwZrg/Rsmrjs1Lsq2pnXtO/S','Bruce Kitchell.png'),(4,'muhammad.evran13@gmail.com',_binary '','Muhammad','Evran','$2a$10$UcHWHC72azPVZJb5Ky.Yy.X695WGf1ZkkGMS3WL3B9WqWf2dQD04G','Muhammad Evran.png'),(7,'nathihsa@gmail.com',_binary '','Nathi','Sangweni','$2a$10$WyHmQiXCSYuHcGeg8eFWvOScqzSgg88MmqpajPdzSkLsvZjT3tKC.','Nathi_Sangweni.png'),(9,'bfeeny238@hotmail.com',_binary '','Bill','Feeny','$2a$10$3sH0v..zpjwA8ux5/q.OAeu0HgmSdMj8VzMWzhwwBDkE8wOISsUyi','Bill Feeny.png'),(10,'redsantosph@gmail.com',_binary '','Frederick','delos Santos','$2a$10$KXHmKpE6YB/0sjiy3fkMv.muKyxqvOXE6jVeaPu8KEaExx62ZmmNe','Frederick Santos.png'),(12,'maytassatya@hotmail.com',_binary '','Satya','Narayana','$2a$10$R7EJcaYijjJo/IVk6c1CieBML2uP3RAKMVlCxylPAePlCfJsX7OOy','Satya Narayana.png'),(13,'matthewefoli@gmail.com',_binary '','Matthew','Efoli','$2a$10$WGn1hd9YEIvZRVon9Y87ieAHtBOkcbRS2tLJM1TJbsR1muEXMK7ny','Matthew.png'),(14,'davekumara2@gmail.com',_binary '','Dave','Kumar','$2a$10$5ebeZu18V5RheieYqpl/LORCN41E3H7yvxKqEwtq5Zq2JVw.E9dva','Dave Kumar.png'),(15,'jackkbruce@yahoo.com',_binary '','Jack','Bruce','$2a$10$a6iyIHRj8DNpu15obVHTSOGcLe4IfpBcD4iEEJesWaFpBQvRoF2da','Jack Bruce.png'),(16,'zirri.mohamed@gmail.com',_binary '','Mohamed','Zirri','$2a$10$TmvyH1AoyDqRmQ4uC8NAZOOV29CPEDGuxVsHLP1cJoHQGr78L4kjW','Mohamed Zirri.jpg'),(18,'aliraza.arain.28@gmail.com',_binary '','Ali','Raza','$2a$10$PISZ2KitSbhE4/Z3dtIGk.WUi2ILiDl4PzRUDEQSp5BJIxcdcPq4G','Ali Raza.png'),(19,'isaachenry2877@gmail.com',_binary '','Isaac','Henry','$2a$10$CtmhrOz/AhDoCpKbeYl8O.0ngCFMukcznNZq7.YcHrkRyKpBG8Zca','Isaac Henry.jpg'),(20,'s.stasovska82@hotmail.com',_binary '','Svetlana','Stasovska','$2a$10$fcN2cNa7vB.78QnmzfNZEeUBkrwUaM.bVK3iDB.KFQlR15DwL7QZy','Svetlana Stasovska.png'),(21,'mikegates2012@gmail.com',_binary '','Mike','Gates','$2a$10$spyKH4GZio5rat1nL5IiluYRAlpbtJomyFbsxtnr/wfZjEuRbEl4y','Mike Gates.png'),(22,'pedroquintero67@gmail.com',_binary '\0','Pedro','Quintero','$2a$10$UPX5EwZw0MyBvbe.7mxg2u8GOl/4KgaUU40iSjr1PLFYvhu35fMmu','Pedro Quintero.png'),(24,'abc@example.com',_binary '','Utku','John','$2a$10$k8Ufy6T9Wg3V5eRsMiEJDeVmZ0lXHkxUa4qYrblTcpy1cbgQbB3.2',NULL),(26,'admin@gmail.com',_binary '','nam','vu hoai','$2a$10$kXZhxkZ72GDTgnmLoWW9hOOqTDYHAeHKJw0agQ/NIW4eO0ujmAopa','download.jpg'),(27,'salesperson@gmail.com',_binary '','Katti','Ma','$2a$10$wyFqrfBWFX5Yh6kpEDypneeJ1DsQ4a9fJLc6NIh7UCRZdafr1fwy2','images.jpg'),(28,'editor@gmail.com',_binary '','Utku','Owe','$2a$10$zcox3v64.wvipUi3QUeyaeXMLW3gd.S/On6z7gSmd1GjefMbq6CrK','admin.jpg'),(29,'shipper@gmail.com',_binary '','Baki','Hanma','$2a$10$TkFpdWfLg69djutxzAWaAeMF6rwnOKFoulzAfnUEGHEsP3Mxn0eX2','download.jpg'),(30,'kate@gmail.com',_binary '','Kate','Quew','$2a$10$XYu3Wu9ywsfj8r4H6Xqobu/bFTO9TP.13I5PSgxwQOwMPTd7sjLZ.','user1.jpg');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_roles` (
                               `user_id` int NOT NULL,
                               `role_id` int NOT NULL,
                               PRIMARY KEY (`user_id`,`role_id`),
                               KEY `FKj6m8fwv7oqv74fcehir1a9ffy` (`role_id`),
                               CONSTRAINT `FK2o0jvgh89lemvvo17cbqvdxaa` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                               CONSTRAINT `FKj6m8fwv7oqv74fcehir1a9ffy` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKj6m8fwv7oqv74fcehir1a9ffy` (`role_id`),
  CONSTRAINT `FK2o0jvgh89lemvvo17cbqvdxaa` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKj6m8fwv7oqv74fcehir1a9ffy` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` VALUES (1,1),(26,1),(3,2),(9,2),(10,2),(12,2),(13,2),(19,2),(20,2),(27,2),(4,3),(7,3),(15,3),(18,3),(20,3),(28,3),(14,4),(15,4),(16,4),(18,4),(24,4),(29,4),(1,5),(14,5),(19,5),(20,5),(21,5),(22,5),(30,5);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-15 23:50:01
