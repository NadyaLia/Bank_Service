DROP TABLE IF EXISTS user_role;
CREATE TABLE `user_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` binary(16) NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_to_user_idx` (`user_id`),
  KEY `fk_to_role_idx` (`role_id`),
  CONSTRAINT `fkey_to_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fkey_to_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
)
