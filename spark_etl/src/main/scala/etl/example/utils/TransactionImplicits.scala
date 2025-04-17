package etl.example.utils

import etl.example.caspian.CaspianContext
import etl.example.constants.NameConstants._
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._
import etl.example.constants.TableConstants._

object TransactionImplicits {

  implicit class TransactionRead(ctx: CaspianContext) {

    def getAllTransactions: DataFrame = {
      ctx.getSparkSession.read
        .json("data/Small-Bank-Transactions.json")
    }
  }

  implicit class RenameColumn(df: DataFrame) {

    def columnRenamed(renames: Map[String, String]): DataFrame = {
      renames.foldLeft(df)((origDf, item) => origDf.withColumnRenamed(item._1, item._2))
    }
  }

  implicit class YearMonth(txnDF: DataFrame) {

    def addYearMonthColumns(): DataFrame = {
      txnDF
        .withColumn(YEAR, substring(col(YEAR_MONTH).cast("string"), 1, 4).cast("int"))
        .withColumn(MONTH, substring(col(YEAR_MONTH).cast("string"), 5, 2).cast("int"))
        .drop(YEAR_MONTH)
    }
  }

}
