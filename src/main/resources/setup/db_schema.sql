-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.15 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             10.1.0.5464
-- --------------------------------------------------------
-- create database if does not exist
create database if not exists trade_management;
use trade_management;

-- Dumping structure for table tat_automation.doccano_sampling_master
drop table IF EXISTS `trade_master`;
create TABLE IF NOT EXISTS `trade_master`(
  `id` bigint unsigned NOT NULL,
  `type` varchar(50) NOT NULL,
  `user` json DEFAULT NULL,
`symbol` varchar(100)CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL,
`shares` INT(10) NOT NULL,
`price` DOUBLE(15,5) NOT NULL,
`created_at` TIMESTAMP NOT NULL,
`updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
PRIMARY KEY(`id`, `created_at`)
)ENGINE = InnoDB DEFAULT CHARSET = utf8 COLLATE = utf8_unicode_ci
PARTITION BY RANGE ( UNIX_TIMESTAMP(created_at) ) (
PARTITION OCTOBER_2020 VALUES LESS THAN ( UNIX_TIMESTAMP('2020-11-01 00:00:00') ),
PARTITION NOVEMBER_2020 VALUES LESS THAN ( UNIX_TIMESTAMP('2020-12-01 00:00:00') ),
PARTITION DECEMBER_2020 VALUES LESS THAN ( UNIX_TIMESTAMP('2021-01-01 00:00:00') ),
PARTITION JANUARY_2021 VALUES LESS THAN ( UNIX_TIMESTAMP('2021-02-01 00:00:00') ),
PARTITION FEBRUARY_2021 VALUES LESS THAN ( UNIX_TIMESTAMP('2021-03-01 00:00:00') ),
PARTITION MARCH_2021 VALUES LESS THAN ( UNIX_TIMESTAMP('2021-04-01 00:00:00') ),
PARTITION APRIL_2021 VALUES LESS THAN ( UNIX_TIMESTAMP('2021-05-01 00:00:00') ),
PARTITION MAY_2021 VALUES LESS THAN ( UNIX_TIMESTAMP('2021-06-01 00:00:00') ),
PARTITION JUNE_2021 VALUES LESS THAN ( UNIX_TIMESTAMP('2021-07-01 00:00:00') ),
PARTITION JULY_2021 VALUES LESS THAN ( UNIX_TIMESTAMP('2021-08-01 00:00:00') ),
PARTITION AUGUST_2021 VALUES LESS THAN ( UNIX_TIMESTAMP('2021-09-01 00:00:00') ),
PARTITION SEPTEMBER_2021 VALUES LESS THAN ( UNIX_TIMESTAMP('2021-10-01 00:00:00') ),
PARTITION OCTOBER_2021 VALUES LESS THAN ( UNIX_TIMESTAMP('2021-11-01 00:00:00') ),
PARTITION NOVEMBER_2021 VALUES LESS THAN ( UNIX_TIMESTAMP('2021-12-01 00:00:00') ),
PARTITION DECEMBER_2021 VALUES LESS THAN ( UNIX_TIMESTAMP('2022-01-01 00:00:00') ),
PARTITION JANUARY_2022 VALUES LESS THAN ( UNIX_TIMESTAMP('2022-02-01 00:00:00') ),
PARTITION FEBRUARY_2022 VALUES LESS THAN ( UNIX_TIMESTAMP('2022-03-01 00:00:00') ),
PARTITION MARCH_2022 VALUES LESS THAN ( UNIX_TIMESTAMP('2022-04-01 00:00:00') ),
PARTITION APRIL_2022 VALUES LESS THAN ( UNIX_TIMESTAMP('2022-05-01 00:00:00') ),
PARTITION MAY_2022 VALUES LESS THAN ( UNIX_TIMESTAMP('2022-06-01 00:00:00') ),
PARTITION JUNE_2022 VALUES LESS THAN ( UNIX_TIMESTAMP('2022-07-01 00:00:00') ),
PARTITION JULY_2022 VALUES LESS THAN ( UNIX_TIMESTAMP('2022-08-01 00:00:00') ),
PARTITION AUGUST_2022 VALUES LESS THAN ( UNIX_TIMESTAMP('2022-09-01 00:00:00') ),
PARTITION SEPTEMBER_2022 VALUES LESS THAN ( UNIX_TIMESTAMP('2022-10-01 00:00:00') ),
PARTITION OCTOBER_2022 VALUES LESS THAN ( UNIX_TIMESTAMP('2022-11-01 00:00:00') ),
PARTITION NOVEMBER_2022 VALUES LESS THAN ( UNIX_TIMESTAMP('2022-12-01 00:00:00') ),
PARTITION DECEMBER_2022 VALUES LESS THAN ( UNIX_TIMESTAMP('2023-01-01 00:00:00') ),
PARTITION JANUARY_2023_AND_UP VALUES LESS THAN MAXVALUE
);
