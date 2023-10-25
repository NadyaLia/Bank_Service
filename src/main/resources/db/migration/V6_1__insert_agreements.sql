INSERT INTO agreement (interest_rate, status, sum, created_at, updated_at, account_id, product_id)
VALUES (0.05, 1, 10000.00, NOW(), NOW(), UUID_TO_BIN('f47ac10b-58cc-4372-a567-0e02b2c3d479'), 1),
       (0.03, 1, 5000.00, NOW(), NOW(), UUID_TO_BIN('f47ac10b-58cc-4372-a567-0e02b2c3d479'), 2),
       (0.04, 2, 15000.00, NOW(), NOW(), UUID_TO_BIN('d47ac10b-58cc-4372-a567-0e02b2c3d481'), 1);
