package etl.example.constants

import NameConstants._

object TableConstants {
  val IBAN     = "iban"
  val CURRENCY = "currency"
  val BALANCE  = "balance"
  val YEAR     = "year"
  val MONTH    = "month"

  val RENAME_ACCOUNT_MAP: Map[String, String] = Map(
    ACCT_ID     -> IBAN,
    ACCT_CCY -> CURRENCY,
    BAL_AFTR_BOOKG_NMRC  -> BALANCE
  )
}
