package com.example

import java.time.LocalDate
import com.example.caspian.{CaspianContext, CaspianEnv, CaspianProcess}
import com.example.core.{HiveMigrationHandler, LoggerImpl}
import com.example.transactions.helper.AccountHelper
import com.example.transactions.writer.TransactionsWriter
import com.example.utils.TransactionImplicits.TransactionRead

class TransactionProcess extends CaspianProcess with LoggerImpl {
  override def run(ctx: CaspianContext, date: LocalDate, env: CaspianEnv, migration: Boolean): Unit = {
    info(s"Transaction Process started with env: $env , date: $date , migration: $migration")

    if(migration)
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

