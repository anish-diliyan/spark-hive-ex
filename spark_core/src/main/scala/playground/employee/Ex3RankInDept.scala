package playground.employee

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.{col, rank}
import playground.InputDataReadEmp

// Rank employees based on their salaries within their respective departments.
object Ex3RankInDept extends App {

  val spark: SparkSession = SparkSession
    .builder()
    .appName("Ex3 Rank Salary")
    .config("spark.master", "local")
    .getOrCreate()
  val inputDataReader = new InputDataReadEmp(spark)

  val salaryDF =
    inputDataReader.getSalariesDF.select(
        col("employee_id").as("sal_emp_id"),
        col("amount")
    )

  val employeeDF =
    inputDataReader.getEmployeesDF.select(
        col("id").as("emp_id"),
        col("dept_id").as("emp_dept_id"),
        col("name").as("emp_name")
    )

  val departmentDF =
    inputDataReader.getDepartmentsDF.select(
        col("id").as("dept_id"),
        col("name").as("dept_name")
    )

  val empSalJoinCondition = col("emp_id") === col("sal_emp_id")
  val empDeptJoinCondition = col("emp_dept_id") === col("dept_id")

  val rankWindow = Window.partitionBy(col("dept_id")).orderBy(col("amount").desc)

  val resultDF = employeeDF.join(salaryDF, empSalJoinCondition, "inner")
    .join(departmentDF, empDeptJoinCondition, "inner")
    .select(
        col("emp_name"),
        col("dept_name"),
        col("amount"),
        rank().over(rankWindow).as("rank_in_dept")
    )
  resultDF.show()
}
