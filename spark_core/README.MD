1. What is DataFrame?
   A DataFrame is a fundamental data structure in Spark that organizes data into a 2-dimensional table format with rows 
   and columns, similar to a spreadsheet or a SQL table.
   Following is the key features of dataframe:
   1. Structure :
      1. Organized in columns (like a database table)
      2. Each column has a name and a specific data type
      3. Data is stored in rows
   2. Distributed :
      1. Data is partitioned across multiple nodes in a cluster
      2. Operations can be performed in parallel
   3. Immutable :
      1. Once created, cannot be modified
      2. Transformations create new DataFrames
   
2. DataFrame Partitioning Across Cluster Nodes:
   1. Basic Concept
      - DataFrame = Distributed data structure
      - Data split into multiple partitions
      - Each partition = Logical chunk of data
      - Partitions distributed across worker nodes
   2. Architecture Overview
      - DataFrame
        ├── Partition 1 → Node 1
        ├── Partition 2 → Node 2
        ├── Partition 3 → Node 1
        └── Partition 4 → Node 3
   3. How It Works
      - Reading data creates partitions automatically but configurable
      - Data automatically split into partitions
      - Each partition processed independently
      - One partition cannot span multiple nodes
      - One node can have multiple partitions
      - Automatically redistributed if node fails
      - lazy evaluated, meaning transformations are not executed until an action is called.

3. What is Schema?
   A schema defines the structure of a DataFrame by specifying the names, data types, and nullable properties of
   its columns. It can be auto-inferred the schema at compile time. It acts as a blueprint that describes how the data 
   is organized in dataframe.

4. What is Row?
   A Row in Spark represents a single record in a DataFrame. Each DataFrame is essentially a collection of Row objects 
   organized in a structured format with named columns. Basically, a row can contain anything, but it has to conform to
   the schema that a data frame has.
   Every row in a DataFrame must have the same structure - meaning the same number of columns with consistent data types
   as defined in the schema. This is one of the fundamental characteristics of DataFrames.

5. What is Shuffling?
   Shuffling is the process of redistributing or reorganizing data across the partitions of a distributed dataset.
     - Redistribution of data across partitions
     - Happens when data needs to be regrouped
     - Common in operations like groupBy, join, distinct
     - Narrow Transformations: No shuffle required
     - Wide Transformations: Requires shuffle
   
6. Spark Type System:
     1. Compile-time Types (Scala)
        - Known during compilation
        - Example: Int, String, case classes
     2. Runtime Types (Spark SQL)
        - Known during execution
        - Example: IntegerType, StringType, StructType
     Compile-time (Scala)                  | Runtime (Spark)
     --------------------------------------|------------------
     Checked by Scala compiler             | Evaluated by Spark
     Static type checking                  | Dynamic type checking
     Errors caught during compilation      | Errors found during execution

     3. Basic Types: StringType, IntegerType, LongType, DoubleType, BooleanType, TimestampType
     4. Complex Types:
        - StructType: Defining table schemas
        - ArrayType: List of values, use if your column contains multiple values
        - MapType: Key-value attributes, use if your column contains key-value
        
7. Spark Query Planning and Optimization:
   Initial Query → Logical Plan → Physical Plan → Execution
   1. A logical plan represents WHAT operations need to be performed, without specifying HOW they should be 
      executed.
   2. A physical plan represents HOW the logical plan should be executed on the cluster.
      - Catalyst optimizer generates several possible execution strategies
      - Each strategy represents a different way to execute the same logical plan
      - Evaluates different physical plans
      - Selects the most efficient plan based on cost metrics
   3. Catalyst Optimizer is a query optimization framework in Apache Spark that transforms and optimizes queries written
      using DataFrame/SQL APIs into efficient execution plans.

8. What is a Spark DAG?
   A DAG in Spark represents the logical arrangement of operations that will be performed on the data. It's a directed 
   graph where:
   - Vertices represent operations (transformations/actions)
   - Edges represent data flow between operations
   - The graph is acyclic (no cycles)

9. Transformations vs Actions:
   Transformations (Lazy)    │ Actions (Eager)
   -------------------------─┼------------------------
   Create new RDD/DataFrame  │ Return results
   No immediate execution    │ Trigger execution
   Build lineage             │ Compute final result

   Transformations (Build Plan)    Actions (Execute)
   ┌─────────────────────┐        ┌──────────────┐
   │ map()               │        │ collect()    │
   │ filter()            │ ─────► │ count()      │
   │ groupBy()           │        │ save()       │
   └─────────────────────┘        └──────────────┘

10. What is Lineage?
    Lineage is a record of all transformations that need to be applied to the base data to get the final result. It's 
    like a recipe that Spark maintains to track how to reconstruct data from the original source.

11. What is RDD?
    RDD (Resilient Distributed Dataset) is the fundamental data structure and main programming abstraction in Apache 
    Spark. It has three key characteristics:
    - Resilient: RDDs are fault-tolerant, meaning they can recover from failures through a lineage graph. If a 
       partition is lost or damaged due to node failures, Spark can recompute it using this lineage information.
    - Distributed: The data is distributed across multiple nodes in a cluster, allowing parallel processing.
    - Dataset: It's a collection of partitioned data elements.
    
