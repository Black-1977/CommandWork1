-- liquibase formatted sql

-- changeset formatted ityapkin:3

INSERT INTO users_transactions (user_id, debit_amount, invest_amount,
credit_amount, saving_amount)
SELECT
    t.user_id,
    SUM(CASE WHEN p.type = 'DEBIT' THEN t.amount ELSE 0 END) AS debit_amount,
    SUM(CASE WHEN p.type = 'INVEST' THEN t.amount ELSE 0 END) AS invest_amount,
    SUM(CASE WHEN p.type = 'CREDIT' THEN t.amount ELSE 0 END) AS credit_amount,
    SUM(CASE WHEN p.type = 'SAVING' THEN t.amount ELSE 0 END) AS saving_amount
FROM
    transactions t
JOIN
    products p ON t.product_id = p.id
GROUP BY
    t.user_id;