SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

ALTER SCHEMA `MusicStore`  DEFAULT CHARACTER SET utf8  DEFAULT COLLATE utf8_general_ci ;

USE `MusicStore`;

ALTER TABLE `MusicStore`.`Article` COLLATE = utf8_general_ci ;

ALTER TABLE `MusicStore`.`Invoice` COLLATE = utf8_general_ci , ADD COLUMN `Submitted` BIT(1) NOT NULL DEFAULT False  AFTER `CustomerID` 
, ADD INDEX `ix_Submitted` (`Submitted` ASC) ;

ALTER TABLE `MusicStore`.`Category` COLLATE = utf8_general_ci ;

ALTER TABLE `MusicStore`.`Customer` COLLATE = utf8_general_ci ;

ALTER TABLE `MusicStore`.`SiteRole` COLLATE = utf8_general_ci ;

ALTER TABLE `MusicStore`.`SiteUser` COLLATE = utf8_general_ci ;

ALTER TABLE `MusicStore`.`SiteUserRole` COLLATE = utf8_general_ci ;

ALTER TABLE `MusicStore`.`InvoiceArticle` COLLATE = utf8_general_ci ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
