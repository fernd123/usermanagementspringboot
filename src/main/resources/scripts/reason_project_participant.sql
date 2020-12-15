CREATE TABLE schema_name_replace.`reason_project_participant` (
  `uuid` varchar(255) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `created_date` bigint NOT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` bigint DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `reason_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  CONSTRAINT `rppreasonforeingkey` FOREIGN KEY (`reason_id`) REFERENCES schema_name_replace.`reason` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;