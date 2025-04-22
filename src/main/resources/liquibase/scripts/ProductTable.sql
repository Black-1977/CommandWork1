-- liquibase formatted sql

-- changeset Black77:1
create table if not exists product_table
(
    id             uuid default gen_random_uuid() not null primary key,
    recommendation uuid,
    rules          uuid[]
);


INSERT INTO product_table (id, recommendation)
VALUES
('5fa6f2ed-9d9b-4bc6-9d7e-6787bbcf8996', 'ab138afb-f3ba-4a93-b74f-0fcee86d447f'),
('564c7628-2e51-4a09-a8a8-4dbb38441492', '147f6a0f-3b91-413b-ab99-87f081d60d5a'),
('47c1edbb-2799-4af3-b901-7518a9f8d204', '59efc529-2fff-41af-baff-90ccd7402925');

