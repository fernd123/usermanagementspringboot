CREATE TABLE schema_name_replace.`plant_plane` (
  `uuid` varchar(255) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `created_date` bigint NOT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` bigint DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `plant_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FK6frh2tx22stiw3qns2neukvm6` (`plant_id`),
  CONSTRAINT `FK6frh2tx22stiw3qns2neukvm6` FOREIGN KEY (`plant_id`) REFERENCES schema_name_replace.`plant` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;