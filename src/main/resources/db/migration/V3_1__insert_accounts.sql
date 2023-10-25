INSERT INTO account (id, name, type, status, balance, currency_code, created_at, updated_at, client_id)
VALUES
(UUID_TO_BIN('f47ac10b-58cc-4372-a567-0e02b2c3d479'), 'Education Savings Account', 1, 1, 1000.50, 840, NOW(), NOW(),
UUID_TO_BIN('ecc1bb19-6c17-11ee-a809-9cb6d0ff6637')),


(UUID_TO_BIN('d47ac10b-58cc-4372-a567-0e02b2c3d481'), 'Premium Business Account', 2, 1, 5000.75, 840, NOW(), NOW(),
UUID_TO_BIN('ecc1c05a-6c17-11ee-a809-9cb6d0ff6637'));