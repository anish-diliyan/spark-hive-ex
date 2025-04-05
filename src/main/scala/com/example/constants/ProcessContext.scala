package com.example.constants

import com.example.caspian.CaspianContext

import java.time.LocalDate

case class ProcessContext(caspianContext: CaspianContext, cspBusDt: LocalDate, executionId: String)
