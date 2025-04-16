-- liquibase formatted sql


INSERT INTO rule_table (id, recommendation_id, query, arguments, negate)
VALUES
(1, 1, 'USER_OF', 'CREDIT', true),
(2, 1, 'ACTIVE_USER_OF', 'DEBIT', false),
(3, 1, 'TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW', 'DEPOSIT,>', false),
(4, 1, 'TRANSACTION_SUM_COMPARE', 'DEBIT,DEPOSIT,>,100000', false);