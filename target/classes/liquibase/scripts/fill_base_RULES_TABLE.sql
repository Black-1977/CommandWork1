-- liquibase formatted sql


INSERT INTO rules_table (query, arguments, negate)
VALUES
('USER_OF', 'CREDIT', true),
('ACTIVE_USER_OF', 'DEBIT', false),
('TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW', 'DEBIT,DEPOSIT,>,DEBIT,WITHDRAW', false),
('TRANSACTION_SUM_COMPARE', 'DEBIT,DEPOSIT,>,100000', false);