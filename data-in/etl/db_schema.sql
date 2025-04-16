CREATE TABLE Account (
    acct_id VARCHAR(34) PRIMARY KEY,
    acct_ccy VARCHAR(3) NOT NULL,
    bal_aftr_bookg DECIMAL(10, 2) NOT NULL,
    bal_aftr_bookg_nmrc INT NOT NULL
);

CREATE TABLE Participant (
    participant_id UUID PRIMARY KEY,
    acct_id VARCHAR(34),
    ctpty_nm VARCHAR(255) NOT NULL,
    ctpty_ctry VARCHAR(255) NOT NULL,
    ctpty_adr_line1 TEXT NOT NULL,
    ctpty_acct_ccy VARCHAR(3) NOT NULL,
    ctpty_acct_id_bban VARCHAR(34) NOT NULL,
    ctpty_acct_id_iban VARCHAR(34) NOT NULL,
    ctpty_agt_bic VARCHAR(255) NOT NULL,
    FOREIGN KEY (acct_id) REFERENCES Account(acct_id) ON DELETE CASCADE
);

CREATE TABLE Transaction (
    booking_id UUID PRIMARY KEY,
    bookg_amt DECIMAL(10, 2) NOT NULL,
    bookg_amt_nmrc INT NOT NULL,
    bookg_cdt_dbt_ind VARCHAR(4) NOT NULL,
    bookg_dt_tm_cet TIMESTAMP NOT NULL,
    bookg_dt_tm_gmt TIMESTAMP NOT NULL,
    year_month INT NOT NULL,
    end_to_end_id UUID NOT NULL,
    tx_acct_svcr_ref UUID NOT NULL,
    ntry_seq_nb INT NOT NULL,
    dtld_tx_tp INT NOT NULL,
    rmt_inf_ustrd1 VARCHAR(255) NOT NULL,
    csvbase_row_id INT NOT NULL
);

CREATE TABLE TransactionParticipant (
    transaction_participant_id UUID PRIMARY KEY,
    booking_id UUID,
    participant_id UUID,
    role VARCHAR(10) CHECK (role IN ('SENDER', 'RECEIVER')),
    FOREIGN KEY (booking_id) REFERENCES Transaction(booking_id) ON DELETE CASCADE,
    FOREIGN KEY (participant_id) REFERENCES Participant(participant_id) ON DELETE CASCADE
);

INSERT INTO Account (acct_id, acct_ccy, bal_aftr_bookg, bal_aftr_bookg_nmrc) VALUES
('NL12FAKE2629464121', 'EUR', 2405.00, 240500),
('GB37FAKE5814414399', 'GBP', 1500.00, 150000);

INSERT INTO Participant (participant_id, acct_id, ctpty_nm, ctpty_ctry, ctpty_adr_line1, ctpty_acct_ccy, ctpty_acct_id_bban, ctpty_acct_id_iban, ctpty_agt_bic) VALUES
('1a2b3c4d-5e6f-7g8h-9i10-jk1112131415', 'NL12FAKE2629464121', 'John Doe', 'Netherlands', '123 Fake St, Fake City', 'EUR', 'NL12FAKE123456789', 'NL12FAKE1234567890', 'BANKNL2Y'),
('2b3c4d5e-6f7g-8h9i-10jk-111213141516', 'GB37FAKE5814414399', 'Victoria Hendrikse', 'Great Britain', '023 Williamson Mills, East Laurenland', 'GBP', '581_44_14_399', 'GB37FAKE5814414399', 'qtmAselRqX');

INSERT INTO Transaction (booking_id, bookg_amt, bookg_amt_nmrc, bookg_cdt_dbt_ind, bookg_dt_tm_cet, bookg_dt_tm_gmt, year_month, end_to_end_id, tx_acct_svcr_ref, ntry_seq_nb, dtld_tx_tp, rmt_inf_ustrd1, csvbase_row_id) VALUES
('c834efe7-aa11-4c60-acfb-ad5c369f3d28', 3.00, 300, 'CRDT', '2024-06-01 00:02:37', '2024-06-01 00:02:37', 202406, '1102707c-63ca-4071-aa52-9ec2fcf923f2', '4bb46134-df94-4d25-8709-713d972d43dc', 1804543, 4111, 'Wedding gift', 1);

INSERT INTO TransactionParticipant (transaction_participant_id, booking_id, participant_id, role) VALUES
('3c4d5e6f-7g8h-9i10-jk11-121314151617', 'c834efe7-aa11-4c60-acfb-ad5c369f3d28', '1a2b3c4d-5e6f-7g8h-9i10-jk1112131415', 'SENDER'),
('4d5e6f7g-8h9i-10jk-1112-131415161718', 'c834efe7-aa11-4c60-acfb-ad5c369f3d28', '2b3c4d5e-6f7g-8h9i-10jk-111213141516', 'RECEIVER');


