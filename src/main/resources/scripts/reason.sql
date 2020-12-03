CREATE TABLE schema_name_replace.`reason` (
  `uuid` varchar(255) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `created_date` bigint NOT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` bigint DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `plant_coordinate_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `UK_40l39kkkcn6q747ubmvel7onh` (`name`),
  KEY `FK41f4aukjibcqsghl82vxka5nk` (`plant_coordinate_id`),
  CONSTRAINT `FK41f4aukjibcqsghl82vxka5nk` FOREIGN KEY (`plant_coordinate_id`) REFERENCES schema_name_replace.`plant_coordinates` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;