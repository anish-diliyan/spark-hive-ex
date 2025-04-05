package com.example.transactions.writer

import org.apache.spark.sql.DataFrame

object CounterPartyWriter {
  def writeCounterParty(df: DataFrame, table: String): Unit = {
    df.write.mode("overwrite").insertInto(table)
  }
}
