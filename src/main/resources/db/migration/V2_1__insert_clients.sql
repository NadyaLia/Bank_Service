INSERT INTO client (id, user_id, status, tax_code, first_name, last_name, email, address, phone,
created_at, updated_at, manager_id) VALUES
(UUID_TO_BIN('ecc1bb19-6c17-11ee-a809-9cb6d0ff6637'), UUID_TO_BIN('34092502-2772-4d71-b52e-f03b44bf6046'), 1, 'TX12345',
'Anna', 'Smith', 'anna@email.com', '321 Gold St', '123-456-7890', NOW(), NOW(), 1),
(UUID_TO_BIN('ecc1c05a-6c17-11ee-a809-9cb6d0ff6637'), UUID_TO_BIN('1bf109b9-7333-4d19-8ee8-da23abfd588d'), 1, 'TX67890',
'Bobby', 'Kit', 'bob@email.com', '456 Elm St', '987-654-3210', NOW(), NOW(), 2);