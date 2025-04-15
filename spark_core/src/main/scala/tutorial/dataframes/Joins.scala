package tutorial.dataframes

import org.apache.spark.sql.{DataFrame, SparkSession}

object Joins extends App {
  private val spark = SparkSession
    .builder()
    .appName("Columns And Expressions")
    .config("spark.master", "local")
    .getOrCreate()

  val guitarsDF: DataFrame = spark.read
    .option("inferSchema", "true")
    .json("data/core/guitars.json")

  val guitaristsDF: DataFrame = spark.read
    .option("inferSchema", "true")
    .json("data/core/guitarPlayers.json")

  val bandsDF: DataFrame = spark.read
    .option("inferSchema", "true")
    .json("data/core/bands.json")

  val joinConditions = guitaristsDF.col("band") === bandsDF.col("id")

  // inner join is default join
  guitaristsDF.join(bandsDF, joinConditions, "inner").show()

  // left_outer = everything from inner join + all the rows in the left table with nulls for missing matches
  guitaristsDF.join(bandsDF, joinConditions, "left_outer").show()

  // right_outer = everything from inner join + all the rows in the right table with nulls for missing matches
  guitaristsDF.join(bandsDF, joinConditions, "right_outer").show()

  // outer = everything from inner join + all the rows in the right table with nulls for missing matches + all the rows in the left table with nulls for missing matches
  guitaristsDF.join(bandsDF, joinConditions, "outer").show()

  // semi-join = everything in the left DF for which there was a match in the right DF
  guitaristsDF.join(bandsDF, joinConditions, "left_semi").show()

  // anti-join = everything in the left DF for which there was NO match in the right DF
  guitaristsDF.join(bandsDF, joinConditions, "left_anti").show()

}
