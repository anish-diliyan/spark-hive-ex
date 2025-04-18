## 1. What is DataFrame?
A **DataFrame** is a fundamental data structure in Apache Spark that organizes data into a 2-dimensional table format with rows and columns, similar to a spreadsheet or a SQL table.

### Key Features:
1. **Structure**:
    - Organized in columns (like a database table).
    - Each column has a name and a specific data type.
    - Data is stored in rows.

2. **Distributed**:
    - Data is partitioned across multiple nodes in a cluster.
    - Operations can be performed in parallel.

3. **Immutable**:
    - Once created, a DataFrame cannot be modified.
    - Transformations create new DataFrames.

## 2. DataFrame Partitioning Across Cluster Nodes
### 2.1 Basic Concept:
- A **DataFrame** is a **distributed data structure**.
- Data is split into multiple **partitions** where:
    - Each partition serves as a logical chunk of data.
    - Partitions are distributed across worker nodes.

### 2.2 Architecture Overview:
DataFrame
├── Partition 1 → Node 1  
├── Partition 2 → Node 2  
├── Partition 3 → Node 1  
└── Partition 4 → Node 3

### 2.3 How It Works:
- **Reading data** creates partitions **automatically**, but this can be configured.

### Key Points on Partition Behavior:
1. Data is automatically divided into partitions.
2. Each partition is processed independently.
3. **One partition cannot span multiple nodes.**
4. **One node can contain multiple partitions.**
5. Partitions are **redistributed automatically** if a node fails.

- Transformations in Spark are **lazy-evaluated**, meaning:
    - Transformations are **not executed immediately**.
    - Execution is triggered only when an **action** (e.g., `show`, `count`, etc.) is called.

## 3. What is Schema?
A **schema** defines the structure of a DataFrame by specifying:
- **Column names**.
- **Data types**.
- **Nullable properties** for each column.

### Key Characteristics:
- Acts as a **blueprint** for how the data in a DataFrame is structured.
- Schemas can either be:
    1. **Auto-Inferred**: Automatically deduced from the data at runtime.
    2. **Explicitly Defined**: Manually set by the user for stricter data validation.

## 4. What is a Row?
A **Row** in Spark represents a **single record** in a DataFrame.

### Key Characteristics:
1. **DataFrame Building Block**:
    - A DataFrame is essentially a collection of Row objects.
    - Rows are organized based on named columns defined in the schema.

2. **Schema Conformance**:
    - Every Row **must conform** to the DataFrame’s schema:
        - Same number of columns.
        - Consistent and expected data types for all values across columns.

3. **Flexibility**:
    - Rows are flexible to hold various types of values **as long as they follow schema rules.**

## 5. What is Shuffling?
**Shuffling** is the process of redistributing or reorganizing data across partitions in a distributed dataset.

### Characteristics:
- Redistribution/regrouping of data happens during wide operations like:
    - **groupBy**.
    - **join**.
    - **distinct**.

- **Shuffling introduces performance overhead**:
    - Requires **extensive network communication**.
    - Can lead to **significant disk I/O**.

### Transformations Based on Shuffling:
1. **Narrow Transformations**:
    - No shuffling required.
    - Examples: `map`, `filter`.

2. **Wide Transformations**:
    - Requires shuffling by redistributing data across partitions.
    - Examples: `groupByKey`, `reduceByKey`.

## 6. Spark Type System
### 6.1 Compile-time Types (Scala):
- Known during **compilation**.
- Examples: `Int`, `String`, `case class`.

### 6.2 Runtime Types (Spark SQL):
- Known during **execution**.
- Examples: `IntegerType`, `StringType`, `StructType`.

| Compile-time (Scala)                  | Runtime (Spark SQL)            |
|---------------------------------------|--------------------------------|
| Checked by Scala compiler             | Evaluated by Spark             |
| Static type checking                  | Dynamic type checking          |
| Errors caught during compilation      | Errors found during execution  |

### 6.3 Spark Data Types:
1. **Basic Types**:
    - `StringType`, `IntegerType`, `LongType`, `DoubleType`, `BooleanType`, `TimestampType`.

