-- liquibase formatted sql

-- changeset Black77:1
create table if not exists rule_table
(
    id          uuid default RANDOM_UUID() not null primary key,
    query       text not null,
    arguments   text [] not null,
    negate      boolean not null,
    product_id  uuid
);

-- changeset Black77:2

INSERT INTO rule_table (product_id, query, arguments, negate)
VALUES
('5fa6f2ed-9d9b-4bc6-9d7e-6787bbcf8996', 'USER_OF', '{"CREDIT"}', true),
('5fa6f2ed-9d9b-4bc6-9d7e-6787bbcf8996', 'TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW', '{"DEBIT", ">"}', false),
('5fa6f2ed-9d9b-4bc6-9d7e-6787bbcf8996', 'TRANSACTION_SUM_COMPARE', '{"DEBIT", "DEPOSIT", ">", "100000"}', false),
('564c7628-2e51-4a09-a8a8-4dbb38441492', 'USER_OF', '{"DEBIT"}', false),
('564c7628-2e51-4a09-a8a8-4dbb38441492', 'USER_OF', '{"INVEST"}', true),
('564c7628-2e51-4a09-a8a8-4dbb38441492', 'TRANSACTION_SUM_COMPARE', '{"SAVING", "DEPOSIT", ">", "1000"}', false),
('47c1edbb-2799-4af3-b901-7518a9f8d204', 'USER_OF', '{"DEBIT"}', false),
('47c1edbb-2799-4af3-b901-7518a9f8d204', 'TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW', '{"DEBIT", ">"}', false),
('47c1edbb-2799-4af3-b901-7518a9f8d204', 'TRANSACTION_SUM_COMPARE', '{"DEBIT", "DEPOSIT", ">", "50000"}', false);
