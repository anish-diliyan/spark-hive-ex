package playground.orders

object MedianSalary extends App {

  /*
  -- Note: PERCENTILE_CONT is not supported in all SQL flavors
-- Using PostgreSQL syntax
SELECT department_name,
       PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY salary) as median_salary
FROM Employees e
JOIN Departments d ON e.department_id = d.department_id
GROUP BY department_name;

   */

}
