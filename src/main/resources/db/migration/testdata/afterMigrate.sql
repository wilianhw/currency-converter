SET session_replication_role = 'replica';

DELETE FROM "transaction";

SET session_replication_role = 'origin';

-- Transaction
INSERT INTO "transaction" (id, purchase_amount, transaction_date, description) VALUES(1, 12, '2023-11-29', 'Transaction Test 1');
INSERT INTO "transaction" (id, purchase_amount, transaction_date, description) VALUES(2, 23.04, '2022-10-12', 'Transaction Test 2');
INSERT INTO "transaction" (id, purchase_amount, transaction_date, description) VALUES(3, 50.99, '2024-03-01', 'Transaction Test 3');
INSERT INTO "transaction" (id, purchase_amount, transaction_date, description) VALUES(4, 13.7, '2021-01-01', 'Transaction Test 4');
SELECT setval('transaction_id_seq', 4);
