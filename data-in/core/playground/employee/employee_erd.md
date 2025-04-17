# Employee Database Schema

## 1. Employees
- **Description**: Tracks details of all employees.
- **Columns**:
    - `id` (Primary Key): Unique identifier for each employee.
    - `name`: Name of the employee.
    - `title`: Job title of the employee (e.g., "Software Engineer").
    - `dept_id` (Foreign Key): The department the employee belongs to (linked to `Departments.id`).
    - `location_id` (Foreign Key): The location where the employee is based (linked to `Locations.id`).
    - `hire_date`: The date the employee was hired.
- **Relationships**:
    - **Many-to-One**: With `Departments` (via `dept_id`).
    - **Many-to-One**: With `Locations` (via `location_id`).
    - **One-to-Many**: With `Salaries`.

## 2. Departments
- **Description**: Tracks details about organizational departments.
- **Columns**:
    - `id` (Primary Key): Unique identifier for each department.
    - `name`: Name of the department (e.g., "Engineering").
    - `location_id` (Foreign Key): The location of the department (linked to `Locations.id`).
- **Relationships**:
    - **One-to-Many**:
        - With `Employees` (via `dept_id`).
        - With `Projects` (via `department_id`).
    - **Many-to-One**: With `Locations`.

## 3. Locations
- **Description**: Tracks physical locations of departments and employees.
- **Columns**:
    - `id` (Primary Key): Unique identifier for each location.
    - `name`: Name of the location (e.g., "New York").
    - `address`: Full address of the location (e.g., "123 5th Avenue").
    - `timezone`: The timezone of the location (e.g., "EST").
- **Relationships**:
    - **One-to-Many**:
        - With `Departments`.
        - With `Employees`.

## 4. Projects
- **Description**: Tracks projects managed by various departments.
- **Columns**:
    - `id` (Primary Key): Unique identifier for each project.
    - `name`: Name of the project (e.g., "Project Apollo").
    - `department_id` (Foreign Key): The department managing the project (linked to `Departments.id`).
    - `start_date`: The starting date of the project.
    - `end_date`: The ending date of the project.
- **Relationships**:
    - **Many-to-One**: With `Departments`.

## 5. Salaries
- **Description**: Tracks salary records for employees.
- **Columns**:
    - `id` (Primary Key): Unique identifier for each salary record.
    - `employee_id` (Foreign Key): The employee whose salary is tracked (linked to `Employees.id`).
    - `effective_date`: The date the salary became effective.
    - `amount`: Salary amount in dollars ($).
- **Relationships**:
    - **Many-to-One**: With `Employees`.

## Relationships Overview
### 1. Employees → Departments
- **Type**: Many-to-One
- **Details**: Each employee belongs to one department, but a department can have many employees.

### 2. Employees → Locations
- **Type**: Many-to-One
- **Details**: Each employee is based at one location, but a location can host multiple employees.

### 3. Departments → Locations
- **Type**: Many-to-One
- **Details**: Each department resides at one location, but a location can host multiple departments.

### 4. Projects → Departments
- **Type**: Many-to-One
- **Details**: Each project is associated with one department, but a department can manage multiple projects.

### 5. Salaries → Employees
- **Type**: Many-to-One
- **Details**: Each salary record belongs to one employee, but an employee can have multiple salary records (e.g., historical data).