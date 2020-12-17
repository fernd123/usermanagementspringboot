CREATE TABLE schema_name_replace.`message` (
  `uuid` varchar(255) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `created_date` bigint NOT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` bigint DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `messageuniqk` (`name`),
  UNIQUE KEY `typeuniqk` (`type`)
);