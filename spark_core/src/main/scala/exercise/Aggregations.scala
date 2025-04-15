package exercise

import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, SparkSession}

object Aggregations extends App {
  val moviesDF: DataFrame = spark.read
    .option("inferSchema", "true")
    .json("data/core/movies.json")
  private val spark = SparkSession
    .builder()
    .appName("Columns And Expressions")
    .config("spark.master", "local")
    .getOrCreate()

  // SumUp all the profits of all the movies
  moviesDF.select(sum("US_Gross") as "total").show()

  // count distinct directors
  moviesDF.select(countDistinct("Director") as "distinct_directors").show()

  // find the average IMDB rating and the average US gross revenue per director
  moviesDF
    .groupBy("Director")
    .agg(
      avg("IMDB_Rating") as "avg_rating",
      sum("US_Gross") as "total_gross"
    )
    .orderBy("Director")
    .show()
}
