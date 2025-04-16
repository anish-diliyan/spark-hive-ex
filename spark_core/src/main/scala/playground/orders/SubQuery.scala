package playground.orders

object SubQuery extends App {

  /*
  SELECT department_name,
       (SELECT COUNT(*)
        FROM Employees e2
        WHERE e2.department_id = d.department_id
        AND salary > (SELECT AVG(salary) FROM Employees)
       ) as high_paid_employees
FROM Departments d;

   */
  /*
  val avgSalary = employeesDF.select(avg("salary")).first().getDouble(0)

val highPaidCounts = employeesDF
  .filter($"salary" > avgSalary)
  .groupBy("department_id")
  .count()
  .withColumnRenamed("count", "high_paid_employees")

departmentsDF
  .join(highPaidCounts, "department_id", "left")
  .na.fill(0)
  .show()

   */

}
