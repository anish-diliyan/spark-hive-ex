package tutorial.mixed

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object ExplodeArray extends App {

  val spark: SparkSession = SparkSession
    .builder()
    .appName("Explode Array")
    .config("spark.master", "local")
    .getOrCreate()

  import spark.implicits._
  val data = Seq(
    (1, "Alice", Array(10, 20, 30)),  // Alice has 3 scores in an array
    (2, "Bob", Array(40, 50)),        // Bob has 2 scores in an array
    (3, "Cathy", Array())             // Cathy has an empty array
  ).toDF("id", "name", "scores")

  // Use explode to create one row for each score
  val explodedDf = data.withColumn("score", explode($"scores"))

  explodedDf.show()
}
