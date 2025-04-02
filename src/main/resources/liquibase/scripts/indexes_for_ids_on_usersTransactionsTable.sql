-- liquibase formatted sql

-- changeset formatted ityapkin:11
CREATE INDEX user_id_index ON users_transactions(user_id)