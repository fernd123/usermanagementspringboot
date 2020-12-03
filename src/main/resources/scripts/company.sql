CREATE TABLE schema_name_replace.`company` (
  `uuid` varchar(255) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `created_date` bigint NOT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` bigint DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `alira` bit(1) DEFAULT NULL,
  `infinisense` bit(1) DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,

  PRIMARY KEY (`uuid`),
  CONSTRAINT `companyuserdefault` FOREIGN KEY (`user_id`) REFERENCES schema_name_replace.`user` (`uuid`),
  UNIQUE KEY `UK_52mr58enuprjs3ftes7p7h321` (`name`)
);
