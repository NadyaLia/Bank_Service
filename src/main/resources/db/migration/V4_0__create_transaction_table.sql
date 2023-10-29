DROP TABLE IF EXISTS transaction;
CREATE TABLE IF NOT EXISTS transaction
(
  id BINARY(16) NOT NULL PRIMARY KEY,
  type INT,
  amount DECIMAL(12,4),
  description VARCHAR(255),
  created_at TIMESTAMP,
  debit_account_id binary(16) null,
  credit_account_id binary(16) null,
  FOREIGN KEY (debit_account_id) REFERENCES account(id),
  FOREIGN KEY (credit_account_id) REFERENCES account(id)
);