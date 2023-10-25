INSERT INTO manager (user_id, first_name, last_name, status, created_at, updated_at)
VALUES
(UUID_TO_BIN('47413cd9-6c15-11ee-a809-9cb6d0ff6637'), 'John', 'Smith', 1, NOW(), NOW()),
(UUID_TO_BIN('47414332-6c15-11ee-a809-9cb6d0ff6637'), 'Valentin', 'Popov', 1, NOW(), NOW());