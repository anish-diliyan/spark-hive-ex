-- 1. Find the department name with the highest number of employees.

SELECT d.name AS department_name, COUNT(e.id) AS employee_count
FROM Departments d
JOIN Employees e ON d.id = e.dept_id
GROUP BY d.name
ORDER BY employee_count DESC
LIMIT 1;

-- 2. Calculate the total salary paid to each department.

SELECT d.name AS department_name, SUM(s.amount) AS total_salary
FROM Departments d
JOIN Employees e ON d.id = e.dept_id
JOIN Salaries s ON e.id = s.employee_id
GROUP BY d.name
ORDER BY total_salary DESC;

-- 3. Rank employees based on their salaries within their respective departments.

SELECT e.name AS employee_name, d.name AS department_name, s.amount AS salary,
       RANK() OVER (PARTITION BY d.id ORDER BY s.amount DESC) AS rank_within_department
FROM Employees e
JOIN Salaries s ON e.id = s.employee_id
JOIN Departments d ON e.dept_id = d.id;

-- 4. Find employees who have the second-highest salary in their department.

WITH RankedSalaries AS (
  SELECT e.id AS employee_id, e.name AS employee_name, d.name AS department_name, s.amount AS salary,
         DENSE_RANK() OVER (PARTITION BY d.id ORDER BY s.amount DESC) AS salary_rank
  FROM Employees e
  JOIN Salaries s ON e.id = s.employee_id
  JOIN Departments d ON e.dept_id = d.id
)
SELECT employee_name, department_name, salary
FROM RankedSalaries
WHERE salary_rank = 2;

-- 5. Find departments and their locations where more than 3 employees were hired after 2018.

SELECT d.name AS department_name, l.name AS location_name, COUNT(e.id) AS employee_count
FROM Departments d
JOIN Locations l ON d.location_id = l.id
JOIN Employees e ON d.id = e.dept_id
WHERE e.hire_date > '2018-01-01'
GROUP BY d.name, l.name
HAVING COUNT(e.id) > 3;

-- 6. Find employees whose salary increased by more than $10,000 over time.

SELECT e.name AS employee_name, (s2.amount - s1.amount) AS salary_increase
FROM Employees e
JOIN Salaries s1 ON e.id = s1.employee_id
JOIN Salaries s2 ON e.id = s2.employee_id
WHERE s2.effective_date > s1.effective_date
  AND (s2.amount - s1.amount) > 10000
ORDER BY salary_increase DESC;

-- 7. List the top 3 highest-paid employees from each location.

SELECT l.name AS location_name, e.name AS employee_name, s.amount AS salary,
       RANK() OVER (PARTITION BY l.id ORDER BY s.amount DESC) AS rank_within_location
FROM Locations l
JOIN Employees e ON l.id = e.location_id
JOIN Salaries s ON e.id = s.employee_id
WHERE RANK() OVER (PARTITION BY l.id ORDER BY s.amount DESC) <= 3;

-- 8. Find all projects along with the number of employees from the department managing them.

SELECT p.name AS project_name, d.name AS department_name, COUNT(e.id) AS employee_count
FROM Projects p
JOIN Departments d ON p.department_id = d.id
JOIN Employees e ON d.id = e.dept_id
GROUP BY p.name, d.name;

-- 9. Find employees who work in a department located in a different city than their office location.

SELECT e.name AS employee_name, d.name AS department_name, l_emp.name AS employee_location, l_dept.name AS department_location
FROM Employees e
JOIN Departments d ON e.dept_id = d.id
JOIN Locations l_emp ON e.location_id = l_emp.id
JOIN Locations l_dept ON d.location_id = l_dept.id
WHERE e.location_id != d.location_id;

-- 10. Generate a hierarchical employee list, grouping employees by department and sorting by salary within each department

SELECT d.name AS department_name, e.name AS employee_name, s.amount AS salary
FROM Departments d
JOIN Employees e ON d.id = e.dept_id
JOIN Salaries s ON e.id = s.employee_id
ORDER BY department_name, salary DESC;