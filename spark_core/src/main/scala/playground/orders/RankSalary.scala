package playground.orders

object RankSalary extends App {

  /*
  SELECT name,
       department_name,
       salary,
       RANK() OVER (PARTITION BY department_name ORDER BY salary DESC) as salary_rank
FROM Employees e
JOIN Departments d ON e.department_id = d.department_id;

   */
  /*
  import org.apache.spark.sql.expressions.Window

val windowSpec = Window.partitionBy("department_name").orderBy($"salary".desc)

employeesDF
  .join(departmentsDF, "department_id")
  .withColumn("salary_rank", rank().over(windowSpec))
  .select("name", "department_name", "salary", "salary_rank")
  .show()

   */
}
