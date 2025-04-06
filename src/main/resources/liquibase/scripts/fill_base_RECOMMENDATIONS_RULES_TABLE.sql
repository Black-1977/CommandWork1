-- liquibase formatted sql

-- changeset formatted napkin:1

INSERT INTO recommendations_rules_table (name, query)
VALUES
('простой кредит', 'USER_OF'),
('простой кредит', 'TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW'),
('простой кредит', 'TRANSACTION_SUM_COMPARE');