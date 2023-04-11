/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.5.5-10.4.24-MariaDB : Database - ventas
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ventas` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `ventas`;

/*Table structure for table `detalle` */

DROP TABLE IF EXISTS `detalle`;

CREATE TABLE `detalle` (
  `idDetalle` int(10) NOT NULL AUTO_INCREMENT,
  `precio` float NOT NULL,
  `cantidad` int(10) NOT NULL,
  `idProducto` int(10) NOT NULL,
  `idVenta` int(10) NOT NULL,
  PRIMARY KEY (`idDetalle`),
  KEY `fk_detalle_producto` (`idProducto`),
  KEY `fk_detalle_venta` (`idVenta`),
  CONSTRAINT `fk_detalle_producto` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`),
  CONSTRAINT `fk_detalle_venta` FOREIGN KEY (`idVenta`) REFERENCES `venta` (`idVenta`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4;

/*Data for the table `detalle` */

insert  into `detalle`(`idDetalle`,`precio`,`cantidad`,`idProducto`,`idVenta`) values (1,100,1,6,3),(2,10,10,7,1),(3,10,10,7,1),(4,10,10,7,1),(5,6000,1,7,4),(6,100,1,6,4),(7,6000,1,7,5),(8,100,1,6,5),(9,6000,1,7,6),(10,100,1,6,6),(11,100,1,6,7),(12,100,1,6,8),(13,100,1,6,9),(14,6000,1,7,9),(15,100,5,6,10),(16,6000,2,7,10),(17,100,4,6,11),(18,6000,1,7,11),(19,100,1,6,12),(20,6000,1,7,12),(21,100,4,6,13),(22,6000,2,7,14),(23,6000,2,7,15),(24,6000,2,7,16),(25,6000,50,7,17),(26,50000,1,8,18),(27,50000,10,8,19),(28,50000,1,8,20),(29,50000,2,8,21),(30,234,1,13,22),(31,234,1,13,23),(32,30000,1,21,24),(33,40000,1,22,24),(34,50000,1,23,24),(35,5000,1,26,24),(36,2000,1,14,25),(37,4000,1,15,25),(38,2000,1,14,26),(39,2000,1,25,27),(40,5000,1,26,28),(41,4000,1,15,29),(42,2000,1,14,29),(43,2000,1,25,29),(44,5000,1,26,29);

/*Table structure for table `precio` */

DROP TABLE IF EXISTS `precio`;

CREATE TABLE `precio` (
  `idPrecio` int(10) NOT NULL AUTO_INCREMENT,
  `valor` float NOT NULL,
  `fechaHasta` datetime DEFAULT NULL,
  `idProducto` int(10) NOT NULL,
  PRIMARY KEY (`idPrecio`),
  KEY `pk_precio_producto` (`idProducto`),
  CONSTRAINT `pk_precio_producto` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4;

/*Data for the table `precio` */

insert  into `precio`(`idPrecio`,`valor`,`fechaHasta`,`idProducto`) values (1,12,'2022-10-17 15:20:03',4),(2,13,NULL,4),(3,10,NULL,5),(4,10,'2022-10-17 16:12:50',6),(5,100,NULL,6),(6,100,'2022-10-17 16:33:22',7),(7,6000,NULL,7),(8,50000,NULL,8),(9,30000,'2022-10-19 23:06:28',9),(10,25000,NULL,9),(11,15000,NULL,10),(12,12,NULL,11),(13,234,NULL,12),(14,234,NULL,13),(15,2000,NULL,14),(16,4000,NULL,15),(17,12,NULL,16),(18,35000,NULL,17),(19,40000,NULL,18),(20,7000,'2022-11-01 12:01:29',19),(21,7100,'2022-11-01 12:01:41',19),(22,7200,NULL,19),(23,60000,NULL,20),(24,30000,NULL,21),(25,40000,NULL,22),(26,50000,NULL,23),(27,70000,NULL,24),(28,2000,NULL,25),(29,5000,NULL,26),(30,10,'2022-11-13 20:59:56',27),(31,20,NULL,27);

/*Table structure for table `producto` */

DROP TABLE IF EXISTS `producto`;

CREATE TABLE `producto` (
  `idProducto` int(10) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `marca` varchar(45) NOT NULL,
  `tipoProducto` varchar(45) NOT NULL,
  `descripcion` varchar(200) NOT NULL,
  `stock` int(10) NOT NULL,
  `estado` int(2) NOT NULL DEFAULT 1,
  PRIMARY KEY (`idProducto`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4;

/*Data for the table `producto` */

insert  into `producto`(`idProducto`,`nombre`,`marca`,`tipoProducto`,`descripcion`,`stock`,`estado`) values (1,'intel','INTEL','CPU','primeraBd',10,0),(2,'Asdf','AMD','MOTHERBOARD','asdfasdf',1,0),(3,'Asdfasf','AMD','CPU','adfsadsf',23,0),(4,'Asdfas','GYGABYTE','CPU','SDAFASD',12,0),(5,'Nuevito','INTEL','CPU','asdfasdf',10,0),(6,'Ryzen','AMD','CPU','asdfasd',1,0),(7,'Rtx3000','NVIDIA','GPU','sadfasdfadsf',50,0),(8,'Gtx3600','NVIDIA','GPU','esto es una placa de video de ultima generacion\nde origen taiwanes ',100,0),(9,'Ryzen7','AMD','CPU','asdfasdfasfd',100,0),(10,'Corei5','INTEL','CPU','safasdfasdf',100,0),(11,'Sadf','GYGABYTE','MOTHERBOARD','sdafasdf',324,0),(12,'Sadf','ADATA','CPU','safdasdf',34,0),(13,'Asdfasdf','GYGABYTE','CPU','safsadef',2234,0),(14,'Ram4 gb','ADATA','RAM','esg',8,1),(15,'Ram 8 gb','ADATA','RAM','adsf',9,1),(16,'Asdf','GYGABYTE','MOTHERBOARD','asdfasdf',12,0),(17,'Ryzen 2200g','AMD','CPU','Procesador de segunda generacion\nNuecles: 6\nHilos: 4\nColeer: SI\nOtro:',50,1),(18,'Ryzen 5 1600af','AMD','CPU','Procesador de primera generacion\nNuecles: 8\nHilos: 12\nColeer: SI\n12 nanometros',30,1),(19,'A320m','ASROCK','MOTHERBOARD','a 320m asrock',100,1),(20,'1660 super','NVIDIA','GPU','Tarjeta grafica de nvidia',10,1),(21,'I3','INTEL','CPU','procesador intel i3',9,1),(22,'I5','INTEL','CPU','procesador intel i5',11,1),(23,'I7','INTEL','CPU','procesador intel i7',4,1),(24,'I9','INTEL','CPU','procesador intel i9',4,0),(25,'Ssd 4gb','ADATA','ALMACENAMIENTO','ssd',8,1),(26,'Ssd 4gb','KINGSTON','ALMACENAMIENTO','ssd kingston',47,1),(27,'Ryzen 5 3200g','AMD','CPU','neuva desc',10,1);

/*Table structure for table `venta` */

DROP TABLE IF EXISTS `venta`;

CREATE TABLE `venta` (
  `idVenta` int(10) NOT NULL AUTO_INCREMENT,
  `cliente` varchar(45) NOT NULL,
  `fechaVenta` datetime NOT NULL,
  `medioPago` varchar(45) NOT NULL,
  `montoTotal` float NOT NULL,
  `estado` int(2) NOT NULL DEFAULT 1,
  PRIMARY KEY (`idVenta`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4;

/*Data for the table `venta` */

insert  into `venta`(`idVenta`,`cliente`,`fechaVenta`,`medioPago`,`montoTotal`,`estado`) values (1,'juan','2022-10-19 20:16:07','EFECTIVO',180,0),(2,'manco','2022-10-19 20:18:38','PAYPAL',300,0),(3,'sadfa','2022-10-19 21:12:53','DEBITO',100,0),(4,'ASDASd','2022-10-19 21:31:15','CREDITO',6100,0),(5,'ASDF','2022-10-19 21:40:46','MERCADOPAGO',6100,0),(6,'ASDF','2022-10-19 21:43:18','CREDITO',6100,0),(7,'dsafs','2022-10-19 21:44:58','CREDITO',100,0),(8,'sdfsdaf','2022-10-19 21:46:36','EFECTIVO',100,0),(9,'sdaff','2022-10-19 21:49:39','DEBITO',6100,0),(10,'sdfa','2022-10-19 21:50:14','DEBITO',12500,0),(11,'sdaf','2022-10-19 21:50:53','CREDITO',6400,0),(12,'sdfgds','2022-10-19 21:52:00','DEBITO',6100,0),(13,'asdfsd','2022-10-19 21:52:37','CREDITO',400,0),(14,'asdf','2022-10-19 21:56:04','EFECTIVO',12000,0),(15,'sadf','2022-10-19 21:56:44','CREDITO',12000,0),(16,'sadf','2022-10-19 21:59:18','CREDITO',12000,0),(17,'sdfasf','2022-10-19 22:02:02','CREDITO',300000,0),(18,'daniel morales','2022-10-19 22:08:05','CREDITO',50000,0),(19,'david ohana','2022-10-19 22:09:01','PAYPAL',500000,0),(20,'pepito','2022-02-01 22:11:51','PAYPAL',50000,0),(21,'juancito','2022-10-19 22:47:24','PAYPAL',100000,0),(22,'juan','2022-10-28 23:04:02','CREDITO',234,0),(23,'afd','2022-10-29 00:20:16','CHEQUE',234,0),(24,'juan','2022-11-01 12:15:27','EFECTIVO',125000,1),(25,'WillMartino','2022-11-01 12:18:55','MERCADOPAGO',6000,0),(26,'staartPopjoy','2022-11-01 12:19:51','DEBITO',2000,1),(27,'Stuart Haber','2022-11-01 12:23:12','PAYPAL',2000,1),(28,'Rian Matta','2022-11-01 15:41:06','EFECTIVO',5000,1),(29,'jair bukele','2022-11-13 21:04:17','CREDITO',13000,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
