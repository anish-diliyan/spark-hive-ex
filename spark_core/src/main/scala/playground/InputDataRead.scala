package playground

import org.apache.spark.sql.functions.{col, explode}
import org.apache.spark.sql.{DataFrame, SparkSession}

object InputDataRead {

  def getEmployeesDF(spark: SparkSession): DataFrame = {
    val employeesDF = spark.read
      .option("multiline", "true")
      .json("data-in/core/playground/employees.json")

    // Then explode the employees array and select all fields
    employeesDF.select(explode(col("employees")).as("employee")).select("employee.*")
  }

  def getDepartmentsDF(spark: SparkSession): DataFrame = {
    val departmentsDF = spark.read
      .option("multiline", "true")
      .json("data-in/core/playground/departments.json")

    // Then explode the departments array and select all fields
    departmentsDF.select(explode(col("departments")).as("departments")).select("departments.*")
  }

}
