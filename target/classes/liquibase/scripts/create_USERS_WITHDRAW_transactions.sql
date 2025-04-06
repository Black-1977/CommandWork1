-- liquibase formatted sql

-- changeset formatted ityapkin:12

CREATE TABLE users_withdraws (
    user_id UUID,
    debit_amount DECIMAL(10,2),
    saving_amount DECIMAL(10,2),
    credit_amount DECIMAL(10,2),
    invest_amount DECIMAL(10,2)
);