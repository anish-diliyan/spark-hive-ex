DROP TABLE IF EXISTS counter_party.recipient_details;

CREATE EXTERNAL TABLE recipient_details (
    name STRING COMMENT 'Name of the person or entity receiving money',
    country STRING COMMENT 'Country where the recipient is located',
    address STRING COMMENT 'Complete postal address of the recipient',
    currency STRING COMMENT 'Currency of the recipient account (e.g., GBP for British Pounds)',
    local_account_number STRING COMMENT 'Local/domestic bank account number (BBAN)',
    international_account_number STRING COMMENT 'International Bank Account Number (IBAN)',
    bank_identifier_code STRING COMMENT 'Bank Identification Code (BIC/SWIFT code)'
)
COMMENT 'Table containing recipient/counterparty banking and personal details'
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
PARTITIONED BY (currency String)
STORED AS PARQUET;