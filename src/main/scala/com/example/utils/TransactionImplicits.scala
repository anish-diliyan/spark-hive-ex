package com.example.utils

import com.example.caspian.CaspianContext
import org.apache.spark.sql.DataFrame

object TransactionImplicits {

  implicit class TransactionRead(ctx: CaspianContext) {
    def getAllTransactions: DataFrame = {
      ctx.getSparkSession.read
        .option("multiline", "true")
        .option("inferSchema", "true")
        .json("data/Small-Bank-Transactions.json")
    }
  }

  implicit class RenameColumn(df: DataFrame) {
    def columnRenamed(renames: Map[String, String]): DataFrame = {
      renames.foldLeft(df)((origDf, item) => origDf.withColumnRenamed(item._1, item._2))
    }
  }

}
