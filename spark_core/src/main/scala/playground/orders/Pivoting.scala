package playground.orders

object Pivoting {
 /*
 SELECT *
FROM (
    SELECT
        department_name,
        QUARTER(hire_date) as quarter,
        salary
    FROM Employees e
    JOIN Departments d ON e.department_id = d.department_id
)
PIVOT (
    COUNT(*) as count,
    AVG(salary) as avg_salary
    FOR quarter IN (1,2,3,4)
);

  */

  /*
  employeesDF
  .join(departmentsDF, "department_id")
  .withColumn("quarter", quarter($"hire_date"))
  .groupBy("department_name")
  .pivot("quarter", Seq(1, 2, 3, 4))
  .agg(
    count("*").as("count"),
    avg("salary").as("avg_salary")
  )
  .show()

   */
}
