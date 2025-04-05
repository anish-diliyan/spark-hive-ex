package com.example.caspian

import com.example.core.SparkSessionProvider
import org.apache.spark.sql.SparkSession

class CaspianContext(spark: SparkSession) {
  def getSparkSession: SparkSession = spark
}

object CaspianContext {
  private val sparkSession = SparkSessionProvider.getSession
  def apply(): CaspianContext = new CaspianContext(sparkSession)
}
