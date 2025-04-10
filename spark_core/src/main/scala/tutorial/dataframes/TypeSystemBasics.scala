package tutorial.dataframes

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types._

object TypeSystemBasics extends App {
  private val spark = SparkSession.builder()
    .appName("DataFrames Basics")
    .config("spark.master", "local")
    .getOrCreate()

  // StructType Example
  val personSchema = StructType(Array(
    StructField("name", StringType, nullable = false),
    StructField("age", IntegerType, nullable = true),
    StructField("city", StringType, nullable = true)
  ))
  val data: Seq[Row] = Seq(
    Row("John", 30, "New York"),
    Row("Alice", 25, "London")
  )
  val rdd = spark.sparkContext.parallelize(data)
  spark.createDataFrame(rdd, personSchema)

  // Simple ArrayType Example
  val arraySchema = StructType(Array(
    StructField("name", StringType),
    StructField("scores", ArrayType(IntegerType, containsNull = true))
  ))

  // Example with data
  val arrayData = Seq(
    Row("John", Array(85, 90, 95)),
    Row("Alice", Array(88, 85, 87))
  )
  val arrayRdd = spark.sparkContext.parallelize(arrayData)
  spark.createDataFrame(arrayRdd, arraySchema)

  // MapType Example
  val mapSchema = StructType(Array(
    StructField("name", StringType),
    StructField("attributes", MapType(StringType, StringType))
  ))

  // Example with data
  val mapData = Seq(
    Row("John", Map("hair" -> "brown", "eyes" -> "blue")),
    Row("Alice", Map("hair" -> "black", "eyes" -> "brown"))
  )
  val mapRdd = spark.sparkContext.parallelize(mapData)
  spark.createDataFrame(mapRdd, mapSchema)

}
