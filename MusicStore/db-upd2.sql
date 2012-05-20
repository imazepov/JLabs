SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

ALTER SCHEMA `MusicStore`  DEFAULT CHARACTER SET utf8  DEFAULT COLLATE utf8_general_ci ;

USE `MusicStore`;

ALTER TABLE `MusicStore`.`Article` COLLATE = utf8_general_ci , CHANGE COLUMN `ID` `ID` INT(11) NOT NULL AUTO_INCREMENT  ;

ALTER TABLE `MusicStore`.`Invoice` COLLATE = utf8_general_ci , CHANGE COLUMN `ID` `ID` INT(11) NOT NULL AUTO_INCREMENT  , CHANGE COLUMN `Submitted` `Submitted` BIT(1) NOT NULL DEFAULT False  ;

ALTER TABLE `MusicStore`.`Category` COLLATE = utf8_general_ci , CHANGE COLUMN `ID` `ID` INT(11) NOT NULL AUTO_INCREMENT  ;

ALTER TABLE `MusicStore`.`Customer` COLLATE = utf8_general_ci , CHANGE COLUMN `ID` `ID` INT(11) NOT NULL AUTO_INCREMENT  ;

ALTER TABLE `MusicStore`.`SiteRole` COLLATE = utf8_general_ci ;

ALTER TABLE `MusicStore`.`SiteUser` COLLATE = utf8_general_ci ;

ALTER TABLE `MusicStore`.`SiteUserRole` COLLATE = utf8_general_ci , DROP FOREIGN KEY `fk_SiteUser_has_SiteRole_SiteUser` ;

ALTER TABLE `MusicStore`.`SiteUserRole` 
  ADD CONSTRAINT `fk_SiteUser_has_SiteRole_SiteUser`
  FOREIGN KEY (`UserLogin` )
  REFERENCES `MusicStore`.`SiteUser` (`Login` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `MusicStore`.`InvoiceArticle` COLLATE = utf8_general_ci , DROP FOREIGN KEY `fk_Invoice_has_Article_Invoice1` ;

ALTER TABLE `MusicStore`.`InvoiceArticle` 
  ADD CONSTRAINT `fk_Invoice_has_Article_Invoice1`
  FOREIGN KEY (`InvoiceID` )
  REFERENCES `MusicStore`.`Invoice` (`ID` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
