package playground.employee

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import playground.InputDataRead

/*
  1. Only departments with an average salary above 50,000
  2. For each of these departments:
     a. Show the department name
     b. The total number of employees in that department
     c. The average salary in that department
 */
/*
  SELECT
    d.name as dept_name
    sum(e.id) as total_employees,
    avg(e.salary) as average_salary
  FROM
    employees e INNER JOIN departments d
  ON
    e.dept_id = d.id
  GROUP BY
    e.dept_id
  HAVING
    avg(e.salary) > 50000;
 */
object AverageSalary extends App {
  val spark = SparkSession
    .builder()
    .appName("Average Salary")
    .config("spark.master", "local")
    .getOrCreate()

  // id | name | salary | dept_id | hire_date
  val employeesDF = InputDataRead.getEmployeesDF(spark)
  // id | name | location
  val departmentsDF = InputDataRead.getDepartmentsDF(spark)

  val SALARY_THRESHOLD = 50000
  val employeesProjected = employeesDF.select("dept_id", "salary")
  val departmentsProjected = departmentsDF.select("id", "name")

  val joinCondition = col("dept_id") === col("id")
  val resultDF = employeesProjected
    .join(departmentsProjected, joinCondition, "inner")
    .groupBy(col("dept_id"), col("name").as("dept_name"))
    .agg(
      count("*").as("total_employees"),
      avg(col("salary")).as("average_salary")
    )
    .filter(col("average_salary") > SALARY_THRESHOLD)
    .select(
      col("dept_name"),
      col("total_employees"),
      col("average_salary")
    )
  resultDF.show()
}
