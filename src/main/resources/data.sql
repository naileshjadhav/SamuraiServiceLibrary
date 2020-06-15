create schema if not exists samurai;
use samurai;

INSERT ignore INTO super_category (super_category_id, creation_date, super_category_enable,super_category_name) values (1, '2020-05-27 14:34:21.778000', 1, 'Tools for AI-Led RUN');
INSERT ignore INTO super_category (super_category_id, creation_date, super_category_enable,super_category_name) values (2, '2020-05-27 14:34:21.778000', 1, 'Tools for AI-Led DEV');

INSERT ignore INTO `samurai`.`sub_category` (`sub_category_id`, `creation_date`, `sub_category_enable`, `sub_category_name`, `super_category_id`) VALUES (1, '2020-05-27 14:34:21.778000', 1, 'Industry-Led Cloud Service', 2);
INSERT ignore INTO `samurai`.`sub_category` (`sub_category_id`, `creation_date`, `sub_category_enable`, `sub_category_name`, `super_category_id`) VALUES (2, '2020-05-27 14:34:21.778000', 1, 'Zensar AI-Led Tools', 1);
INSERT ignore INTO `samurai`.`sub_category` (`sub_category_id`, `creation_date`, `sub_category_enable`, `sub_category_name`, `super_category_id`) VALUES (3, '2020-05-27 14:34:21.778000', 1, 'Industry-Led ITSM Integration Tools', 1);
INSERT ignore INTO `samurai`.`sub_category` (`sub_category_id`, `creation_date`, `sub_category_enable`, `sub_category_name`, `super_category_id`) VALUES (4, '2020-05-27 14:34:21.778000', 1, 'Industry-Led ITSM Monitorning Tools', 1);
INSERT ignore INTO `samurai`.`sub_category` (`sub_category_id`, `creation_date`, `sub_category_enable`, `sub_category_name`, `super_category_id`) VALUES (5, '2020-05-27 14:34:21.778000', 1, 'Industry-Led Dev Tools', 2);
