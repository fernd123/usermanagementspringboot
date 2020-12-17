CREATE TABLE schema_name_replace.`user_signature` (
  `uuid` varchar(255) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `created_date` bigint NOT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` bigint DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FKhnedhai6q7xk4117vee6gjv3l` (`user_id`),
  CONSTRAINT `FKhnedhai6q7xk4117vee6gjv3l` FOREIGN KEY (`user_id`) REFERENCES schema_name_replace.`user` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;