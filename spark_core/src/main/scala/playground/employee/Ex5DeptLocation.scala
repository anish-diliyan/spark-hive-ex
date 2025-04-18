package playground.employee

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, count}
import playground.InputDataReadEmp
import playground.employee.Ex4SecondSalary.inputDataReader

// Find departments and their locations where more than 3 employees were hired after 2018.
object Ex5DeptLocation extends App {

  val spark: SparkSession = SparkSession
    .builder()
    .appName("Ex5 Dept Location")
    .config("spark.master", "local")
    .getOrCreate()
  val inputDataReader = new InputDataReadEmp(spark)

  val employeeDF =
    inputDataReader.getEmployeesDF.filter(col("hire_date") > "2018-01-01").select(
      col("dept_id").as("emp_dept_id"),
      col("location_id").as("emp_loc_id")
    )

  val departmentDF =
    inputDataReader.getDepartmentsDF.select(
      col("id").as("dept_id"),
      col("name").as("dept_name")
    )

  val locationDF =
    inputDataReader.getLocationsDF.select(
      col("id").as("loc_id"),
      col("name").as("loc_name"),
      col("address").as("loc_address")
    )

  val empDeptJoinCondition = col("emp_dept_id") === col("dept_id")
  val empLocJoinCondition = col("emp_loc_id") === col("loc_id")

  val resultDF = employeeDF.join(departmentDF, empDeptJoinCondition, "inner")
    .join(locationDF, empLocJoinCondition, "inner")
    .groupBy("dept_name", "loc_name")
    .agg(
      count("*").as("emp_count")
    )
    .filter(col("emp_count") > 3)
    .select(
        col("dept_name"),
      col("loc_name"),
      col("emp_count")
    )

  resultDF.show()
}
