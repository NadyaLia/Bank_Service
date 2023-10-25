DROP TABLE IF EXISTS product;
CREATE TABLE IF NOT EXISTS product
(
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(70) NOT NULL,
  status INT,
  currency_code INT,
  interest_rate DECIMAL(6,4),
  `limit` INT,
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  manager_id int not null,
  FOREIGN KEY (manager_id) REFERENCES manager(id)
);