create schema if not exists samurai;
use samurai;
CREATE TABLE if not exists `super_category` (
  `super_category_id` bigint NOT NULL AUTO_INCREMENT,
  `creation_date` datetime(6) DEFAULT NULL,
  `super_category_enable` bit(1) DEFAULT 0,
  `super_category_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`super_category_id`)
);
CREATE TABLE if not exists `sub_category` (
  `sub_category_id` bigint NOT NULL AUTO_INCREMENT,
  `creation_date` datetime(6) DEFAULT NULL,
  `sub_category_enable` bit(1) DEFAULT 0,
  `sub_category_name` varchar(100) DEFAULT NULL,
  `super_category_id` bigint DEFAULT NULL,
  PRIMARY KEY (`sub_category_id`),
  KEY `FKhjpo5kw7gbmm8xhyok8799d45` (`super_category_id`),
  CONSTRAINT `FKhjpo5kw7gbmm8xhyok8799d45` FOREIGN KEY (`super_category_id`) REFERENCES `super_category` (`super_category_id`)
);
CREATE TABLE IF NOT EXISTS `service_library` (
  `service_id` bigint NOT NULL AUTO_INCREMENT,
  `creation_date` datetime(6) DEFAULT NULL,
  `logo_image` longblob,
  `service_decommisioned` bit(1) DEFAULT NULL,
  `service_description` varchar(200) DEFAULT NULL,
  `service_name` varchar(100) DEFAULT NULL,
  `type_of_service` varchar(50) DEFAULT NULL,
  `service_instance_id` bigint DEFAULT NULL,
  `sub_category_id` bigint DEFAULT NULL,
  `super_category_id` bigint DEFAULT NULL,
  PRIMARY KEY (`service_id`),
  KEY `FKt9vjabjlrf9xib9fvvkwvxge3` (`service_instance_id`),
  KEY `FKbgly3u1kwjlcuicyo18d1xvlb` (`sub_category_id`),
  KEY `FK7sf2foa8jud339f477himo4gf` (`super_category_id`),
  CONSTRAINT `FK7sf2foa8jud339f477himo4gf` FOREIGN KEY (`super_category_id`) REFERENCES `super_category` (`super_category_id`),
  CONSTRAINT `FKbgly3u1kwjlcuicyo18d1xvlb` FOREIGN KEY (`sub_category_id`) REFERENCES `sub_category` (`sub_category_id`),
  CONSTRAINT `FKt9vjabjlrf9xib9fvvkwvxge3` FOREIGN KEY (`service_instance_id`) REFERENCES `service_instance` (`service_instance_id`)
);

CREATE TABLE IF NOT EXISTS `service_instance` (
  `service_instance_id` bigint NOT NULL AUTO_INCREMENT,
  `is_decomissioned` bit(1) DEFAULT b'0',
  `is_inactive` bit(1) DEFAULT b'0',
  `service_instance_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`service_instance_id`)
);

DROP PROCEDURE IF EXISTS fk_check_service_library;
DELIMITER $
CREATE PROCEDURE fk_check_service_library()
BEGIN

IF NOT EXISTS (
    SELECT NULL 
    FROM information_schema.TABLE_CONSTRAINTS
    WHERE
        CONSTRAINT_SCHEMA = DATABASE() AND
        CONSTRAINT_NAME   = 'FKt9vjabjlrf9xib9fvvkwvxge3' AND
        CONSTRAINT_TYPE   = 'FOREIGN KEY'
)
THEN
    ALTER TABLE `service_library`
    ADD CONSTRAINT `FKt9vjabjlrf9xib9fvvkwvxge3`
    FOREIGN KEY (`service_instance_id`)
    REFERENCES `service_instance` (`service_instance_id`);
END IF;
END$
DELIMITER ;
call fk_check_service_library;

