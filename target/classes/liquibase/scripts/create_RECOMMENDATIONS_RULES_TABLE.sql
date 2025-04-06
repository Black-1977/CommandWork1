-- liquibase formatted sql

DROP TABLE IF EXISTS recommendations_rules_table CASCADE;
CREATE TABLE recommendations_rules_table(
    name TEXT REFERENCES recommendations_table,
    query TEXT REFERENCES rules_table,
    PRIMARY KEY (name, query)
);