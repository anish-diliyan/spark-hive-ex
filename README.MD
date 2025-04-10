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