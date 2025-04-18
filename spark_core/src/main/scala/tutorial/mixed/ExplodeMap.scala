package tutorial.mixed

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.explode

object ExplodeMap extends App {

  // Initialize SparkSession
  val spark: SparkSession = SparkSession
    .builder()
    .appName("Explode Map")
    .config("spark.master", "local")
    .getOrCreate()

  import spark.implicits._

  // Define case class to enforce schema clarity
  case class Person(id: Int, name: String, subjects: Map[String, Int])

  // Provide the dataset and explicitly apply the case class
  val data = Seq(
    Person(1, "Alice", Map("math" -> 90, "science" -> 85)),  // Alice's map
    Person(2, "Bob", Map("math" -> 70)),                    // Bob's map
    Person(3, "Cathy", Map())                               // Cathy's empty map
  ).toDF()

  // Use explode on the map column to flatten it into rows
  val explodedDf = data.withColumn("subject_score", explode($"subjects"))

  // Show the resulting DataFrame
  explodedDf.show()
}