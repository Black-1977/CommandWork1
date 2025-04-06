-- liquibase formatted sql


DROP TABLE IF EXISTS rules_table CASCADE;
CREATE TABLE rules_table(
    query TEXT PRIMARY KEY,
    arguments TEXT,
    negate BOOLEAN
);