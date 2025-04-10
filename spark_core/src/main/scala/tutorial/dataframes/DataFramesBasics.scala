package tutorial.dataframes

import org.apache.spark.sql.{DataFrame, SparkSession}

object DataFramesBasics extends App {
  // Creating a SparkSession
  private val spark = SparkSession.builder()
    .appName("DataFrames Basics")
    .config("spark.master", "local")
    .getOrCreate()

  // reading a DF
  private val firstDF: DataFrame = spark.read
    .format("json")
    .option("inferSchema", "true")
    .load("data/core/cars.json")

  // showing a dataframe
  firstDF.show()

  // description of columns
  firstDF.printSchema()

  // get rows
  firstDF.take(10).foreach(println)
}
