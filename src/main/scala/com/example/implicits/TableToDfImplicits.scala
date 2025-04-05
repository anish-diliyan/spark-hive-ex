package com.example.implicits

import com.example.constants.AccountIdTypeId.AccountIdTypeId
import com.example.constants.{AccountIdTypeId, ProcessContext}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions.col

import java.time.LocalDate

object TableToDfImplicits {

  implicit class TransactionRead(ctx: ProcessContext) {
    val sparkSession: SparkSession = ctx.caspianContext.getSparkSession
    val cspBusDt: LocalDate = ctx.cspBusDt
    def getTransactions(source: AccountIdTypeId): DataFrame = {
      source match {
//        case AccountIdTypeId.EBOSS => sparkSession.createDataFrame(eboss).filter(col(CSP_BUS_DT) === cspBusDt.toCspDateString)
//        case AccountIdTypeId.RMBSA => sparkSession.createDataFrame(rmbsa).filter(col(CSP_BUS_DT) === cspBusDt.toCspDateString)
        case _ => throw new Exception("Invalid AccountIdTypeId")
      }
    }
  }

}
