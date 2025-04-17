package playground.employee

import org.apache.spark.sql.SparkSession
import playground.InputDataReadEmp

object HighestSalary extends App {

  val spark: SparkSession = SparkSession
    .builder()
    .appName("Employee Database")
    .config("spark.master", "local")
    .getOrCreate()

  // Create an instance of InputDataReadEmp and provide the SparkSession
  val inputDataReader = new InputDataReadEmp(spark)

  // Fetch the needed DataFrames
  val employeesDF = inputDataReader.getEmployeesDF
  employeesDF.show()
}
