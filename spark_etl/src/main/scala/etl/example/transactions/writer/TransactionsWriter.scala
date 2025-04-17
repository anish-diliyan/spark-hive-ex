package etl.example.transactions.writer

import org.apache.spark.sql.DataFrame

object TransactionsWriter {

  def writeCounterParty(df: DataFrame, table: String): Unit = {
    df.write.mode("overwrite").insertInto(table)
  }
}
