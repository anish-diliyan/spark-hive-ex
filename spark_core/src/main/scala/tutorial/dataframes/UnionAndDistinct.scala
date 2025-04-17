package tutorial.dataframes

import org.apache.spark.sql.{DataFrame, SparkSession}

object UnionAndDistinct extends App {

  private val spark = SparkSession
    .builder()
    .appName("Columns And Expressions")
    .config("spark.master", "local")
    .getOrCreate()

  val carsDF: DataFrame = spark.read
    .option("inferSchema", "true")
    .json("data/core/cars.json")

  val moreCarsDf = spark.read
    .option("inferSchema", "true")
    .json("data/core/more_cars.json")

  val allCarsDf = carsDF.union(moreCarsDf)

  val distinctCarsDf = allCarsDf.distinct()

  distinctCarsDf.show()
}
