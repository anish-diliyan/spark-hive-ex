package exercise

import org.apache.spark.sql.{Row, SparkSession}

object DataFrameBasics extends App {

  val spark = SparkSession
    .builder()
    .appName("DataFrameBasics")
    .master("local[*]")
    .getOrCreate()

  // create a Dataframe Manually
  val phones = Seq(
      ("Samsung", "Galaxy S20", 2020, 1000),
      ("Samsung", "Galaxy S21", 2021, 1200),
      ("Samsung", "Galaxy S22", 2022, 1500),
      ("Apple", "iPhone 13", 2021, 1300),
      ("Apple", "iPhone 14", 2022, 1800),
      ("Apple", "iPhone 15", 2023, 2000)
  )
  import spark.implicits._
  val phonesDF = phones.toDF("brand", "model", "year", "price")
  phonesDF.show()

  // read a dataframe from a file
  val moviesDF = spark.read
    .option("inferSchema", "true")
    .json("src/main/resources/data/movies.json")

  moviesDF.printSchema()
  moviesDF.count()
}
