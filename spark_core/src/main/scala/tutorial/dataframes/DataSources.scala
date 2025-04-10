package tutorial.dataframes

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{DoubleType, IntegerType, StringType, StructField, StructType}

object DataSources extends App {
  private val spark = SparkSession.builder()
    .appName("DataSources")
    .config("spark.master", "local")
    .getOrCreate()

  val carsSchema = StructType(Array(
    StructField("Name", StringType),
    StructField("Miles_per_Gallon", IntegerType),
    StructField("Cylinders", IntegerType),
    StructField("Displacement", IntegerType),
    StructField("Horsepower", IntegerType),
    StructField("Weight_in_lbs", IntegerType),
    StructField("Acceleration", DoubleType),
    StructField("Year", StringType),
    StructField("Origin", StringType)
  ))

  /**
   * Reading a DF:
   * - format
   * - schema or inferSchema = true
   * - zero or more options
   * - path (some path or no path to read all files)
   * */
  val carsDF = spark.read
    .format("json")
    .schema(carsSchema)  // enforce a schema
    .option("mode", "DROPMALFORMED")
    .option("path", "data/core/cars.json")
    .load()

  /**
   * Writing DFs
   * - format
   * - save mode = overwrite, append, ignore, errorIfExists
   * - path
   * - zero or more options
   * */
  carsDF.write
    .format("json")
    .mode("overwrite")
    .option("path", "spark_core/src/main/resources/data/cars_dupe.json")
    .save()

}