2. **Complex Types**:
    - **StructType**: Used to define table schemas.
    - **ArrayType**: Used when a column contains a list of values.
    - **MapType**: Used when a column contains key-value attributes.
## 7. Spark Query Planning and Optimization
Spark uses a multi-stage process to optimize and execute queries in the most efficient way possible.

### Query Planning Stages:
1. **Initial Query → Logical Plan → Physical Plan → Execution**
2. **Logical Plan**:
    - Represents **WHAT** operations need to be performed.
    - Does not define HOW the operations should be executed.
3. **Physical Plan**:
    - Represents **HOW** the logical plan should be executed on the cluster.
    - **Catalyst Optimizer** generates several possible execution strategies:
        - Each strategy represents a different way to execute the same logical plan.
        - Evaluates different physical plans.
        - Selects the most efficient plan based on **cost metrics**.

### What is the Catalyst Optimizer?
Catalyst Optimizer is a **query optimization framework** in Apache Spark. It:
- Transforms queries written using **DataFrame/SQL APIs** into **optimized execution plans**.
- Ensures efficient query execution by analyzing and applying optimizations at each stage of the query plan.

---

## 8. What is a Spark DAG?
A **DAG (Directed Acyclic Graph)** in Spark represents the **logical arrangement of operations** that Spark will perform on the data.

### Key Characteristics:
- **Vertices**: Represent operations (e.g., transformations, actions).
- **Edges**: Represent the data flow between operations.
- **Acyclic**: The graph does not contain any cycles, ensuring no operation loops back.

---

## 9. Transformations vs Actions

| **Transformations (Lazy)**         | **Actions (Eager)**               |
|-------------------------------------|------------------------------------|
| Create new RDD/DataFrame            | Return results                    |
| No immediate execution              | Trigger execution                 |
| Build lineage                       | Compute final result              |

### Comparison:
- **Transformations** (Build the Plan):
    - Examples: `map()`, `filter()`, `groupBy()`
- **Actions** (Trigger Execution):
    - Examples: `collect()`, `count()`, `save()`

---

## 10. What is Lineage?
**Lineage** is a record of all transformations that Spark maintains to track how to reconstruct data from the original source.

### Characteristics:
- Lineage is like a **recipe** describing how the final result was derived from the input data.
- Helps in **fault-tolerance**: If a partition is lost, Spark can recompute it using the lineage graph.

---

## 11. What is RDD?
An **RDD (Resilient Distributed Dataset)** is the fundamental data structure and main programming abstraction in Apache Spark.

### Key Characteristics:
1. **Resilient**:
    - RDDs are fault-tolerant, meaning they can recover from failures using their **lineage graph**.
    - If a partition is lost or damaged, Spark can recompute it using the lineage information.
2. **Distributed**:
    - Data is partitioned and distributed across multiple nodes in a cluster for parallel processing.
3. **Dataset**:
    - Represents a collection of records or partitioned data elements.

---

## 12. What is Dataset?
A **Dataset** is a strongly-typed, structured collection of data that combines the benefits of RDDs and DataFrames.

### Key Characteristics:
- **Schema-Aware**:
    - Datasets have a strongly defined structure.
- **Type Safety**:
    - Provides compile-time type checking, ensuring program safety.
- **Performance**:
    - Supports automatic optimizations, leveraging Spark's Catalyst optimizer.
- **Structured Data Handling**:
    - Best suited for managing **structured data** with **type safety** and modern functional programming interfaces.
## 13. Difference Between DataFrame, Dataset, and RDD
Comparison of key features across RDD, DataFrame, and Dataset:

| **Feature**       | **RDD**         | **DataFrame**    | **Dataset**               |
|--------------------|-----------------|------------------|---------------------------|
| **Schema**         | No              | Yes              | Yes                       |
| **Optimization**   | Manual          | Automatic        | Automatic                 |
| **Type Safety**    | Yes             | No               | Yes                       |
| **Ease of Use**    | Complex         | Simple           | Moderate                  |
| **Performance**    | Lower           | Highest          | High                      |
| **Best For**       | Unstructured    | Structured       | Structured with           |
|                    | Data            | Data             | Type Safety               |

