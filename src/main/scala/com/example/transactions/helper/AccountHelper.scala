package com.example.transactions.helper

import com.example.constants.NameConstants._
import com.example.constants.TableConstants._
import com.example.core.LoggerImpl
import org.apache.spark.sql.functions.{col, count}
import org.apache.spark.sql.types.DecimalType
import org.apache.spark.sql.{DataFrame, functions}
import com.example.utils.TransactionImplicits.YearMonth
import com.example.utils.TransactionImplicits.RenameColumn

object AccountHelper extends LoggerImpl {

  def getAccountData(completeTxnDF: DataFrame): DataFrame = {
    val accountDetailsDf = completeTxnDF.select(
      col(ACCT_ID),
      col(ACCT_CCY),
      col(BAL_AFTR_BOOKG_NMRC),
      col(YEAR_MONTH)
    )
    accountDetailsDf.groupBy(ACCT_ID, ACCT_CCY, YEAR_MONTH).agg(
      functions.max(BAL_AFTR_BOOKG_NMRC).cast(DecimalType(20, 2)).as(BAL_AFTR_BOOKG_NMRC)
    ).addYearMonthColumns().columnRenamed(RENAME_ACCOUNT_MAP)
  }

  def getCountGroupByYearMonth(completeTxnDF: DataFrame): DataFrame = {
    completeTxnDF.groupBy(YEAR_MONTH).agg(count("*"))
  }

}
