package etl.example.constants

import etl.example.caspian.CaspianContext
import java.time.LocalDate

case class ProcessContext(caspianContext: CaspianContext, cspBusDt: LocalDate, executionId: String)
