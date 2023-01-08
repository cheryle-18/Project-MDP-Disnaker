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
-- CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_disnaker` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

-- USE `db_disnaker`;

/*Table structure for table `failed_jobs` */

DROP TABLE IF EXISTS `failed_jobs`;

CREATE TABLE `failed_jobs` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `uuid` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `connection` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `queue` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `payload` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `exception` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `failed_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `failed_jobs_uuid_unique` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `failed_jobs` */

/*Table structure for table `kategori` */

DROP TABLE IF EXISTS `kategori`;

CREATE TABLE `kategori` (
  `kategori_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `nama` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`kategori_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `kategori` */

insert  into `kategori`(`kategori_id`,`nama`,`created_at`,`updated_at`,`deleted_at`) values 
(1,'Industri','2023-01-07 21:20:58','2023-01-07 21:20:58',NULL),
(2,'Jasa','2023-01-07 21:20:58','2023-01-07 21:20:58',NULL),
(3,'Otomotif','2023-01-07 21:20:58','2023-01-07 21:20:58',NULL),
(4,'Pariwisata','2023-01-07 21:20:58','2023-01-07 21:20:58',NULL),
(5,'Perkantoran','2023-01-07 21:20:58','2023-01-07 21:20:58',NULL),
(6,'Teknologi Informasi','2023-01-07 21:20:58','2023-01-07 21:20:58',NULL),
(7,'Kesenian','2023-01-07 21:20:58','2023-01-07 21:20:58',NULL);

/*Table structure for table `lowongan` */

DROP TABLE IF EXISTS `lowongan`;

CREATE TABLE `lowongan` (
  `lowongan_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `nama` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `kategori_id` bigint(20) unsigned NOT NULL,
  `perusahaan_id` bigint(20) unsigned NOT NULL,
  `kuota` int(11) NOT NULL DEFAULT 0,
  `keterangan` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` tinyint(4) NOT NULL COMMENT '0 = tidak aktif, 1 = aktif',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`lowongan_id`),
  KEY `lowongan_kategori_id_foreign` (`kategori_id`),
  KEY `lowongan_perusahaan_id_foreign` (`perusahaan_id`),
  CONSTRAINT `lowongan_kategori_id_foreign` FOREIGN KEY (`kategori_id`) REFERENCES `kategori` (`kategori_id`) ON DELETE CASCADE,
  CONSTRAINT `lowongan_perusahaan_id_foreign` FOREIGN KEY (`perusahaan_id`) REFERENCES `perusahaan` (`perusahaan_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `lowongan` */

insert  into `lowongan`(`lowongan_id`,`nama`,`kategori_id`,`perusahaan_id`,`kuota`,`keterangan`,`status`,`created_at`,`updated_at`,`deleted_at`) values 
(1,'Pegawai Administrasi',5,1,10,'Lorem ipsum dolor sit, amet consectetur adipisicing elit. At, nesciunt.',1,NULL,NULL,NULL),
(2,'Staff IT',6,1,15,'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quidem, quo?',0,NULL,NULL,NULL),
(3,'Lowongan 1',7,12,12,'Voluptatum sapiente ipsum tempora veritatis qui quia ullam nihil fuga nam.',1,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(4,'Lowongan 2',3,8,12,'Omnis laborum ut et laboriosam vel ut aut et autem.',1,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(5,'Lowongan 3',6,2,12,'Et in dolor molestiae voluptas molestiae esse ratione est.',1,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(6,'Lowongan 4',7,11,10,'Omnis saepe vero facilis recusandae aut et id rem minima repellendus laboriosam assumenda.',1,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(7,'Lowongan 5',4,3,12,'Maxime rerum est hic voluptas porro rerum tenetur enim adipisci.',1,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(8,'Lowongan 6',7,4,13,'Porro commodi minus vero enim tempore tenetur.',0,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(9,'Lowongan 7',3,7,14,'Numquam non voluptas dolores fugit et eaque qui sed et in voluptas quae.',0,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(10,'Lowongan 8',4,1,15,'Aperiam praesentium mollitia qui sint nam sapiente eum.',1,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(11,'Lowongan 9',2,9,20,'Maiores quod ipsum in cupiditate error in voluptas.',1,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(12,'Lowongan 10',5,10,16,'Quam dignissimos blanditiis qui et rem magni quo asperiores fugiat voluptatem debitis.',1,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(13,'Lowongan 11',4,3,20,'Rerum dignissimos quibusdam vel in tempora alias id placeat.',0,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(14,'Lowongan 12',1,7,10,'Beatae molestiae et quia hic sit voluptas quidem delectus aut saepe vitae.',1,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(15,'Lowongan 13',4,5,17,'Quia atque rem explicabo dicta et minus animi recusandae commodi dicta.',0,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(16,'Lowongan 14',7,9,11,'Incidunt vitae architecto architecto quod consequatur consequuntur qui illo nisi.',0,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(17,'Lowongan 15',4,12,16,'Quod commodi fugiat voluptatem nostrum in cupiditate et odio qui fugit non.',0,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL);

/*Table structure for table `migrations` */

DROP TABLE IF EXISTS `migrations`;

CREATE TABLE `migrations` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `migration` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `batch` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `migrations` */

insert  into `migrations`(`id`,`migration`,`batch`) values 
(1,'2014_10_12_100000_create_password_resets_table',1),
(2,'2019_08_19_000000_create_failed_jobs_table',1),
(3,'2019_12_14_000001_create_personal_access_tokens_table',1),
(4,'2022_12_27_070425_create_user_table',1),
(5,'2022_12_27_070520_create_kategori_table',1),
(6,'2022_12_27_070539_create_lowongan_table',1),
(7,'2022_12_27_070554_create_pelatihan_table',1),
(8,'2022_12_27_070625_create_pendaftaran_lowongan_table',1),
(9,'2022_12_27_070637_create_pendaftaran_pelatihan_table',1),
(10,'2022_12_27_070644_create_perusahaan_table',1),
(11,'2022_12_27_070651_create_peserta_table',1),
(12,'2022_12_27_070702_create_syarat_lowongan_table',1),
(13,'2022_12_27_070711_create_syarat_pelatihan_table',1),
(14,'2023_01_02_043242_create_pendidikan_table',1);

/*Table structure for table `password_resets` */

DROP TABLE IF EXISTS `password_resets`;

CREATE TABLE `password_resets` (
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `token` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  KEY `password_resets_email_index` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `password_resets` */

/*Table structure for table `pelatihan` */

DROP TABLE IF EXISTS `pelatihan`;

CREATE TABLE `pelatihan` (
  `pelatihan_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `nama` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `kategori_id` bigint(20) unsigned NOT NULL,
  `kuota` int(11) NOT NULL DEFAULT 0,
  `durasi` int(11) NOT NULL DEFAULT 0,
  `pendidikan_id` int(11) NOT NULL,
  `keterangan` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` tinyint(4) NOT NULL COMMENT '0 = tidak aktif, 1 = aktif',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`pelatihan_id`),
  KEY `pelatihan_kategori_id_foreign` (`kategori_id`),
  CONSTRAINT `pelatihan_kategori_id_foreign` FOREIGN KEY (`kategori_id`) REFERENCES `kategori` (`kategori_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `pelatihan` */

insert  into `pelatihan`(`pelatihan_id`,`nama`,`kategori_id`,`kuota`,`durasi`,`pendidikan_id`,`keterangan`,`status`,`created_at`,`updated_at`,`deleted_at`) values 
(1,'Administrasi Perkantoran',5,10,15,4,'Lorem ipsum dolor sit, amet consectetur adipisicing elit. At, nesciunt.',1,NULL,NULL,NULL),
(2,'Pelatihan 1',1,11,48,2,'Sint odit et sunt laudantium magni dolor vel non rerum et ut porro.',1,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(3,'Pelatihan 2',5,11,37,5,'Quae laboriosam in sed non rerum sed cupiditate adipisci rerum quia numquam repellat recusandae.',0,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(4,'Pelatihan 3',6,15,14,9,'Veritatis incidunt recusandae quod et corrupti ut voluptas praesentium optio voluptas quasi nisi id.',0,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(5,'Pelatihan 4',4,11,17,11,'Reiciendis placeat ipsam aut et cumque voluptatibus repudiandae quia nihil dignissimos exercitationem sit iure.',0,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(6,'Pelatihan 5',7,18,19,5,'Omnis iure nemo perspiciatis enim a autem est fuga tempore.',0,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(7,'Pelatihan 6',5,17,28,11,'Consequatur accusantium ad aperiam labore qui ut neque nobis neque error quaerat.',0,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(8,'Pelatihan 7',3,10,16,6,'Quo et porro placeat qui quo reprehenderit ipsam recusandae dicta possimus ut voluptatem dolorem.',0,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(9,'Pelatihan 8',4,13,36,3,'Quae commodi assumenda officiis eos fuga illum provident magnam assumenda autem.',1,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(10,'Pelatihan 9',6,15,41,7,'Ut quia quidem ut laudantium impedit voluptates earum iusto.',1,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(11,'Pelatihan 10',2,17,21,9,'Vero minima nihil consequuntur aspernatur sunt ut at ipsa et.',1,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(12,'Pelatihan 11',6,18,35,2,'Libero sunt error ratione sequi ipsam ut.',0,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(13,'Pelatihan 12',1,13,39,6,'Fuga pariatur nihil quidem et autem corrupti voluptate est rerum.',0,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(14,'Pelatihan 13',1,20,13,6,'Provident qui ea quo laborum assumenda incidunt atque quam.',1,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(15,'Pelatihan 14',2,18,22,8,'Ad alias dolorem consequatur et ut eos corrupti distinctio exercitationem voluptatum.',0,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(16,'Pelatihan 15',5,10,15,11,'Neque natus expedita eum modi voluptatum nihil expedita.',0,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL);

/*Table structure for table `pendaftaran_lowongan` */

DROP TABLE IF EXISTS `pendaftaran_lowongan`;

CREATE TABLE `pendaftaran_lowongan` (
  `pl_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `lowongan_id` bigint(20) unsigned NOT NULL,
  `peserta_id` bigint(20) unsigned NOT NULL,
  `tanggal` datetime NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`pl_id`),
  KEY `pendaftaran_lowongan_lowongan_id_foreign` (`lowongan_id`),
  KEY `pendaftaran_lowongan_peserta_id_foreign` (`peserta_id`),
  CONSTRAINT `pendaftaran_lowongan_lowongan_id_foreign` FOREIGN KEY (`lowongan_id`) REFERENCES `lowongan` (`lowongan_id`) ON DELETE CASCADE,
  CONSTRAINT `pendaftaran_lowongan_peserta_id_foreign` FOREIGN KEY (`peserta_id`) REFERENCES `peserta` (`peserta_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `pendaftaran_lowongan` */

insert  into `pendaftaran_lowongan`(`pl_id`,`lowongan_id`,`peserta_id`,`tanggal`,`created_at`,`updated_at`,`deleted_at`) values 
(1,1,1,'2022-12-31 15:37:17',NULL,NULL,NULL);

/*Table structure for table `pendaftaran_pelatihan` */

DROP TABLE IF EXISTS `pendaftaran_pelatihan`;

CREATE TABLE `pendaftaran_pelatihan` (
  `pp_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `pelatihan_id` bigint(20) unsigned NOT NULL,
  `peserta_id` bigint(20) unsigned NOT NULL,
  `tgl_pendaftaran` datetime NOT NULL,
  `tgl_wawancara` datetime DEFAULT NULL,
  `status_pendaftaran` tinyint(4) NOT NULL COMMENT '0 = pending, 1 = wawancara, 2 = sedang pelatihan, 3 = selesai, 4 = ditolak',
  `status_kelulusan` tinyint(4) DEFAULT NULL COMMENT '0 = pending, 1 = diterima, 2 = ditolak',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`pp_id`),
  KEY `pendaftaran_pelatihan_pelatihan_id_foreign` (`pelatihan_id`),
  KEY `pendaftaran_pelatihan_peserta_id_foreign` (`peserta_id`),
  CONSTRAINT `pendaftaran_pelatihan_pelatihan_id_foreign` FOREIGN KEY (`pelatihan_id`) REFERENCES `pelatihan` (`pelatihan_id`) ON DELETE CASCADE,
  CONSTRAINT `pendaftaran_pelatihan_peserta_id_foreign` FOREIGN KEY (`peserta_id`) REFERENCES `peserta` (`peserta_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `pendaftaran_pelatihan` */

insert  into `pendaftaran_pelatihan`(`pp_id`,`pelatihan_id`,`peserta_id`,`tgl_pendaftaran`,`tgl_wawancara`,`status_pendaftaran`,`status_kelulusan`,`created_at`,`updated_at`,`deleted_at`) values 
(1,1,1,'2022-12-31 15:37:17','2023-01-15 00:00:00',3,1,NULL,NULL,NULL),
(2,1,2,'2022-12-31 15:37:17','2023-01-15 00:00:00',0,0,NULL,NULL,NULL),
(3,1,5,'2022-12-31 15:37:17','2023-01-15 00:00:00',1,0,NULL,NULL,NULL),
(4,1,3,'2022-12-31 15:37:17','2023-01-15 00:00:00',2,0,NULL,NULL,NULL),
(5,1,4,'2022-12-31 15:37:17','2023-01-15 00:00:00',4,1,NULL,NULL,NULL);

/*Table structure for table `pendidikan` */

DROP TABLE IF EXISTS `pendidikan`;

CREATE TABLE `pendidikan` (
  `pendidikan_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `nama` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`pendidikan_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `pendidikan` */

insert  into `pendidikan`(`pendidikan_id`,`nama`,`created_at`,`updated_at`,`deleted_at`) values 
(1,'Tidak Memiliki Ijazah','2023-01-07 21:20:58','2023-01-07 21:20:58',NULL),
(2,'SD','2023-01-07 21:20:58','2023-01-07 21:20:58',NULL),
(3,'SMP','2023-01-07 21:20:58','2023-01-07 21:20:58',NULL),
(4,'SMA','2023-01-07 21:20:58','2023-01-07 21:20:58',NULL),
(5,'SMK','2023-01-07 21:20:58','2023-01-07 21:20:58',NULL),
(6,'Diploma I','2023-01-07 21:20:58','2023-01-07 21:20:58',NULL),
(7,'Diploma II','2023-01-07 21:20:58','2023-01-07 21:20:58',NULL),
(8,'Diploma III','2023-01-07 21:20:58','2023-01-07 21:20:58',NULL),
(9,'Diploma IV','2023-01-07 21:20:58','2023-01-07 21:20:58',NULL),
(10,'Strata I','2023-01-07 21:20:58','2023-01-07 21:20:58',NULL),
(11,'Strata II','2023-01-07 21:20:58','2023-01-07 21:20:58',NULL),
(12,'Strata III','2023-01-07 21:20:58','2023-01-07 21:20:58',NULL);

/*Table structure for table `personal_access_tokens` */

DROP TABLE IF EXISTS `personal_access_tokens`;

CREATE TABLE `personal_access_tokens` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tokenable_type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `tokenable_id` bigint(20) unsigned NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `token` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `abilities` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_used_at` timestamp NULL DEFAULT NULL,
  `expires_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `personal_access_tokens_token_unique` (`token`),
  KEY `personal_access_tokens_tokenable_type_tokenable_id_index` (`tokenable_type`,`tokenable_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `personal_access_tokens` */

/*Table structure for table `perusahaan` */

DROP TABLE IF EXISTS `perusahaan`;

CREATE TABLE `perusahaan` (
  `perusahaan_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL,
  `alamat` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`perusahaan_id`),
  KEY `perusahaan_user_id_foreign` (`user_id`),
  CONSTRAINT `perusahaan_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `perusahaan` */

insert  into `perusahaan`(`perusahaan_id`,`user_id`,`alamat`,`created_at`,`updated_at`,`deleted_at`) values 
(1,2,'Jalan Kertajaya VII/07-10','2023-01-07 21:21:01','2023-01-07 21:21:01',NULL),
(2,12,'3542 Litzy Fort Apt. 614\nLake Nathanielton, MO 63141','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(3,4,'86064 Trey Squares Suite 269\nCormierchester, FL 68603-8904','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(4,2,'515 Conroy Drives Suite 612\nNorth Malloryshire, NY 85362-0430','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(5,7,'688 Bashirian Well\nWest Simone, CO 08126','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(6,22,'19048 Kaycee Ports Suite 102\nVirgiltown, AR 27397','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(7,18,'2099 Cole Rue Apt. 427\nPort Rylan, IN 62108-6610','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(8,10,'7618 Hyatt Shores\nLefflertown, VA 55654-4391','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(9,17,'75143 Gutmann Pass\nAudreanneburgh, MO 71106-7895','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(10,23,'6727 Purdy Motorway\nStarkberg, NH 43132-3474','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(11,9,'867 Renner Park Suite 744\nHermistonborough, WI 72119-5983','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(12,15,'78781 Gerhard Greens\nNew Edside, KS 96377-6407','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL);

/*Table structure for table `peserta` */

DROP TABLE IF EXISTS `peserta`;

CREATE TABLE `peserta` (
  `peserta_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL,
  `nik` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tgl_lahir` date DEFAULT NULL,
  `pendidikan_id` int(11) DEFAULT NULL,
  `jurusan` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nilai` int(11) DEFAULT NULL,
  `ijazah` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` tinyint(4) NOT NULL COMMENT '0 = free, 1 = sedang pelatihan, 2 = kerja',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`peserta_id`),
  KEY `peserta_user_id_foreign` (`user_id`),
  CONSTRAINT `peserta_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `peserta` */

insert  into `peserta`(`peserta_id`,`user_id`,`nik`,`tgl_lahir`,`pendidikan_id`,`jurusan`,`nilai`,`ijazah`,`status`,`created_at`,`updated_at`,`deleted_at`) values 
(1,1,'1234567890','2000-01-01',4,'IPA',75,'1.jpg',2,'2023-01-07 21:21:01','2023-01-07 21:21:01',NULL),
(2,3,'1234567890','2001-01-01',4,'IPS',80,NULL,0,'2023-01-07 21:21:01','2023-01-07 21:21:01',NULL),
(3,21,'1234567890','2001-11-29',12,'Teknik Informatika',68,NULL,2,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(4,1,'1234567890','1996-08-13',11,'Teknik Sipil',NULL,NULL,1,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(5,5,'1234567890','1999-05-04',8,'Manajemen Pajak',45,NULL,2,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(6,20,'1234567890','1998-04-27',4,'Bahasa',NULL,NULL,0,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(7,3,'1234567890','1995-12-01',6,'Desain Produk',NULL,NULL,0,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(8,11,'1234567890','1999-01-24',12,'Teknik Sipil',NULL,NULL,1,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(9,13,'1234567890','1997-04-01',5,'Teknik Informatika',NULL,NULL,0,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(10,8,'1234567890','2002-08-18',2,'-',93,NULL,2,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(11,19,'1234567890','1997-12-04',10,'Sastra Inggris',NULL,NULL,1,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(12,14,'1234567890','2002-12-10',4,'Bahasa',NULL,NULL,0,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(13,6,'1234567890','2000-08-11',11,'Teknik Elektro',NULL,NULL,1,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(14,16,'1234567890','2002-08-21',6,'Teknik Elektro',NULL,NULL,1,'2023-01-07 14:21:01','2023-01-07 14:21:01',NULL);

/*Table structure for table `syarat_lowongan` */

DROP TABLE IF EXISTS `syarat_lowongan`;

CREATE TABLE `syarat_lowongan` (
  `sl_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `lowongan_id` bigint(20) unsigned NOT NULL,
  `deskripsi` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`sl_id`),
  KEY `syarat_lowongan_lowongan_id_foreign` (`lowongan_id`),
  CONSTRAINT `syarat_lowongan_lowongan_id_foreign` FOREIGN KEY (`lowongan_id`) REFERENCES `lowongan` (`lowongan_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `syarat_lowongan` */

insert  into `syarat_lowongan`(`sl_id`,`lowongan_id`,`deskripsi`,`created_at`,`updated_at`,`deleted_at`) values 
(1,1,'Lorem ipsum dolor sit amet.','2023-01-07 21:21:01','2023-01-07 21:21:01',NULL),
(2,1,'Lorem ipsum dolor sit amet consectetur.','2023-01-07 21:21:01','2023-01-07 21:21:01',NULL),
(3,6,'Debitis ducimus velit velit ut corrupti.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(4,5,'Quisquam aliquam dolores eius.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(5,6,'Labore aut quis minima.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(6,10,'Corporis optio corporis et facere.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(7,15,'Est veritatis non.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(8,4,'Laborum et voluptatem saepe.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(9,7,'Molestiae deleniti id odit temporibus deserunt.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(10,9,'Omnis quaerat ratione est quod fugit.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(11,6,'Vel illo eaque earum.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(12,4,'Sapiente consequuntur dolor in amet.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(13,14,'Non rerum ipsa non repellat voluptatem.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(14,13,'Porro non ut.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(15,10,'Qui voluptatem qui est quaerat voluptate.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(16,11,'Dolorem ducimus ullam alias natus.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(17,13,'Enim nihil voluptatem dolores quia est.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(18,1,'Omnis natus tempore magni et.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(19,6,'Aut ratione rerum distinctio quis.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(20,11,'Modi qui velit dolorem.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(21,2,'Nobis similique molestiae accusamus.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(22,3,'Ut corporis porro illum est culpa.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(23,16,'Cum culpa consequatur explicabo maxime.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(24,5,'Neque error et.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(25,15,'Maiores sunt iure minima atque.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(26,10,'Eligendi aliquid sed modi libero sit.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(27,8,'Deserunt eligendi tempore iure.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(28,11,'Ipsam vitae officia vitae.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(29,8,'Odit sed dolor laborum doloremque.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(30,3,'Et ipsa in eaque inventore consequatur.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(31,12,'Tenetur ipsa saepe molestiae.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(32,3,'Non harum fuga laboriosam.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL);

/*Table structure for table `syarat_pelatihan` */

DROP TABLE IF EXISTS `syarat_pelatihan`;

CREATE TABLE `syarat_pelatihan` (
  `sp_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `pelatihan_id` bigint(20) unsigned NOT NULL,
  `deskripsi` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`sp_id`),
  KEY `syarat_pelatihan_pelatihan_id_foreign` (`pelatihan_id`),
  CONSTRAINT `syarat_pelatihan_pelatihan_id_foreign` FOREIGN KEY (`pelatihan_id`) REFERENCES `pelatihan` (`pelatihan_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `syarat_pelatihan` */

insert  into `syarat_pelatihan`(`sp_id`,`pelatihan_id`,`deskripsi`,`created_at`,`updated_at`,`deleted_at`) values 
(1,1,'Lorem ipsum dolor sit amet.','2023-01-07 21:21:01','2023-01-07 21:21:01',NULL),
(2,4,'Animi sunt commodi est.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(3,16,'Officia quo nihil soluta laboriosam nihil.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(4,9,'Odit quisquam nemo vero provident.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(5,12,'Quaerat ipsum iure nostrum.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(6,9,'Non dolorem officiis veritatis minus quis.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(7,1,'Voluptas eos distinctio perferendis.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(8,8,'Odit repudiandae nulla ea aut.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(9,14,'Minima vero cupiditate et nam dolores.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(10,7,'Neque eos aut voluptatibus nobis.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(11,2,'Libero beatae velit sint.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(12,15,'Non omnis eum magni.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(13,11,'Ut maxime consequatur.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(14,2,'Possimus qui modi corrupti.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(15,9,'Laudantium id laudantium molestiae qui.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(16,16,'Inventore voluptatem totam libero voluptates id.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(17,11,'Atque facilis vero.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(18,4,'Omnis et fugit.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(19,2,'Dignissimos placeat deleniti maxime saepe asperiores.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(20,4,'Officia inventore officia optio.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(21,10,'Et et suscipit natus voluptatibus.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(22,15,'Dolore consequatur ad rerum commodi qui.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(23,8,'Voluptatem non deleniti earum rem omnis.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(24,16,'Dolorem quos voluptatibus dicta.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(25,11,'Repellendus quia accusamus consequatur ea commodi.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(26,14,'Officiis esse explicabo tempora non.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(27,3,'Nulla rerum sit voluptas consequuntur.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(28,14,'Doloribus qui aut.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(29,12,'Beatae et iure.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(30,8,'Iure temporibus ullam.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(31,14,'Neque et quibusdam inventore cumque quia.','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `nama` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `username` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `telp` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role` tinyint(4) NOT NULL,
  `api_key` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_username_unique` (`username`),
  UNIQUE KEY `user_email_unique` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `user` */

insert  into `user`(`user_id`,`nama`,`email`,`username`,`password`,`telp`,`role`,`api_key`,`created_at`,`updated_at`,`deleted_at`) values 
(1,'John Doe','johndoe@gmail.com','johndoe','$2y$10$Uwz9NPmsLtsAyiRQWsAjj.MaGgTPgqn6r/s13PeVkQ2MefHf8m5le','0123456789',0,'3BOeRei9OAGw4uWq','2023-01-07 21:20:58','2023-01-07 21:20:58',NULL),
(2,'PT XYZ','xyz@gmail.com','xyz','$2y$10$.wLJXQkcHm0aetNmRMwPGeUoO3Sd0MPrCFu.GA2vzs26b2wEOFeZW','1234567890',1,'ailNNwuQ6Qp2kLrV','2023-01-07 21:20:58','2023-01-07 21:20:58',NULL),
(3,'Jane Doe','janedoe@gmail.com','janedoe','$2y$10$IqSQ.IKYgizMXnpfLHUJUetdqyoDGrU6pLtSua352SEkEmJGzPtKe','0123456789',0,'MCdpjDyPRZVZC8Zw','2023-01-07 21:20:58','2023-01-07 21:20:58',NULL),
(4,'Aryanna Schumm','Aryanna@example.com','cayla.kiehn','$2y$10$LaWerJj1tkeSMBLV46DRAe4/6LSUUYFQ8FpMP7to0bp.jycTZwR3K','989.478.9798',1,'oa4mCC4ie0I5f8BS','2023-01-07 14:21:00','2023-01-07 14:21:00',NULL),
(5,'Meta Waelchi','Meta@example.net','willie.mayert','$2y$10$OsLF0LmllO.Tu9U3jmDm4u4NPp7/I/9BU5c8JhjzqUd1cAWBmNE7m','(682) 831-6645',0,'-MEbDjQk_3mHgG4m','2023-01-07 14:21:00','2023-01-07 14:21:00',NULL),
(6,'Greg Stokes','Greg@example.net','batz.wilhelmine','$2y$10$eidSxGIxgatyAF1vWUxdJ.iK7hxeKwtBxZw5RPs9Ef1EYEaAjh/VS','+16576229038',0,'U6L_prhU6TtT3LGI','2023-01-07 14:21:00','2023-01-07 14:21:00',NULL),
(7,'Tia Lehner','Tia@example.net','vrohan','$2y$10$JvQ8Wzx1N3TZoQy02mJnAusDHzVMiK3CsZMhpfMvwZksmaFjSqYJe','+1-805-247-7796',1,'jHWaB9T-F6ZDT1Ov','2023-01-07 14:21:00','2023-01-07 14:21:00',NULL),
(8,'Delbert Halvorson','Delbert@example.org','keara.raynor','$2y$10$oEEQligutcZEqIqU23H5aO5GY4CLR2JO/PYu9wx3k.3rcRc1sUbvq','+1.909.849.4548',0,'qujRF5Tt5syjo7TL','2023-01-07 14:21:00','2023-01-07 14:21:00',NULL),
(9,'Neal Morissette','Neal@example.com','boyer.audrey','$2y$10$T6qGTftLUEeGdZXyUlFFqeGgoMz/afIaRo.pNEfUYz9PmJjKzeKS6','+17572469706',1,'1oaRm8vCpC5RVddk','2023-01-07 14:21:00','2023-01-07 14:21:00',NULL),
(10,'Winston Conn','Winston@example.org','cordie84','$2y$10$QVGO5dnK0NsDbJ4MxNITnumUgmmcJwtq0QTLwzVa0rbogDvvyEosy','+1.678.373.3159',1,'zNOTlEZvyROAehhD','2023-01-07 14:21:00','2023-01-07 14:21:00',NULL),
(11,'Assunta Prosacco','Assunta@example.net','qlowe','$2y$10$zg8kT.vRFp87Kj9UnLD6wuHKBVkDxnKM3kSR3Jie8QqWw43WyXN2.','(727) 386-4146',0,'rf3gAnbjWm6WLBwl','2023-01-07 14:21:00','2023-01-07 14:21:00',NULL),
(12,'Kendrick Ullrich','Kendrick@example.org','cordie.okuneva','$2y$10$u0xNxDrh8pHBXq9xKPggxu23Y13LTQn0D1aJ4m8GwdiVVPPEpwJcO','(224) 950-5098',1,'oRPA6ezVNH_XyCVH','2023-01-07 14:21:00','2023-01-07 14:21:00',NULL),
(13,'Rahsaan Turner','Rahsaan@example.org','estrella.braun','$2y$10$njDpwEOfyiPFnHsNrkVzZOfMlIG7HKJ0dOa66rlV/45KNux3Lg/EC','281.963.4247',0,'GntbBsNjEPk1H4Xn','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(14,'Una Zulauf','Una@example.net','gutkowski.francis','$2y$10$qqJeUyX/hnNdnbFUIe.WBu.6wUeJnDCiDus4yg1619f/qHdi0kucm','+1.847.227.6991',0,'u_Sdflg9b5N25KrJ','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(15,'Berry Ruecker','Berry@example.net','luisa84','$2y$10$.4iqZM2SdHc6dQM8.IuD0e2K1hcz0.qaUr6uAf20mHaCJoF8ezl6u','+1-978-815-5313',1,'X4u-LXTe5rWOH69f','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(16,'Emerald Gulgowski','Emerald@example.net','reina18','$2y$10$zNn5NTEnCghhRCCsnhIMZ.N3lopcv9miiPPFkw60GY79RXCWFYp7q','+1-781-780-6376',0,'_usy-Ak-G1sPoLmx','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(17,'Soledad Tromp','Soledad@example.net','shawn.torp','$2y$10$VazXBtS9BW94PolPFq3gZeNEpwJ1svYN2kLqwhoClCz3I1Q0yknAm','+13869415862',1,'KUfL3OPt0C-mzkIq','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(18,'Destinee Gottlieb','Destinee@example.com','marty54','$2y$10$Ru0nVFf1YMgIzyMGJ6Y3BeCVc8LHGbs6GI6tWxDTdgfG8CoN1cyX.','+1 (732) 816-8665',1,'Fv9U3HOLBmpO4fQM','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(19,'Vito Huel','Vito@example.com','tward','$2y$10$DwBn6FeVQEDlIYqeYEKA1uaaDrLJaGNRXWWzuAauHapUxlcRQI56K','+1-501-391-5693',0,'i32V5X9qojRBD_Fq','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(20,'Yazmin Treutel','Yazmin@example.net','moen.moses','$2y$10$twJi1gjnTENwSAppISqF2.qZowHQ/eyJD9sFQEqgsnDib0Emkv/IS','346-744-9406',0,'IBqD8MXUZ4feFtRL','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(21,'Sidney Erdman','Sidney@example.net','alisa.pagac','$2y$10$Ed2KcRQPr4JwqQk97U7j/.vitag7kZPeohnnVnrWWe1HRNuJByyOy','1-463-793-9340',0,'1PIS6rAZZESunmmQ','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(22,'Stan Gerhold','Stan@example.net','pebert','$2y$10$pWvtukumqV5G1kNKJPvlxuEKOATEH/tLeBvnnYkL1LsyeqAhzsdZe','+1-754-396-7394',1,'2b2MhoLovr9JviSZ','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL),
(23,'Maria Bode','Maria@example.net','vgusikowski','$2y$10$tl397YHOTMqNvBhjFUBzEOd6qI8B/d6fGAeeh9Ztr2CIE0jCU68vi','+1 (928) 446-2021',1,'oziysLIwo4qU4CGB','2023-01-07 14:21:01','2023-01-07 14:21:01',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
