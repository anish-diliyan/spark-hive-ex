package etl.example

import java.time.LocalDate
import etl.example.caspian.{CaspianContext, CaspianEnv, CaspianProcess}
import etl.example.core.{HiveMigrationHandler, LoggerImpl}
import etl.example.utils.TransactionImplicits.TransactionRead
import etl.example.transactions.helper.AccountHelper
import etl.example.transactions.writer.TransactionsWriter

class TransactionProcess extends CaspianProcess with LoggerImpl {

  override def run(
      ctx: CaspianContext,
      date: LocalDate,
      env: CaspianEnv,
      migration: Boolean
  ): Unit = {
    info(s"Transaction Process started with env: $env , date: $date , migration: $migration")

    if (migration)
      new HiveMigrationHandler(ctx.getSparkSession).executeMigration()
    else
      info("Migration skipped...because already applied")

    val completeTxnDF = ctx.getAllTransactions

    val accountDF = AccountHelper.getAccountData(completeTxnDF)
    info(accountDF.count() + "---------------------------")
    AccountHelper.getCountGroupByYearMonth(completeTxnDF).show()
    TransactionsWriter.writeCounterParty(accountDF, "transactions.account")
    // validate AccountDF is successfully written by reading the counter_party.account
    val accountDFFromHive = ctx.getSparkSession.table("transactions.account")
    accountDFFromHive.show(10, truncate = false)
  }
}
