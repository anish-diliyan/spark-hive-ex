package tutorial.dataframes

import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{DataFrame, SparkSession}

object FilterDataFrame extends App {

  private val spark = SparkSession
    .builder()
    .appName("Columns And Expressions")
    .config("spark.master", "local")
    .getOrCreate()

  val carsDF: DataFrame = spark.read
    .option("inferSchema", "true")
    .json("data/core/cars.json")

  carsDF.filter(col("Origin") =!= "USA")
  carsDF.where(col("Origin") =!= "USA")
  carsDF.where("Origin = 'USA'")

  // chain filters
  carsDF.filter(col("Origin") === "USA").filter(col("Horsepower") > 150)
  carsDF.filter("Origin = 'USA' and Horsepower > 150")
  carsDF.filter(col("Origin") === "USA" && col("Horsepower > 150"))
}
