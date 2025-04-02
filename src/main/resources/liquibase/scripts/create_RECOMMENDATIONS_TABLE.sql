-- liquibase formatted sql

-- changeset formatted ityapkin:16
CREATE TABLE recommendations_table(
    rec_id UUID,
    name TEXT,
    rules TEXT,
    description TEXT
);