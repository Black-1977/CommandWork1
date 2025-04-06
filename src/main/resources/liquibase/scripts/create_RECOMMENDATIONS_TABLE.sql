-- liquibase formatted sql


DROP TABLE IF EXISTS recommendations_table CASCADE;
CREATE TABLE recommendations_table(
    name TEXT PRIMARY KEY,
    description TEXT
);