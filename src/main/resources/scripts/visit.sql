CREATE TABLE schema_name_replace.`visit` (
  `uuid` varchar(255) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `created_date` bigint NOT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` bigint DEFAULT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  `reason_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FKkroinwblyucd8qeyfni4hxujx` (`reason_id`),
  KEY `FKtly3l2y9j92oskaoh7bmx0drt` (`user_id`),
  CONSTRAINT `FKkroinwblyucd8qeyfni4hxujx` FOREIGN KEY (`reason_id`) REFERENCES schema_name_replace.`reason` (`uuid`),
  CONSTRAINT `FKtly3l2y9j92oskaoh7bmx0drt` FOREIGN KEY (`user_id`) REFERENCES schema_name_replace.`user` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;