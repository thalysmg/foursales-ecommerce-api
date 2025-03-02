-- MySQL dump 10.13  Distrib 8.0.41, for Linux (x86_64)
--
-- Host: localhost    Database: foursales-ecommerce
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `DATABASECHANGELOG`
--

DROP TABLE IF EXISTS `DATABASECHANGELOG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `DATABASECHANGELOG` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DATABASECHANGELOG`
--

LOCK TABLES `DATABASECHANGELOG` WRITE;
/*!40000 ALTER TABLE `DATABASECHANGELOG` DISABLE KEYS */;
INSERT INTO `DATABASECHANGELOG` VALUES ('202502031828_1','thalys.gadelha','db/changelog/202502031827_create_table_perfil_e_usuario.xml','2025-02-03 23:14:07',1,'EXECUTED','9:223963bfa5083347549e5dcd38111175','createTable tableName=perfil','',NULL,'4.29.2',NULL,NULL,'8624447348'),('202502031828_2','thalys.gadelha','db/changelog/202502031827_create_table_perfil_e_usuario.xml','2025-02-03 23:14:07',2,'EXECUTED','9:d594575929cdba58952b9eada6e18d77','createTable tableName=usuario','',NULL,'4.29.2',NULL,NULL,'8624447348'),('202502031828_3','thalys.gadelha','db/changelog/202502031827_create_table_perfil_e_usuario.xml','2025-02-03 23:14:07',3,'EXECUTED','9:a88634f3cb10f2c5de9327803d765c39','sql','',NULL,'4.29.2',NULL,NULL,'8624447348'),('202502031941_1','thalys.gadelha','db/changelog/202502031941_create_produto_e_pedido.xml','2025-02-04 00:18:59',4,'EXECUTED','9:12b170dc7631f789360703be61c8959d','createTable tableName=produto','',NULL,'4.29.2',NULL,NULL,'8628339418'),('202502031941_2','thalys.gadelha','db/changelog/202502031941_create_produto_e_pedido.xml','2025-02-04 00:18:59',5,'EXECUTED','9:746f2229031e9ebcbf6efbc05554dfde','createTable tableName=pedido','',NULL,'4.29.2',NULL,NULL,'8628339418'),('202502031941_3','thalys.gadelha','db/changelog/202502031941_create_produto_e_pedido.xml','2025-02-04 00:18:59',6,'EXECUTED','9:6fe79307aadd2d4a8fb39f3383fbb179','createTable tableName=pedido_produto','',NULL,'4.29.2',NULL,NULL,'8628339418'),('202502041854_1','thalys.gadelha','db/changelog/202502041849_insert_users.xml','2025-02-04 21:55:57',7,'EXECUTED','9:a494c8a395bb8d0057f46ac12999768d','sql','',NULL,'4.29.2',NULL,NULL,'8706157312');
/*!40000 ALTER TABLE `DATABASECHANGELOG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DATABASECHANGELOGLOCK`
--

DROP TABLE IF EXISTS `DATABASECHANGELOGLOCK`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `DATABASECHANGELOGLOCK` (
  `ID` int NOT NULL,
  `LOCKED` tinyint NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DATABASECHANGELOGLOCK`
--

LOCK TABLES `DATABASECHANGELOGLOCK` WRITE;
/*!40000 ALTER TABLE `DATABASECHANGELOGLOCK` DISABLE KEYS */;
INSERT INTO `DATABASECHANGELOGLOCK` VALUES (1,0,NULL,NULL);
/*!40000 ALTER TABLE `DATABASECHANGELOGLOCK` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedido` (
  `id` char(36) NOT NULL DEFAULT (uuid()),
  `valor_total` decimal(11,2) NOT NULL,
  `id_cliente` char(36) NOT NULL,
  `data` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_atualizacao` datetime DEFAULT NULL,
  `situacao` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cliente` (`id_cliente`),
  CONSTRAINT `fk_cliente` FOREIGN KEY (`id_cliente`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido`
--

LOCK TABLES `pedido` WRITE;
/*!40000 ALTER TABLE `pedido` DISABLE KEYS */;
INSERT INTO `pedido` VALUES ('2324b529-96ea-4587-9994-cf9cbc854a5c',3887.18,'d04a4763-e342-11ef-9911-0242ac120002','2025-02-04 19:11:05',NULL,'PENDENTE'),('234c04c6-2d6b-44f7-beac-9c2ca9fe6046',6984.10,'d048d21f-e342-11ef-9911-0242ac120002','2025-02-04 19:02:00','2025-02-04 19:05:18','PAGO'),('9faa024a-4eef-4fab-a0eb-3d6cea4308cc',349.44,'917d98a6-e284-11ef-9e24-0242ac120002','2025-02-04 00:04:17','2025-02-04 00:34:17','PAGO'),('dfa58793-1186-4110-9222-d3d169612100',1495.83,'917d98a6-e284-11ef-9e24-0242ac120002','2025-02-04 02:49:40','2025-02-04 02:59:40','PAGO'),('f2b018c2-7723-46ff-95ce-ee78755aca94',2779.44,'d04a0457-e342-11ef-9911-0242ac120002','2025-02-04 19:06:55','2025-02-04 19:09:52','PAGO');
/*!40000 ALTER TABLE `pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedido_produto`
--

DROP TABLE IF EXISTS `pedido_produto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedido_produto` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_pedido` char(36) NOT NULL,
  `id_produto` char(36) NOT NULL,
  `qtd_produto` int NOT NULL,
  `preco_unitario` decimal(11,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_produto` (`id_produto`),
  KEY `fk_pedido` (`id_pedido`),
  CONSTRAINT `fk_pedido` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id`),
  CONSTRAINT `fk_produto` FOREIGN KEY (`id_produto`) REFERENCES `produto` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido_produto`
--

LOCK TABLES `pedido_produto` WRITE;
/*!40000 ALTER TABLE `pedido_produto` DISABLE KEYS */;
INSERT INTO `pedido_produto` VALUES (1,'9faa024a-4eef-4fab-a0eb-3d6cea4308cc','32fb5eb2-464f-4eeb-89f0-e1d018d7db58',1,212.61),(2,'9faa024a-4eef-4fab-a0eb-3d6cea4308cc','8ae65ce8-2b33-4bbd-be22-108f554d2ef6',3,45.61),(3,'dfa58793-1186-4110-9222-d3d169612100','9a8a9782-0a86-4505-876c-42d27b15dd12',2,141.61),(4,'dfa58793-1186-4110-9222-d3d169612100','e1ddd133-39b5-4936-9537-1621b77d5fbb',1,1212.61),(5,'234c04c6-2d6b-44f7-beac-9c2ca9fe6046','32fb5eb2-464f-4eeb-89f0-e1d018d7db58',3,212.61),(6,'234c04c6-2d6b-44f7-beac-9c2ca9fe6046','8ae65ce8-2b33-4bbd-be22-108f554d2ef6',4,1212.61),(7,'234c04c6-2d6b-44f7-beac-9c2ca9fe6046','9a8a9782-0a86-4505-876c-42d27b15dd12',2,141.61),(8,'234c04c6-2d6b-44f7-beac-9c2ca9fe6046','e1ddd133-39b5-4936-9537-1621b77d5fbb',1,1212.61),(9,'f2b018c2-7723-46ff-95ce-ee78755aca94','32fb5eb2-464f-4eeb-89f0-e1d018d7db58',1,212.61),(10,'f2b018c2-7723-46ff-95ce-ee78755aca94','8ae65ce8-2b33-4bbd-be22-108f554d2ef6',1,1212.61),(11,'f2b018c2-7723-46ff-95ce-ee78755aca94','9a8a9782-0a86-4505-876c-42d27b15dd12',1,141.61),(12,'f2b018c2-7723-46ff-95ce-ee78755aca94','e1ddd133-39b5-4936-9537-1621b77d5fbb',1,1212.61),(13,'2324b529-96ea-4587-9994-cf9cbc854a5c','8ae65ce8-2b33-4bbd-be22-108f554d2ef6',2,53.87),(14,'2324b529-96ea-4587-9994-cf9cbc854a5c','9a8a9782-0a86-4505-876c-42d27b15dd12',1,141.61),(15,'2324b529-96ea-4587-9994-cf9cbc854a5c','e1ddd133-39b5-4936-9537-1621b77d5fbb',3,1212.61);
/*!40000 ALTER TABLE `pedido_produto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `perfil`
--

DROP TABLE IF EXISTS `perfil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `perfil` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(60) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfil`
--

LOCK TABLES `perfil` WRITE;
/*!40000 ALTER TABLE `perfil` DISABLE KEYS */;
INSERT INTO `perfil` VALUES (1,'ADMIN'),(2,'USER');
/*!40000 ALTER TABLE `perfil` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produto`
--

DROP TABLE IF EXISTS `produto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produto` (
  `id` char(36) NOT NULL DEFAULT (uuid()),
  `nome` varchar(255) NOT NULL,
  `preco` decimal(11,2) NOT NULL,
  `descricao` text NOT NULL,
  `categoria` varchar(100) NOT NULL,
  `qtd_estoque` int NOT NULL DEFAULT '0',
  `data_cadastro` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_atualizacao` datetime DEFAULT NULL,
  `id_usuario_cadastro` char(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_usuario_cadastro` (`id_usuario_cadastro`),
  CONSTRAINT `fk_usuario_cadastro` FOREIGN KEY (`id_usuario_cadastro`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produto`
--

LOCK TABLES `produto` WRITE;
/*!40000 ALTER TABLE `produto` DISABLE KEYS */;
INSERT INTO `produto` VALUES ('32fb5eb2-464f-4eeb-89f0-e1d018d7db58','Produto 3',212.61,'Descricao Produto 3','ELETRONICOS',25,'2025-02-03 22:06:29','2025-02-04 19:09:52','917cd691-e284-11ef-9e24-0242ac120002'),('8ae65ce8-2b33-4bbd-be22-108f554d2ef6','Produto 1',53.87,'Descricao Produto 1 Atualizado','ELETRONICOS',2,'2025-02-03 21:41:59','2025-02-04 19:09:52','917cd691-e284-11ef-9e24-0242ac120002'),('9a8a9782-0a86-4505-876c-42d27b15dd12','Produto 2',141.61,'Descricao Produto 2','ELETRONICOS',15,'2025-02-03 22:05:32','2025-02-04 19:09:52','917cd691-e284-11ef-9e24-0242ac120002'),('e1ddd133-39b5-4936-9537-1621b77d5fbb','Produto 4',1212.61,'Descricao Produto 4','PERIFERICOS',7,'2025-02-04 00:48:50','2025-02-04 19:09:52','917cd691-e284-11ef-9e24-0242ac120002');
/*!40000 ALTER TABLE `produto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` char(36) NOT NULL DEFAULT (uuid()),
  `nome` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `senha` char(60) NOT NULL,
  `id_perfil` bigint NOT NULL,
  `data_cadastro` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ativo` tinyint NOT NULL DEFAULT '0',
  `data_atualizacao` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  KEY `fk_perfil` (`id_perfil`),
  CONSTRAINT `fk_perfil` FOREIGN KEY (`id_perfil`) REFERENCES `perfil` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES ('917cd691-e284-11ef-9e24-0242ac120002','Administrador','administrador@test.com','$2a$10$XtbqDI8DI9f2MphcdklGJenjjTv3iebAxyjtcddAIFWPCOAoARhNy',1,'2025-02-03 23:14:07',1,NULL),('917d98a6-e284-11ef-9e24-0242ac120002','Usuário','usuario@test.com','$2a$10$XtbqDI8DI9f2MphcdklGJenjjTv3iebAxyjtcddAIFWPCOAoARhNy',2,'2025-02-03 23:14:07',1,NULL),('d048d21f-e342-11ef-9911-0242ac120002','Cliente 1','cliente1@test.com','$2a$10$XtbqDI8DI9f2MphcdklGJenjjTv3iebAxyjtcddAIFWPCOAoARhNy',2,'2025-02-04 21:55:57',1,NULL),('d04a0457-e342-11ef-9911-0242ac120002','Cliente 2','cliente2@test.com','$2a$10$XtbqDI8DI9f2MphcdklGJenjjTv3iebAxyjtcddAIFWPCOAoARhNy',2,'2025-02-04 21:55:57',1,NULL),('d04a4763-e342-11ef-9911-0242ac120002','Cliente 3','cliente3@test.com','$2a$10$XtbqDI8DI9f2MphcdklGJenjjTv3iebAxyjtcddAIFWPCOAoARhNy',2,'2025-02-04 21:55:57',1,NULL);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'foursales-ecommerce'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-04 19:58:19
