-- Generate DROP statements for all tables
SELECT 'DROP TABLE IF EXISTS ' || schemaname || '.' || tablename || ' CASCADE;'
FROM pg_tables
WHERE tableowner = 'postgres' -- replace with your database owner if different
AND schemaname NOT IN ('pg_catalog', 'information_schema');

DROP TABLE IF EXISTS public.DBS CASCADE;
DROP TABLE IF EXISTS public.TBLS CASCADE;
DROP TABLE IF EXISTS public.DATABASE_PARAMS CASCADE;
DROP TABLE IF EXISTS public.PARTITIONS CASCADE;
DROP TABLE IF EXISTS public.PART_COL_STATS CASCADE;
DROP TABLE IF EXISTS public.PARTITION_PARAMS CASCADE;
DROP TABLE IF EXISTS public.CDS CASCADE;
DROP TABLE IF EXISTS public.SERDES CASCADE;                                                                                                                                       
DROP TABLE IF EXISTS public.SKEWED_STRING_LIST CASCADE;                                                                                                                           
DROP TABLE IF EXISTS public.SDS CASCADE;                                                                                                                                          
DROP TABLE IF EXISTS public.TAB_COL_STATS CASCADE;                                                                                                                                
DROP TABLE IF EXISTS public.COLUMNS_V2 CASCADE;                                                                                                                                   
DROP TABLE IF EXISTS public.TABLE_PARAMS CASCADE;                                                                                                                                 
DROP TABLE IF EXISTS public.BUCKETING_COLS CASCADE;                                                                                                                               
DROP TABLE IF EXISTS public.SERDE_PARAMS CASCADE;                                                                                                                                 
DROP TABLE IF EXISTS public.PARTITION_KEYS CASCADE;                                                                                                                               
DROP TABLE IF EXISTS public.SORT_COLS CASCADE;                                                                                                                                    
DROP TABLE IF EXISTS public.SKEWED_STRING_LIST_VALUES CASCADE;                                                                                                                    
DROP TABLE IF EXISTS public.SD_PARAMS CASCADE;                                                                                                                                    
DROP TABLE IF EXISTS public.SKEWED_COL_NAMES CASCADE;                                                                                                                             
DROP TABLE IF EXISTS public.SKEWED_COL_VALUE_LOC_MAP CASCADE;                                                                                                                     
DROP TABLE IF EXISTS public.SKEWED_VALUES CASCADE;                                                                                                                                
DROP TABLE IF EXISTS public.KEY_CONSTRAINTS CASCADE;                                                                                                                              
DROP TABLE IF EXISTS public.PARTITION_KEY_VALS CASCADE;                                                                                                                           
DROP TABLE IF EXISTS public.VERSION CASCADE;    

:: Terminate existing connections first
"%PSQL%" -U %DB_USER% -d postgres -c "SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE datname='%DB_NAME%' AND pid <> pg_backend_pid();"
:: Drop the database
"%PSQL%" -U %DB_USER% -d postgres -c "DROP DATABASE %DB_NAME%;"

// PostgreSQL connection settings
//        .config("javax.jdo.option.ConnectionURL", "jdbc:postgresql://localhost:5432/hive_store")
//        .config("javax.jdo.option.ConnectionDriverName", "org.postgresql.Driver")
//        .config("javax.jdo.option.ConnectionUserName", "postgres")
// Hive specific configurations
//        .config("hive.metastore.schema.verification", "false")
//        .config("hive.metastore.schema.verification.record.version", "false")
//        .config("hive.metastore.client.connect.retry.delay", "5")
//        .config("hive.metastore.client.socket.timeout", "1800")
//        .config("hive.metastore.uris", "")  // Empty for embedded metastore
//        .config("datanucleus.autoCreateSchema", "true")
//        .config("datanucleus.fixedDatastore", "false")
//        .config("datanucleus.autoCreateTables", "true")

git remote add origin https://github.com/anish-diliyan/spark-hive-ex
git branch -M main
git push -u origin main

insertInto() can't be used together with partitionBy(). Partition columns have already been defined for the table. It is not necessary to use partitionBy().
df.write.mode("overwrite").partitionBy("currency").insertInto(table)

slf4j-api: This is the SLF4J (Simple Logging Facade for Java) API. It serves as an abstraction or facade for various logging frameworks. It allows you to write logging code without tying it to a specific logging implementation. Your application code uses this API to write log statements. [2]

log4j-api: This is the API component of Log4j 2. It contains the interfaces and classes that your application can use to log messages specifically with Log4j 2.

