package tutorial.dataframes

import org.apache.spark.sql.functions.{approx_count_distinct, avg, count, countDistinct}
import org.apache.spark.sql.{DataFrame, SparkSession}

object Aggregations extends App {
  private val spark = SparkSession
    .builder()
    .appName("Columns And Expressions")
    .config("spark.master", "local")
    .getOrCreate()

  val moviesDF: DataFrame = spark.read
    .option("inferSchema", "true")
    .json("data/core/movies.json")

  // counting
  val genresCountDF = moviesDF.select(count("Major_Genre")) // all the values except null
  genresCountDF.show()
  moviesDF.selectExpr("count(Major_Genre)").show() // count all the rows

  // counting all the distinct values
  moviesDF.select(countDistinct("Major_Genre")).show()

  // approximate count
  moviesDF.select(approx_count_distinct("Major_Genre")).show()

  // min and max
  moviesDF.selectExpr("min(US_Gross)").show()
  moviesDF.selectExpr("max(US_Gross)").show()

  // sum
  moviesDF.selectExpr("sum(US_Gross)").show()

  // average
  moviesDF.select(avg("US_Gross")).show()

  // Grouping
  val countByGenreDF = moviesDF
    .groupBy("Major_Genre") // include null
    .count()

  // agg() function: use when need to use multiple aggregation
  val avgRatingByGenreDF = moviesDF
    .groupBy("Major_Genre")
    .agg(
      count("*").as("N_Movies"),
      avg("IMDB_Rating").as("Imdb_Rating")
    ).orderBy("N_Movies")
}
