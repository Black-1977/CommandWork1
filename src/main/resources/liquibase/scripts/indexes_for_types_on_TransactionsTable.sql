-- liquibase formatted sql

-- changeset formatted ityapkin:1

CREATE INDEX idx_transactions_type ON transactions(type);