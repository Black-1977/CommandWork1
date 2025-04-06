-- liquibase formatted sql

-- changeset formatted ityapkin:18
CREATE UNIQUE INDEX idx_id_deposit ON USERS_DEPOSITS (user_id);