SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `MusicStore` DEFAULT CHARACTER SET utf8 ;
USE `MusicStore` ;

-- -----------------------------------------------------
-- Table `MusicStore`.`Category`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `MusicStore`.`Category` (
  `ID` INT NOT NULL ,
  `Name` VARCHAR(100) NOT NULL ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MusicStore`.`Article`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `MusicStore`.`Article` (
  `ID` INT NOT NULL ,
  `Name` VARCHAR(255) NOT NULL ,
  `PhotoFileName` VARCHAR(255) NULL ,
  `Description` TEXT NOT NULL ,
  `CategoryID` INT NOT NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `fk_Article_Category1` (`CategoryID` ASC) ,
  CONSTRAINT `fk_Article_Category1`
    FOREIGN KEY (`CategoryID` )
    REFERENCES `MusicStore`.`Category` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MusicStore`.`SiteUser`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `MusicStore`.`SiteUser` (
  `Login` VARCHAR(255) NOT NULL ,
  `Passw` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`Login`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MusicStore`.`Customer`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `MusicStore`.`Customer` (
  `ID` INT NOT NULL ,
  `Name` VARCHAR(255) NOT NULL ,
  `Email` VARCHAR(255) NOT NULL ,
  `Phone` VARCHAR(255) NOT NULL ,
  `Login` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `fk_Customer_SiteUser1` (`Login` ASC) ,
  UNIQUE INDEX `User_Login_UNIQUE` (`Login` ASC) ,
  CONSTRAINT `fk_Customer_SiteUser1`
    FOREIGN KEY (`Login` )
    REFERENCES `MusicStore`.`SiteUser` (`Login` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MusicStore`.`Invoice`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `MusicStore`.`Invoice` (
  `ID` INT NOT NULL ,
  `Date` DATETIME NOT NULL ,
  `CustomerID` INT NOT NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `fk_Invoice_Customer1` (`CustomerID` ASC) ,
  CONSTRAINT `fk_Invoice_Customer1`
    FOREIGN KEY (`CustomerID` )
    REFERENCES `MusicStore`.`Customer` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MusicStore`.`SiteRole`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `MusicStore`.`SiteRole` (
  `Name` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`Name`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MusicStore`.`SiteUserRole`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `MusicStore`.`SiteUserRole` (
  `UserLogin` VARCHAR(255) NOT NULL ,
  `RoleName` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`UserLogin`, `RoleName`) ,
  INDEX `fk_SiteUser_has_SiteRole_SiteRole1` (`RoleName` ASC) ,
  INDEX `fk_SiteUser_has_SiteRole_SiteUser` (`UserLogin` ASC) ,
  CONSTRAINT `fk_SiteUser_has_SiteRole_SiteUser`
    FOREIGN KEY (`UserLogin` )
    REFERENCES `MusicStore`.`SiteUser` (`Login` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_SiteUser_has_SiteRole_SiteRole1`
    FOREIGN KEY (`RoleName` )
    REFERENCES `MusicStore`.`SiteRole` (`Name` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MusicStore`.`InvoiceArticle`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `MusicStore`.`InvoiceArticle` (
  `InvoiceID` INT NOT NULL ,
  `ArticleID` INT NOT NULL ,
  PRIMARY KEY (`InvoiceID`, `ArticleID`) ,
  INDEX `fk_Invoice_has_Article_Article1` (`ArticleID` ASC) ,
  INDEX `fk_Invoice_has_Article_Invoice1` (`InvoiceID` ASC) ,
  CONSTRAINT `fk_Invoice_has_Article_Invoice1`
    FOREIGN KEY (`InvoiceID` )
    REFERENCES `MusicStore`.`Invoice` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Invoice_has_Article_Article1`
    FOREIGN KEY (`ArticleID` )
    REFERENCES `MusicStore`.`Article` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
