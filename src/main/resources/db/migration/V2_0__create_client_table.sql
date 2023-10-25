DROP TABLE IF EXISTS client;
CREATE TABLE IF NOT EXISTS client
(
  id BINARY(16) NOT NULL PRIMARY KEY,
  user_id BINARY(16) NOT NULL,
  status INT,
  tax_code VARCHAR(20),
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  email VARCHAR(60),
  address VARCHAR(80) NOT NULL,
  phone VARCHAR(20),
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  manager_id INT NOT NULL,
  FOREIGN KEY (manager_id) REFERENCES manager(id)
);