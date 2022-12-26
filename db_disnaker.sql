/*
SQLyog Community v13.1.9 (64 bit)
MySQL - 10.4.24-MariaDB : Database - db_disnaker
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_disnaker` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `db_disnaker`;

/*Table structure for table `kategori` */

DROP TABLE IF EXISTS `kategori`;

CREATE TABLE `kategori` (
  `kategori_id` int(11) NOT NULL AUTO_INCREMENT,
  `nama` varchar(50) NOT NULL,
  PRIMARY KEY (`kategori_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `kategori` */

/*Table structure for table `lowongan` */

DROP TABLE IF EXISTS `lowongan`;

CREATE TABLE `lowongan` (
  `lowongan_id` int(11) NOT NULL AUTO_INCREMENT,
  `nama` varchar(100) NOT NULL,
  `kategori_id` int(11) NOT NULL,
  `perusahaan_id` int(11) NOT NULL,
  `kuota` int(11) NOT NULL,
  `keterangan` varchar(150) NOT NULL,
  `status` tinyint(4) NOT NULL COMMENT '0=tidak aktif, 1=aktif',
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`lowongan_id`),
  KEY `kategori_id` (`kategori_id`),
  KEY `perusahaan_id` (`perusahaan_id`),
  CONSTRAINT `lowongan_ibfk_1` FOREIGN KEY (`kategori_id`) REFERENCES `kategori` (`kategori_id`),
  CONSTRAINT `lowongan_ibfk_2` FOREIGN KEY (`perusahaan_id`) REFERENCES `perusahaan` (`perusahaan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `lowongan` */

/*Table structure for table `pelatihan` */

DROP TABLE IF EXISTS `pelatihan`;

CREATE TABLE `pelatihan` (
  `pelatihan_id` int(11) NOT NULL AUTO_INCREMENT,
  `nama` varchar(100) NOT NULL,
  `kategori_id` int(11) NOT NULL,
  `kuota` int(11) NOT NULL,
  `durasi` int(11) NOT NULL,
  `pendidikan` varchar(5) NOT NULL,
  `keterangan` varchar(150) NOT NULL,
  `status` tinyint(4) NOT NULL COMMENT '0=tidak aktif, 1=aktif',
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`pelatihan_id`),
  KEY `kategori_id` (`kategori_id`),
  CONSTRAINT `pelatihan_ibfk_1` FOREIGN KEY (`kategori_id`) REFERENCES `kategori` (`kategori_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `pelatihan` */

/*Table structure for table `pendaftaran_lowongan` */

DROP TABLE IF EXISTS `pendaftaran_lowongan`;

CREATE TABLE `pendaftaran_lowongan` (
  `pl_id` int(11) NOT NULL AUTO_INCREMENT,
  `lowongan_id` int(11) NOT NULL,
  `peserta_id` int(11) NOT NULL,
  `tanggal` date NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`pl_id`),
  KEY `lowongan_id` (`lowongan_id`),
  KEY `peserta_id` (`peserta_id`),
  CONSTRAINT `pendaftaran_lowongan_ibfk_1` FOREIGN KEY (`lowongan_id`) REFERENCES `lowongan` (`lowongan_id`),
  CONSTRAINT `pendaftaran_lowongan_ibfk_2` FOREIGN KEY (`peserta_id`) REFERENCES `peserta` (`peserta_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `pendaftaran_lowongan` */

/*Table structure for table `pendaftaran_pelatihan` */

DROP TABLE IF EXISTS `pendaftaran_pelatihan`;

CREATE TABLE `pendaftaran_pelatihan` (
  `pp_id` int(11) NOT NULL AUTO_INCREMENT,
  `pelatihan_id` int(11) NOT NULL,
  `peserta_id` int(11) NOT NULL,
  `tgl_pendaftaran` date NOT NULL DEFAULT current_timestamp(),
  `tgl_wawancara` date DEFAULT NULL,
  `status_pendaftaran` tinyint(4) NOT NULL COMMENT '0=pending, 1=wawancara, 2=pelatihan, 3=selesai, 4=ditolak',
  `status_kelulusan` tinyint(4) NOT NULL COMMENT '0=pending, 1=diterima, 2=ditolak',
  PRIMARY KEY (`pp_id`),
  KEY `pelatihan_id` (`pelatihan_id`),
  KEY `peserta_id` (`peserta_id`),
  CONSTRAINT `pendaftaran_pelatihan_ibfk_1` FOREIGN KEY (`pelatihan_id`) REFERENCES `pelatihan` (`pelatihan_id`),
  CONSTRAINT `pendaftaran_pelatihan_ibfk_2` FOREIGN KEY (`peserta_id`) REFERENCES `peserta` (`peserta_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `pendaftaran_pelatihan` */

/*Table structure for table `perusahaan` */

DROP TABLE IF EXISTS `perusahaan`;

CREATE TABLE `perusahaan` (
  `perusahaan_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `alamat` varchar(150) NOT NULL,
  PRIMARY KEY (`perusahaan_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `perusahaan_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `perusahaan` */

/*Table structure for table `peserta` */

DROP TABLE IF EXISTS `peserta`;

CREATE TABLE `peserta` (
  `peserta_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `nik` varchar(20) NOT NULL,
  `tgl_lahir` date NOT NULL,
  `pendidikan` varchar(5) NOT NULL,
  `jurusan` varchar(50) NOT NULL,
  `nilai` int(11) NOT NULL,
  `status` tinyint(4) NOT NULL COMMENT '0=free, 1=sedang pelatihan',
  PRIMARY KEY (`peserta_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `peserta_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `peserta` */

/*Table structure for table `syarat_lowongan` */

DROP TABLE IF EXISTS `syarat_lowongan`;

CREATE TABLE `syarat_lowongan` (
  `sl_id` int(11) NOT NULL AUTO_INCREMENT,
  `lowongan_id` int(11) NOT NULL,
  `deskripsi` varchar(150) NOT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`sl_id`),
  KEY `lowongan_id` (`lowongan_id`),
  CONSTRAINT `syarat_lowongan_ibfk_1` FOREIGN KEY (`lowongan_id`) REFERENCES `lowongan` (`lowongan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `syarat_lowongan` */

/*Table structure for table `syarat_pelatihan` */

DROP TABLE IF EXISTS `syarat_pelatihan`;

CREATE TABLE `syarat_pelatihan` (
  `sp_id` int(11) NOT NULL AUTO_INCREMENT,
  `pelatihan_id` int(11) NOT NULL,
  `deskripsi` varchar(150) NOT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`sp_id`),
  KEY `pelatihan_id` (`pelatihan_id`),
  CONSTRAINT `syarat_pelatihan_ibfk_1` FOREIGN KEY (`pelatihan_id`) REFERENCES `pelatihan` (`pelatihan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `syarat_pelatihan` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `nama` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `telp` varchar(12) NOT NULL,
  `role` tinyint(4) NOT NULL COMMENT '0=peserta, 1=perusahaan',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `user` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
