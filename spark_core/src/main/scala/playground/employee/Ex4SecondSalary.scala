package playground.employee

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.{col, dense_rank}
import playground.InputDataReadEmp

object Ex4SecondSalary extends App {

  val spark: SparkSession = SparkSession
    .builder()
    .appName("Ex4 Second Salary")
    .config("spark.master", "local")
    .getOrCreate()
  val inputDataReader = new InputDataReadEmp(spark)

  val salaryDF =
    inputDataReader.getSalariesDF.select(
      col("employee_id").as("sal_emp_id"),
      col("amount").as("salary")
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

  val rankWindow = Window.partitionBy(col("dept_id")).orderBy(col("salary").desc)
  val rankedSalariesDF = employeeDF.join(salaryDF, empSalJoinCondition, "inner")
    .join(departmentDF, empDeptJoinCondition, "inner")
    .select(
        col("emp_name"),
      col("dept_name"),
      col("salary"),
      dense_rank().over(rankWindow).as("salary_rank")
    )
  val resultDF = rankedSalariesDF.filter(col("salary_rank") === 2)
    .select(
      col("emp_name"),
      col("dept_name"),
      col("salary")
    )
  resultDF.show()
}
