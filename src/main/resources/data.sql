create schema if not exists samurai;
use samurai;
DROP PROCEDURE IF EXISTS superCategory_pl;
DELIMITER $
create PROCEDURE superCategory_pl()
BEGIN
    DECLARE superCategoryId1 INT DEFAULT 0;
    DECLARE superCategoryId2 INT DEFAULT 0;
	SELECT super_category_id INTO superCategoryId1 FROM super_category WHERE super_category_id = 1;
    SELECT super_category_id INTO superCategoryId2 FROM super_category WHERE super_category_id = 2;
	IF (superCategoryId1 = 0) then 
		INSERT INTO super_category (super_category_id, creation_date, super_category_enable,super_category_name)
		values (1, '2020-05-27 14:34:21.778000', 1, 'Tools for AI-Led RUN');
	END IF;
    IF (superCategoryId2 = 0) then 
		INSERT INTO super_category (super_category_id, creation_date, super_category_enable,super_category_name)
		values (2, '2020-05-27 14:34:21.778000', 1, 'Industry-Led Dev Tools');
	END IF;
END$
DELIMITER ;
call superCategory_pl;

drop procedure if exists subCategory_pl;
DELIMITER $
CREATE  PROCEDURE  subCategory_pl()
BEGIN
	DECLARE subCategoryId1 INT DEFAULT 0;
    DECLARE subCategoryId2 INT DEFAULT 0;
	DECLARE subCategoryId3 INT DEFAULT 0;
    DECLARE subCategoryId4 INT DEFAULT 0;
	DECLARE subCategoryId5 INT DEFAULT 0;
	
	SELECT sub_category_id INTO subCategoryId1 FROM sub_category WHERE sub_category_id = 1;
	IF (subCategoryId1 = 0) then 
		INSERT INTO `samurai`.`sub_category` (`sub_category_id`, `creation_date`, `sub_category_enable`, `sub_category_name`, `super_category_id`) VALUES (1, '2020-05-27 14:34:21.778000', 1, 'Industry-Led Cloud Tools', 2);
	END IF;
    SELECT sub_category_id INTO subCategoryId2 FROM sub_category WHERE sub_category_id = 2;
	IF (subCategoryId2 = 0) then 
		INSERT INTO `samurai`.`sub_category` (`sub_category_id`, `creation_date`, `sub_category_enable`, `sub_category_name`, `super_category_id`) VALUES (2, '2020-05-27 14:34:21.778000', 1, 'Zensar AI-Led Tools', 1);
	END IF;
	SELECT sub_category_id INTO subCategoryId3 FROM sub_category WHERE sub_category_id = 3;
	IF (subCategoryId3 = 0) then 
		INSERT INTO `samurai`.`sub_category` (`sub_category_id`, `creation_date`, `sub_category_enable`, `sub_category_name`, `super_category_id`) VALUES (3, '2020-05-27 14:34:21.778000', 1, 'Industry-Led ITSM Integration Tools', 1);
	END IF;
    SELECT sub_category_id INTO subCategoryId4 FROM sub_category WHERE sub_category_id = 4;
	IF (subCategoryId4 = 0) then 
		INSERT INTO `samurai`.`sub_category` (`sub_category_id`, `creation_date`, `sub_category_enable`, `sub_category_name`, `super_category_id`) VALUES (4, '2020-05-27 14:34:21.778000', 1, 'Industry-Led ITSM Monitorning Tools', 1);
	END IF;
	SELECT sub_category_id INTO subCategoryId5 FROM sub_category WHERE sub_category_id = 5;
	IF (subCategoryId5 = 0) then 
		INSERT INTO `samurai`.`sub_category` (`sub_category_id`, `creation_date`, `sub_category_enable`, `sub_category_name`, `super_category_id`) VALUES (5, '2020-05-27 14:34:21.778000', 1, 'Industry-Led Dev Tools', 2);
	END IF;
END$
DELIMITER ;
call subCategory_pl;