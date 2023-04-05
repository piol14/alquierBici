-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `alquiler_bici` DEFAULT CHARACTER SET utf8 ;
USE `alquiler_bici` ;

-- -----------------------------------------------------
-- Table `mydb`.`bici`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `alquiler_bici`.`bici` (
  `idbici` INT NOT NULL AUTO_INCREMENT,
  `estado` TINYINT NOT NULL,
  PRIMARY KEY (`idbici`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `alquiler_bici`.`usuario` (
  `idusuario` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `estado` TINYINT NOT NULL,
  `bici_idbici` INT(4) ,
  PRIMARY KEY (`idusuario`),
  INDEX `fk_usuario_bici_idx` (`bici_idbici` ASC) VISIBLE,
  CONSTRAINT `fk_usuario_bici`
    FOREIGN KEY (`bici_idbici`)
    REFERENCES `alquiler_bici`.`bici` (`idbici`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
INSERT INTO `alquiler_bici`.`bici` (`idbici`, `estado`) VALUES ('2', '0');
INSERT INTO `alquiler_bici`.`usuario` (`idusuario`, `nombre`, `estado`) VALUES ('2', 'Carmen', '0');