package com.example

import java.time.LocalDate
import com.example.caspian.{CaspianContext, CaspianEnv, CaspianProcess}
import com.example.core.{HiveMigrationHandler, LoggerImpl}
import com.example.utils.TransactionImplicits.TransactionRead


class TransactionProcess extends CaspianProcess with LoggerImpl {
  override def run(ctx: CaspianContext, date: LocalDate, env: CaspianEnv, migration: Boolean): Unit = {
    println(s"Transaction Process started with env: $env , date: $date , migration: $migration")

    if(migration)
      new HiveMigrationHandler(ctx.getSparkSession).executeMigration()
    else
      info("Migration skipped...because already applied")

    val completeTxnDF = ctx.getAllTransactions

    completeTxnDF.show(10, truncate = false)
  }
}