log4j-core: This is the actual implementation of Log4j 2 - the core logging functionality. It contains all the code that makes logging work, including features like appenders, layouts, filters, etc. This is required for Log4j 2 to actually perform the logging operations.

log4j-slf4j-impl: This is the binding that connects SLF4J to Log4j 2. It allows SLF4J API calls to be routed to Log4j 2. Without this binding, SLF4J wouldn't know how to direct logging calls to Log4j 2.

The combination works like this:

Your application code uses SLF4J API to write logs

The SLF4J API calls are routed through log4j-slf4j-impl

These calls then use log4j-api and log4j-core to actually perform the logging

The "provided" scope means that these dependencies will not be included during local development/running because it assumes these dependencies will be provided by the runtime environment (like a Spark cluster). 

Spark Optimization Techniques

Partition tuning and management

Caching and persistence strategies

Memory management

Broadcast variables and accumulators

Spark SQL and DataFrames Advanced Concepts

Window functions

User-Defined Functions (UDFs)

Custom aggregations

Complex data types and nested schemas

Spark Streaming

Structured Streaming

Stream processing with windowing

Watermarking

Integration with Kafka

Spark MLlib

Machine learning pipelines

Feature engineering

Model training and evaluation

Hyperparameter tuning

Advanced Data Processing

Join optimizations

Handling skewed data

Custom partitioning strategies

Delta Lake integration

Spark Performance Monitoring

Spark UI interpretation

Metrics and instrumentation

Debugging and troubleshooting

Resource management

// 1. map - Transforms each element of the RDD using the provided function
val numbers = sc.parallelize(1 to 5)
val doubled = numbers.map(x => x * 2)
// Result: 2, 4, 6, 8, 10

// 2. filter - Returns a new RDD containing only elements that satisfy the condition
val evenNumbers = numbers.filter(x => x % 2 == 0)
// Result: 2, 4

// 3. flatMap - Similar to map, but each input item can be mapped to 0 or more output items
val sentences = sc.parallelize(Array("Hello World", "How are you"))
val words = sentences.flatMap(line => line.split(" "))
// Result: "Hello", "World", "How", "are", "you"

// 4. distinct - Returns a new RDD containing distinct elements
val duplicateNumbers = sc.parallelize(List(1, 1, 2, 2, 3))
val uniqueNumbers = duplicateNumbers.distinct()
// Result: 1, 2, 3

// 5. union - Combines two RDDs
val rdd1 = sc.parallelize(1 to 3)
val rdd2 = sc.parallelize(4 to 6)
val combined = rdd1.union(rdd2)
// Result: 1, 2, 3, 4, 5, 6

// 6. intersection - Returns common elements between two RDDs
val rddA = sc.parallelize(1 to 5)
val rddB = sc.parallelize(4 to 8)
val common = rddA.intersection(rddB)
// Result: 4, 5

// 7. groupByKey - Groups values for each key
val pairs = sc.parallelize(Array(("A", 1), ("B", 2), ("A", 3)))
val grouped = pairs.groupByKey()
// Result: (A, [1, 3]), (B, [2])

// 8. reduceByKey - Combines values for each key using the provided function
val sumByKey = pairs.reduceByKey((a, b) => a + b)
// Result: (A, 4), (B, 2)

// 9. sortBy - Sorts RDD by given function
val unsorted = sc.parallelize(Array(3, 1, 2, 4))
val sorted = unsorted.sortBy(x => x)
// Result: 1, 2, 3, 4

// 10. coalesce and repartition
// coalesce - Reduces the number of partitions
val lessPartitions = rdd1.coalesce(2) // Only reduces partitions

// repartition - Restructures the data into n partitions
val repart = rdd1.repartition(4) // Can increase or decrease partitions

// 11. mapPartitions - Transforms each partition as a whole
val result = rdd1.mapPartitions(iterator => {
// Process entire partition
iterator.map(_ * 2)
})

// 12. zip - Pairs elements of two RDDs
val rdd3 = sc.parallelize(List("a", "b", "c"))
val rdd4 = sc.parallelize(List(1, 2, 3))
val zipped = rdd3.zip(rdd4)
// Result: (a,1), (b,2), (c,3)


- **Format the whole project:** Run the following in a terminal within the project root:
  sbt scalafmtAll
- - **Format only `main` source files:** Run:
    sbt scalafmt
- - **Format only `test` source files:** Run:
    sbt test:scalafmt

The `MatchError: scala.collection.immutable.Map[_ <: String, Int`
arises when Spark tries to serialize a column with a Scala map type (`Map[String, Int]`). Spark uses reflection to infer
encoders for this data, and complex types like maps can cause issues when they are not explicitly handled.

