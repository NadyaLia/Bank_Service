DROP TABLE IF EXISTS agreement;
CREATE TABLE IF NOT EXISTS agreement
(
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  interest_rate DECIMAL(6,4),
  status INT,
  sum DECIMAL(15,2),
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  account_id binary(16) not null,
  product_id int not null,
  FOREIGN KEY (account_id) REFERENCES account(id),
  FOREIGN KEY (product_id) REFERENCES product(id)
);