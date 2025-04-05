package com.example

import com.example.caspian.{CaspianContext, CaspianEnv, CaspianProcess}
import com.example.core.HiveMigrationHandler

import java.time.LocalDate

class TransactionProcess extends CaspianProcess {
  override def run(ctx: CaspianContext, date: LocalDate, env: CaspianEnv, migration: Boolean): Unit = {
    println(s"Transaction Process started with env: $env , date: $date , migration: $migration")
    new HiveMigrationHandler(ctx.getSparkSession).executeMigration()
  }
}

