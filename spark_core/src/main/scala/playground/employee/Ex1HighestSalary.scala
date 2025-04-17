package playground.employee

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions.{col, count}
import playground.InputDataReadEmp

/*
  1. Find the department name with the highest number of employees.
  2. Display the department name and the employee count for it.
 */
object Ex1HighestSalary extends App {

  val spark: SparkSession = SparkSession
    .builder()
    .appName("Ex1 Highest Salary")
    .config("spark.master", "local")
    .getOrCreate()

  // Create an instance of InputDataReadEmp and provide the SparkSession
  val inputDataReader = new InputDataReadEmp(spark)

  // Fetch the needed DataFrames, and select the required columns
  val employeesDF = inputDataReader.getEmployeesDF.select(
      col("id").as("emp_id"),
      col("dept_id").as("emp_dept_id")
  )

  val departmentsDF = inputDataReader.getDepartmentsDF.select(
      col("id").as("dept_id"),
      col("name")
  )

  val joinCondition = col("dept_id") === col("emp_dept_id")

  val resultDF: DataFrame = employeesDF.join(departmentsDF, joinCondition, "inner")
    .groupBy(col("name"))
    .agg(
        count(col("emp_id")).as("emp_count")
    )
    .select(
        col("name"),
        col("emp_count")
    )
    .orderBy(col("emp_count").desc)
    .limit(1)

  resultDF.show()
}
