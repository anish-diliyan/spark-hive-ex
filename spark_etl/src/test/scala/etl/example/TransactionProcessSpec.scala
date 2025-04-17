package etl.example

import etl.example.caspian.{CaspianContext, CaspianEnv}

import java.time.LocalDate

object TransactionProcessSpec extends App {
  val process = new TransactionProcess()
  val context = CaspianContext()
  val currentDate = LocalDate.now().minusDays(1) // yesterday's date
  val environment = CaspianEnv.DEV
  val migration = true
  // Run the process
  process.run(ctx = context, date = currentDate, env = environment, migration = migration)
}
