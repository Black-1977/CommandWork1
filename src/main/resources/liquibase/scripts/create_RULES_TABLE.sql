-- liquibase formatted sql

-- changeset formatted napkin:3
DROP TABLE IF EXISTS rules_table CASCADE;
CREATE TABLE rules_table(
    query TEXT PRIMARY KEY,
    arguments TEXT,
    negate BOOLEAN
);