---

## 14. Various Read Modes
The read mode determines how Spark handles corrupt records or parsing errors when reading data.

1. **PERMISSIVE Mode (Default)**:
    - Sets corrupt records to `null`.
    - Optional: Capture bad records using `.option("columnNameOfCorruptRecord", "_corrupt_record")`.

2. **FAILFAST Mode**:
    - Throws an exception on any corrupt records.

3. **DROPMALFORMED Mode**:
    - Drops corrupt records from the dataset.

---

## 15. Various Write Modes
Output data can be persisted using the following write modes:

1. **Overwrite Mode**:
    - Completely replaces existing data.

2. **Append Mode**:
    - Adds new data to the existing dataset.

3. **Ignore Mode**:
    - Does nothing if the existing data already exists.

4. **ErrorIfExists Mode**:
    - Throws an exception if data already exists.

---

## 16. Columns and Expressions
Columns and expressions enable powerful operations on DataFrames.

### 16.1 Columns:
Columns are special objects that reference data within DataFrames. They can be used to create new DataFrames by processing the values inside the source data.

#### Ways to Reference Columns:
1. **Using col() function**:
   ```scala
   val salaryCol = col("salary")
   ```

2. **Using DataFrame.col**:
   ```scala
   val nameCol = employeeDF.col("name")
   ```

3. **Using `$` Symbol**:
   ```scala
   val deptCol = $"department"
   ```

4. **Using column string reference**:
   ```scala
   val idCol = employeeDF("id")
   ```

5. **Using column() function**:
   ```scala
   val salaryCol = column("salary")
   ```

6. **Using Scala Symbol**:
   ```scala
   val deptCol = 'department
   ```

7. **Using plain column names**:
   ```scala
   val salaryAndDept = employeeDF("name", "salary")
   ```

---

### 16.2 Expressions:
Expressions are powerful constructs for manipulating data. They allow you to process DataFrames in complex ways. Columns are a subtype of `Expression`.

#### Example 1: Basic Expression
```scala
val simpleExpression: Column = carsDF.col("Weight_in_lbs")
val weightInKilogram = simpleExpression / 2.2
carsDF.select(
  col("Name"),
  simpleExpression,
  weightInKilogram.as("Weight_in_kg")
)
```

#### Example 2: Using the `expr` function
Instead of chaining operations, you can use SQL-style strings with the `expr` method:
```scala
carsDF.select(
    col("Name"),
    expr("Weight_in_lbs / 2.2").as("Weight_in_kg")
)
```
- Note: Division operator on Columns and inside `expr` have slightly different implementations and may give slightly different results.

#### Example 3: Select with `expr()` only (`selectExpr`)
```scala
carsDF.selectExpr(
  "Name",
  "Weight_in_lbs / 2.2 as Weight_in_kg"
)
```

---

### 16.3 Common Column/Expression Operations:
1. **Adding a New Column**:
    - Extend a DataFrame with an additional column using `withColumn()`:
   ```scala
   carsDF.withColumn(
     "Weight_in_kg", col("Weight_in_lbs") / 2.2
   )
   ```

