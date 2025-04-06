-- liquibase formatted sql

-- changeset formatted napkin:3

INSERT INTO rules_table (query, arguments, negate)
VALUES
('USER_OF', 'CREDIT', true),
('TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW', 'DEBIT,DEPOSIT,>,DEBIT,WITHDRAW', false),
('TRANSACTION_SUM_COMPARE', 'DEBIT,DEPOSIT,>,100000', false);