12. What is DataSet?
    A Dataset is a strongly-typed, structured collection of data that provides both static type safety and modern 
    functional programming interfaces.
    - Schema-aware: Datasets have a defined structure
    - Type Safety: Provides compile-time type checking
    - Performance: Offers high performance with automatic optimization
    - Structured Data Handling: Best suited for structured data with type safety requirements
    
13. Difference between DataFrame, DataSet and RDD?
    Feature          | RDD           | DataFrame      | Dataset
    -----------------|---------------|----------------|------------------
    Schema           | No            | Yes            | Yes
    Optimization     | Manual        | Automatic      | Automatic
    Type Safety      | Yes           | No             | Yes
    Ease of Use      | Complex       | Simple         | Moderate
    Performance      | Lower         | Highest        | High
    Best For         | Unstructured  | Structured     | Structured with
                     | Data          | Data           | Type Safety
    
14. Various Read Modes:
    The mode option determines how Spark handles corrupt records or parsing errors.
    1. PERMISSIVE Mode (Default)
       - sets corrupt records to null
       - .option("columnNameOfCorruptRecord", "_corrupt_record")  // Optional: capture bad records
    2. FAILFAST Mode
       - Throws exception on corrupt records
    3. DROPMALFORMED Mode
       - Drops corrupt records

15. Various Write Modes:
    1. Overwrite Mode: Completely replaces existing data
    2. Append Mode: Adds new data to existing data
    3. Ignore Mode: Does nothing if data exists
    4. ErrorIfExists Mode: Throws exception if data exists

16. Columns and Expressions:
    - Projection: Operate on data frame columns and obtain new data frames.
    1. Columns: Columns are special objects that will allow you to obtain new data frames out of some source data
       frames by processing the values inside. The col() method takes an argument, which is the column name that we want
       to obtain. Column is a plain Jvm object that has No data inside.
       - Different Ways to Reference Columns:
         1. Using col() function: val salaryCol = col("salary")
         2. Using DataFrame.col: val nameCol = employeeDF.col("name")
         3. Using $ symbol: val deptCol = $"department"
         4. Using column string reference: val idCol = employeeDF("id")
         5. Using column() function: val salaryCol = column("salary")
         6. Using Scala Symbol = val deptCol = 'department
         7. Using plain column names: val salaryAndDept = employeeDF("name", "salary")
    2. Expressions: Expressions are a powerful construct and will allow you to process data frames in almost any fashion
       Selecting columns name is the simplest version of expression, Columns is a subtype of Expression, But after you 
       obtain a simple expression, you can then chain it with various operators.
       ```scala
          val simpleExpression: Column = carsDF.col("Weight_in_lbs")
          val weightInKilogram = simpleExpression / 2.2
          carsDF.select(
            col("Name"),
            simpleExpression,
            weightInKilogram.as("Weight_in_kg")
          )
       ```
       Alternatively, instead of creating these complex expressions because in practice you will create very, very 
       complex expressions, you can use the expr method, and you can pass in a SQL like string.
       ```scala
          carsDF.select(
              col("Name"),
              expr("Weight_in_lbs / 2.2").as("Weight_in_kg")
          )
       ```
       Division operator on Column and inside expr and identical implementation but not same, so the result can slightly
       differ.
    3. Select with only expr() call: selectExpr
       ```scala
          carsDF.selectExpr(
            "Name",
            "Weight_in_lbs / 2.2 as Weight_in_kg"
          )
       ```
    4. Adding a new Column to DF: withColumn()
       - this will extend a Data Frame with additional column.
       ```scala 
           carsDF.withColumn(
             "Weight_in_kg", col("Weight_in_lbs") / 2.2
           )
       ```
    5. Renaming a Column: withColumnRenamed()
       ```scala
          carsDF.withColumnRenamed("Weight_in_lbs", "Weight in pounds")
       ```
       - CareFull with column names: When you're doing expressions, be sure to escape reserved characters like spaces or
         hyphens inside Backticks because the compiler will then figure out that this weight in pounds is a single 
         symbol, and it will treat it as one.
       ```scala
          carsDF.selectExpr("Weight_in_lbs / 2.2 as `Weight in Kg`")
       ```
    6. Remove a Column: drop()
       ```scala
          carsDF.drop("Weight_in_lbs", "Name")
       ```
17. Aggregation:
    - Think of aggregation as summarizing data - like when you want to calculate totals, averages, or counts from a 
      large dataset.
    - Some frequently used functions:
      1. count(): Counts rows
      2. sum(): Adds up values
      3. avg(): Calculates average
      4. max(): Finds maximum value
      5. min(): Finds minimum value
      6. first(): Gets first value
      7. last(): Gets last value
    - The agg() function is used when you need to perform multiple aggregation operations at once, especially after 
      grouping data. It's particularly useful when you want to calculate different metrics simultaneously.

18. Joins
    - Think of joins as combining two different tables/DataFrames based on a common column
    - Similar to combining information from two Excel sheets using a common ID
    - if the join condition passes rows are combined, for non-matching records rows are discarded.
    Types of Join:
      1. inner join: default join
      2. left_outer: everything from inner join + all the rows in the left table with nulls for missing matches
      3. right_outer: everything from inner join + all the rows in the right table with nulls for missing matches
      4. outer: everything from inner join + 
         all the rows in the right table with nulls for missing matches + 
         all the rows in the left table with nulls for missing matches
      5. semi-join: everything in the left DF for which there was a match in the right DF
      6. anti-join: everything in the left DF for which there was NO match in the right DF
      
19. 