2. **Renaming a Column**:
    - Rename a column with `withColumnRenamed()`:
   ```scala
   carsDF.withColumnRenamed("Weight_in_lbs", "Weight in pounds")
   ```
    - Be cautious about column names. Use backticks (`) to escape reserved characters like spaces or special symbols:
   ```scala
   carsDF.selectExpr("Weight_in_lbs / 2.2 as `Weight in Kg`")
   ```

3. **Removing a Column**:
    - Remove one or more columns from a DataFrame using `drop()`:
   ```scala
   carsDF.drop("Weight_in_lbs", "Name")
   ```
## 17. Aggregation
Aggregation refers to summarizing data, such as calculating totals, averages, or counts from a large dataset.

### Common Aggregation Functions:
1. **count()**: Counts rows.
2. **sum()**: Adds up values.
3. **avg()**: Calculates the average.
4. **max()**: Finds the maximum value.
5. **min()**: Finds the minimum value.
6. **first()**: Gets the first value.
7. **last()**: Gets the last value.

### Using `agg()`:
The `agg()` function is used for performing multiple aggregations simultaneously, especially after grouping data. It simplifies calculating different metrics at the same time.

---

## 18. Joins
Joins combine two DataFrames (or tables) based on a common column (like combining Excel sheets using a common ID). Rows are combined for matching keys, and non-matching rows are handled depending on the type of join.

### Types of Joins:
1. **Inner Join**: Default join; combines only matching rows.
2. **Left Outer Join**: Includes all rows from the left table and fills unmatched rows from the right table with `null`.
3. **Right Outer Join**: Includes all rows from the right table and fills unmatched rows from the left table with `null`.
4. **Full Outer Join**: Includes all rows from both tables, filling unmatched rows with `null` in respective columns.
5. **Semi-Join**: Includes rows from the left DataFrame that have a match in the right DataFrame.
6. **Anti-Join**: Includes rows from the left DataFrame that do not have a match in the right DataFrame.

---

## 19. Various Errors While Working with PlayGround

### 1. After `groupBy`, only grouped and aggregated columns are accessible:
If a column is not part of the `groupBy` or aggregation, trying to access it (e.g., `departmentsDF("name")`) will result in an error.

### 2. Using `.select()` vs. `.withColumnRenamed()`:
- **`.select()`**: Optimized for renaming only the required columns.
- **`.withColumnRenamed()`**: Creates a new DataFrame with the renamed column, which can be less efficient if used for multiple columns repeatedly.

### 3. Avoiding `take(1)` for Single Results:
- **Issue**:
    - `.take(1)` fetches the first row as a Scala array, pulling the data to the driver unnecessarily and is not memory-efficient.
- **Solution**:
    - Use `.limit(1)` instead, which operates distributedly and returns a DataFrame with the first row.

### 4. Specify `orderBy` Direction:
- Default `.orderBy()` sorts in ascending order.
- To sort descending:
  ```scala
  .orderBy(col("emp_count").desc)
  ```

### 5. Efficient `.agg()` Syntax:
- Avoid `count("*")` as it evaluates all columns.
- Instead, specify the column explicitly for clarity and performance:
  ```scala
  count("emp_id")
  ```

### 6. Explicitly Defining Column References:
When using `.groupBy` and `.agg`:
- String-based columns (e.g., `.groupBy("dept_name", "loc_name")`) often work better than column references (e.g., `col(...)`).

---

## 20. When to Use `lit()`
The `lit()` function is important when inserting literal (constant) values into expressions where Spark expects a column or expression.

### Situations Requiring `lit()`:
1. **Avoiding Misinterpretation**:
    - Spark could misinterpret a literal value as a column name, causing an error:
   ```scala
   df.withColumn("new_col", col("existing_col") + "some_string")
   // Error: cannot resolve '`some_string`'
   ```

2. **Using Variables in Expressions**:
    - Wrap non-column variables explicitly with `lit()` for clarity and correctness:
   ```scala
   df.withColumn("new_col", col("existing_col") + lit(5))
   ```

3. **When Spark Functions Expect Columns**:
    - Certain Spark functions explicitly require column objects, where literals need to be wrapped with `lit()`.

---

### Best Practices for Using `lit()`:

1. **Use `lit()` When**:
    - A literal might be confused with a column name.
    - Combining literals with column expressions.
    - Working with non-obvious literal types (e.g., strings, floats).
    - Making transformation logic explicit in expressions.

2. **Omit `lit()` When**:
    - Passing obvious literals in filters and basic expressions:
      ```scala
      df.filter(col("age") > 30)
      ```
    - Using basic operations where Spark can handle literals automatically:
      ```scala
      df.filter(col("name") === "John")
      ```
      
## 20. Commonly Used Spark Operators

### Arithmetic Operators
| **Operator** | **Example**            | **Description**                           |
|--------------|------------------------|-------------------------------------------|
| `+`          | `df("col1") + 10`      | Adds a literal or another column value.   |
| `-`          | `df("col1") - df("col2")`  | Subtracts one column’s value from another.|
| `*`          | `df("col1") * 2`       | Multiplies column values.                 |
| `/`          | `df("col1") / df("col2")`  | Divides the value of one column by another.|
| `%`          | `df("col1") % 2`       | Returns the remainder of a division.      |

### Logical Operators
| **Operator** | **Example**                                      | **Description**                                                  |
|--------------|---------------------------------------------------|------------------------------------------------------------------|
| `and`        | `df("col1") > 10 and df("col2") < 20`            | Logical AND: True if both conditions are true.                  |
| `or`         | `df("col1") > 10 or df("col2") < 20`             | Logical OR: True if at least one condition is true.             |
| `not`        | `not(df("col1") > 10)`                           | Logical NOT: Negates the condition.                             |

### Relational Operators
| **Operator** | **Example**                     | **Description**                                                |
|--------------|----------------------------------|----------------------------------------------------------------|
| `===`        | `df("col1") === df("col2")`     | Equal to: Compares two columns for equality.                  |
| `!==`        | `df("col1") !== df("col2")`     | Not Equal: Compares two columns for inequality.               |
| `<`          | `df("col1") < df("col2")`       | Less Than: Checks if one column’s value is less than another. |
| `>`          | `df("col1") > df("col2")`       | Greater Than: Checks if one column’s value is greater.        |
| `<=`         | `df("col1") <= df("col2")`      | Less Than or Equal To: True if one column is <= another.      |
| `>=`         | `df("col1") >= df("col2")`      | Greater Than or Equal To: True if one column is >= another.   |

### Null Checking Operators
| **Operator**  | **Example**                              | **Description**                                                     |
|---------------|------------------------------------------|---------------------------------------------------------------------|
| `isNull`      | `df("col").isNull`                       | Checks if a column value is null.                                   |
| `isNotNull`   | `df("col").isNotNull`                    | Checks if a column value is not null.                               |
| `drop("all")` | `df.na.drop("all")`                      | Drops rows where all selected columns are null. |
| `drop("any")` | `df.na.drop("any", Seq("col1", "col2"))` | Drops rows if any of the specified columns contain null. |

## 21. **What are `when` and `otherwise`?**
- **`when`**: Acts like an **"if" condition**. It checks whether a certain condition is `true` for each row in a column.
- **`otherwise`**: Acts like an **"else" condition**. It provides a fallback value if the condition in `when` is `false`.

Together, they allow you to create **new values for a column** or **modify existing values** in a DataFrame, based on 
one or more conditions.
```scala
import org.apache.spark.sql.functions._
when(condition, value).otherwise(value)
```
- **`condition`**: The condition you want to check. It can involve column values, numeric comparisons, string matches, etc.
- **`value`**: This is the value you want to return if the condition is met (inside `when`) or not met (inside `otherwise`).
- If you only use **`when`** without **`otherwise`**, rows that don't meet the condition will receive `null`.
- The **`when`** statements are evaluated in order. The **first condition that matches** will take effect, and no further conditions will be checked after it's met.
- The **`otherwise`** statement acts as the default or fallback value when none of the preceding `when` conditions are true.

## 22. **What is `explode`?**
The **`explode`** function in Spark is used to take a column that contains **multiple values** (like a **list/array** or a **map**), and 
convert it into **separate rows**, one for each value in the list/array/map.
Think of it as:
- If you have one row where a column has multiple values, `explode` creates **many rows**—one for each value in that column.
### **Why is this useful?**
Imagine you have a table like this:

| **id** | **name** | **scores** |
| --- | --- | --- |
| 1 | Alice | [10, 20, 30] |
| 2 | Bob | [40, 50] |
| 3 | Cathy | [] (empty array) |
Now, you want **one row for each score**. Exploding the "scores" column will turn this into:

| **id** | **name** | **score** |
| --- | --- | --- |
| 1 | Alice | 10 |
| 1 | Alice | 20 |
| 1 | Alice | 30 |
| 2 | Bob | 40 |
| 2 | Bob | 50 |
This is what the **`explode`** function does: **flattens arrays (or maps) into separate rows.**









