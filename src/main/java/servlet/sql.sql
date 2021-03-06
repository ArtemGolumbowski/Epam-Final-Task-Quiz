-- MySQL Script generated by MySQL Workbench
-- Sun Oct  3 23:08:51 2021
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema quiz_schema
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema quiz_schema
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `quiz_schema` DEFAULT CHARACTER SET utf8 ;
USE `quiz_schema` ;

-- -----------------------------------------------------
-- Table `quiz_schema`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quiz_schema`.`role` ;

CREATE TABLE IF NOT EXISTS `quiz_schema`.`role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `value` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `quiz_schema`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quiz_schema`.`user` ;

CREATE TABLE IF NOT EXISTS `quiz_schema`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `role` INT NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `fk_user_role_idx` (`role_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `quiz_schema`.`role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `quiz_schema`.`theme`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quiz_schema`.`theme` ;

CREATE TABLE IF NOT EXISTS `quiz_schema`.`theme` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(255) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `quiz_schema`.`test`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quiz_schema`.`test` ;

CREATE TABLE IF NOT EXISTS `quiz_schema`.`test` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` TINYTEXT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `quiz_schema`.`questions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quiz_schema`.`questions` ;

CREATE TABLE IF NOT EXISTS `quiz_schema`.`questions` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `description` TINYTEXT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `quiz_schema`.`test_has_questions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quiz_schema`.`test_has_questions` ;

CREATE TABLE IF NOT EXISTS `quiz_schema`.`test_has_questions` (
  `test_id` INT NOT NULL,
  `questions_id` INT NOT NULL,
  PRIMARY KEY (`test_id`, `questions_id`),
  INDEX `fk_test_has_questions_questions1_idx` (`questions_id` ASC) VISIBLE,
  INDEX `fk_test_has_questions_test1_idx` (`test_id` ASC) VISIBLE,
  CONSTRAINT `fk_test_has_questions_test1`
    FOREIGN KEY (`test_id`)
    REFERENCES `quiz_schema`.`test` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_test_has_questions_questions1`
    FOREIGN KEY (`questions_id`)
    REFERENCES `quiz_schema`.`questions` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `quiz_schema`.`answers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quiz_schema`.`answers` ;

CREATE TABLE IF NOT EXISTS `quiz_schema`.`answers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(255) NULL,
  `value` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `quiz_schema`.`answers_has_questions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quiz_schema`.`answers_has_questions` ;

CREATE TABLE IF NOT EXISTS `quiz_schema`.`answers_has_questions` (
  `answers_id` INT NOT NULL,
  `questions_id` INT NOT NULL,
  PRIMARY KEY (`answers_id`, `questions_id`),
  INDEX `fk_answers_has_questions_questions1_idx` (`questions_id` ASC) VISIBLE,
  INDEX `fk_answers_has_questions_answers1_idx` (`answers_id` ASC) VISIBLE,
  CONSTRAINT `fk_answers_has_questions_answers1`
    FOREIGN KEY (`answers_id`)
    REFERENCES `quiz_schema`.`answers` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_answers_has_questions_questions1`
    FOREIGN KEY (`questions_id`)
    REFERENCES `quiz_schema`.`questions` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `quiz_schema`.`user_has_test`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quiz_schema`.`user_has_test` ;

CREATE TABLE IF NOT EXISTS `quiz_schema`.`user_has_test` (
  `user_id` INT NOT NULL,
  `test_id` INT NOT NULL,
  `result` DECIMAL(2) NULL,
  PRIMARY KEY (`user_id`, `test_id`),
  INDEX `fk_user_has_test_test1_idx` (`test_id` ASC) VISIBLE,
  INDEX `fk_user_has_test_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_test_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `quiz_schema`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_test_test1`
    FOREIGN KEY (`test_id`)
    REFERENCES `quiz_schema`.`test` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `quiz_schema`.`theme_has_test`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quiz_schema`.`theme_has_test` ;

CREATE TABLE IF NOT EXISTS `quiz_schema`.`theme_has_test` (
  `theme_id` INT NOT NULL,
  `test_id` INT NOT NULL,
  PRIMARY KEY (`theme_id`, `test_id`),
  INDEX `fk_theme_has_test_test1_idx` (`test_id` ASC) VISIBLE,
  INDEX `fk_theme_has_test_theme1_idx` (`theme_id` ASC) VISIBLE,
  CONSTRAINT `fk_theme_has_test_theme1`
    FOREIGN KEY (`theme_id`)
    REFERENCES `quiz_schema`.`theme` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_theme_has_test_test1`
    FOREIGN KEY (`test_id`)
    REFERENCES `quiz_schema`.`test` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;