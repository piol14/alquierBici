-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema alquiler_bici
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema alquiler_bici
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `alquiler_bici` DEFAULT CHARACTER SET utf8mb3 ;
USE `alquiler_bici` ;

-- -----------------------------------------------------
-- Table `alquiler_bici`.`bici`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `alquiler_bici`.`bici` (
  `idbici` INT NOT NULL AUTO_INCREMENT,
  `estado` TINYINT NOT NULL DEFAULT '0',
  PRIMARY KEY (`idbici`))
ENGINE = InnoDB
AUTO_INCREMENT = 23435
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `alquiler_bici`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `alquiler_bici`.`usuario` (
  `idusuario` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `bici_idbici` INT NULL DEFAULT NULL,
  PRIMARY KEY (`idusuario`),
  INDEX `fk_usuario_bici_idx` (`bici_idbici` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 4656458
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
