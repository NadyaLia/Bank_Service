DROP TABLE IF EXISTS account;
CREATE TABLE IF NOT EXISTS account
(
  id BINARY(16) NOT NULL PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  type INT,
  status INT,
  balance DECIMAL(15,2),
  currency_code INT,
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  client_id binary(16) NOT NULL,
  FOREIGN KEY (client_id) REFERENCES client(id)
);