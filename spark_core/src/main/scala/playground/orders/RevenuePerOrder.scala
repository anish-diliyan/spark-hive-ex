package playground.orders

import org.apache.spark.sql.SparkSession
import playground.{InputDataReadEmp, InputDataReadOrd}

object RevenuePerOrder extends App {

  val spark: SparkSession = SparkSession
    .builder()
    .appName("Order Database")
    .config("spark.master", "local")
    .getOrCreate()

  // Create an instance of InputDataReadEmp and provide the SparkSession
  val inputDataReader = new InputDataReadOrd(spark)

  // Fetch the needed DataFrames
  val customersDF = inputDataReader.getCustomersDF
  customersDF.show()
}
