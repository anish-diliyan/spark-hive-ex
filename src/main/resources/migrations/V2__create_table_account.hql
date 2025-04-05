CREATE TABLE account (
    acct_id STRING COMMENT 'Account ID/IBAN - Primary key',
    acct_ccy STRING COMMENT 'Account Currency Code',
    bal_aftr_bookg DECIMAL(10,2) COMMENT 'Balance after booking in decimal format',
    bal_aftr_bookg_nmrc BIGINT COMMENT 'Balance after booking in numeric format'
)
COMMENT 'Account information table'
TBLPROPERTIES (
    'parquet.compression'='SNAPPY',
    'auto.purge'='true'
)
PARTITIONED BY (year INT, month INT)
FIELDS TERMINATED BY ','
STORED AS PARQUET;