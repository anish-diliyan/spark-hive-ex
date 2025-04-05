package com.example.constants

object TableColumnName {

  val NAME = "name"
  val ORIG_NAME = "orig_name"
  val ADDRESS = "address"
  val BANK_ACCT = "bank_acct"

  val BANK_NAME = "bank_name"
  val BANK_ID = "bank_id"
  val BANK_ADDRESS = "bank_address"

  val SRC_SYS_ID = "src_sys_id"
  val ORIG_SYS_ID = "orig_sys_id"
  val CSP_BUS_DT = "csp_bus_dt"
  val AML_TRANS_KEY = "aml_trans_key"

  val CP_STAGING_COLN: Seq[String] = Seq(
    NAME, ADDRESS, BANK_ACCT,
    BANK_NAME, BANK_ID, BANK_ADDRESS,
    SRC_SYS_ID, ORIG_SYS_ID, CSP_BUS_DT, AML_TRANS_KEY
  )

}
