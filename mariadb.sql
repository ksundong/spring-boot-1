-- rest_board DB 생성
DROP DATABASE IF EXISTS `rest_board`;
CREATE DATABASE IF NOT EXISTS `rest_board`;
USE `rest_board`;

-- user 테이블 생성
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
	`idx` INT(11) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(20) NOT NULL COLLATE 'utf8mb4_unicode_520_ci',
	`password` VARCHAR(160) NOT NULL COLLATE 'utf8mb4_unicode_520_ci',
	`email` VARCHAR(254) NOT NULL COLLATE 'utf8mb4_unicode_520_ci',
	`principal` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_unicode_520_ci',
	`social_type` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_unicode_520_ci',
	`created_date` DATETIME NOT NULL,
	`updated_date` DATETIME NOT NULL,
	PRIMARY KEY (`idx`)
)
COLLATE='utf8mb4_unicode_520_ci'
ENGINE=InnoDB;

-- board 테이블 생성
DROP TABLE IF EXISTS `board`;
CREATE TABLE `board` (
	`idx` INT(11) NOT NULL AUTO_INCREMENT,
	`title` VARCHAR(100) NOT NULL COLLATE 'utf8mb4_unicode_520_ci',
	`sub_title` VARCHAR(100) NULL DEFAULT NULL COLLATE 'utf8mb4_unicode_520_ci',
	`content` TEXT NOT NULL COLLATE 'utf8mb4_unicode_520_ci',
	`board_type` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_unicode_520_ci',
	`created_date` DATETIME NOT NULL,
	`updated_date` DATETIME NOT NULL,
	`user_idx` INT(11) NOT NULL,
	PRIMARY KEY (`idx`),
	INDEX `user_idx` (`user_idx`),
	CONSTRAINT `user_idx` FOREIGN KEY (`user_idx`) REFERENCES `user` (`idx`)
)
COLLATE='utf8mb4_unicode_520_ci'
ENGINE=InnoDB;

-- rest_board 전용 계정 생성
DROP USER 'restuser'@'localhost';
CREATE USER 'restuser'@'localhost' IDENTIFIED BY 'gL5D9Gepy9CyP-CP';
GRANT ALL PRIVILEGES ON rest_board.* TO 'restuser'@'localhost' WITH GRANT OPTION;
FLUSH PRIVILEGES;