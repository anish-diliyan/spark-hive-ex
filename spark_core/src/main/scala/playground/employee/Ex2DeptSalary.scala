package playground.employee

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col
import playground.InputDataReadEmp
import org.apache.spark.sql.functions._

// Calculate the total salary paid to each department.
object Ex2DeptSalary extends App {

  val spark: SparkSession = SparkSession
    .builder()
    .appName("Ex2 Dept Salary")
    .config("spark.master", "local")
    .getOrCreate()
  val inputDataReader = new InputDataReadEmp(spark)

  val salaryDF =
    inputDataReader.getSalariesDF.select(col("employee_id").as("sal_emp_id"), col("amount"))

  val employeeDF =
    inputDataReader.getEmployeesDF.select(col("id").as("emp_id"), col("dept_id").as("emp_dept_id"))

  val departmentDF =
    inputDataReader.getDepartmentsDF.select(col("id").as("dept_id"), col("name").as("dept_name"))

  val empSalaryJoinCondition = col("sal_emp_id") === col("emp_id")
  val empDeptJoinCondition = col("emp_dept_id") === col("dept_id")

  val resultDF = salaryDF.join(employeeDF, empSalaryJoinCondition, "inner")
    .join(departmentDF, empDeptJoinCondition, "inner")
    .groupBy(col("dept_id"), col("dept_name"))
    .agg(
        sum(col("amount")).as("total_amount")
    )
    .select(
        col("dept_id"),
        col("dept_name"),
        col("total_amount")
    )
    .orderBy(col("dept_id").asc)

  resultDF.show()

}
