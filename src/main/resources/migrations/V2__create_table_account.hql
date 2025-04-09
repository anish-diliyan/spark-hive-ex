DROP TABLE IF EXISTS transactions.account;

CREATE TABLE transactions.account (
    iban STRING COMMENT 'Account ID/IBAN - Primary key',
    currency STRING COMMENT 'Account Currency Code',
    balance BIGINT COMMENT 'Balance after booking in numeric format'
)
PARTITIONED BY (year INT, month INT)
STORED AS PARQUET;