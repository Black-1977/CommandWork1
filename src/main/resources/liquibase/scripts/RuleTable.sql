-- liquibase formatted sql

-- changeset Black77:1
create table if not exists rule_table
(
    id          uuid default gen_random_uuid() not null primary key,
    query       text not null,
    arguments   text [] not null,
    negate      boolean not null,
    product_id  uuid
);
