INSERT INTO product (name, status, currency_code, interest_rate, `limit`, created_at, updated_at, manager_id)
VALUES
('Savings Account', 1, 840, 0.0250, NULL, NOW(), NOW(), 1),  -- 840 is the currency code for USD
('Credit Card', 1, 840, 0.1800, 5000, NOW(), NOW(), 2);      -- 5000 is the credit limit