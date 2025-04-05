package com.example.transactions.helper

import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{DataFrame, SparkSession, functions}

class CpMasterHelper(txnDF: DataFrame)(implicit spark: SparkSession) {

  def getCpMasterTableDF: DataFrame = {
    txnDF.select(
      col("ctpty_agt_bic").as("bank_code"),
      col("ctpty_acct_id_iban").as("account_number"),
      col("ctpty_nm").as("full_name"),
      col("ctpty_acct_ccy").as("currency"),
      col("ctpty_ctry").as("country"),
      functions.split(col("ctpty_adr_line1"), "\n").getItem(0).as("street"),
      functions.split(col("ctpty_adr_line1"), "\n").getItem(1).as("city"),
      functions.split(col("ctpty_adr_line1"), "\n").getItem(2).as("zip_code"),
      col("booking_id").as("trans_key")
    )
  }

}
