package exercise

import org.apache.spark.sql.functions.{col, expr}
import org.apache.spark.sql.{DataFrame, SparkSession}
import tutorial.dataframes.UnionAndDistinct.spark

object DataSources extends App {
  private val spark = SparkSession
    .builder()
    .appName("Columns And Expressions")
    .config("spark.master", "local")
    .getOrCreate()

  val moviesDF: DataFrame = spark.read
    .option("inferSchema", "true")
    .json("data/core/movies.json")

  moviesDF.show()

  // Select the two column of your choice
  val moviesDFSelect: DataFrame = moviesDF.select("Title", "Director")
  moviesDFSelect.show()

  // total profit of movies US_Gross + Worldwide_Gross + US_DVD_Sales
  val moviesDFProfit = moviesDF.select(
    col("Title"),
    expr("US_Gross + Worldwide_Gross + US_DVD_Sales")
  )
  moviesDFProfit.show()
  // select good comedy movies imdb > 6
  val goodComedyMoviesDF: DataFrame = moviesDF.filter("IMDB_Rating > 6")
  goodComedyMoviesDF.show()
}
