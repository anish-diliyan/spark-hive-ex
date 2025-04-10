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

11. Various Read Modes:
    The mode option determines how Spark handles corrupt records or parsing errors.
    1. PERMISSIVE Mode (Default)
       - sets corrupt records to null
       - .option("columnNameOfCorruptRecord", "_corrupt_record")  // Optional: capture bad records
    2. FAILFAST Mode
       - Throws exception on corrupt records
    3. DROPMALFORMED Mode
       - Drops corrupt records

12. Various Write Modes:
    1. Overwrite Mode: Completely replaces existing data
    2. Append Mode: Adds new data to existing data
    3. Ignore Mode: Does nothing if data exists
    4. ErrorIfExists Mode: Throws exception if data exists
    
    