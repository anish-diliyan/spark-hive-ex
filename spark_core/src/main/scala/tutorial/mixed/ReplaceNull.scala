package tutorial.mixed

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object ReplaceNull extends App {
  val spark: SparkSession = SparkSession
    .builder()
    .appName("Replace Null")
    .config("spark.master", "local")
    .getOrCreate()

  import spark.implicits._
  val data = Seq(
    (1, "John", null),
    (2, "Mary", "HR"),
    (3, "Tom", "IT"),
    (4, "Anna", null)
  ).toDF("id", "name", "department")

  val updatedDf = data.withColumn(
    "department",
    // If department is null, set to "Unknown"
    when($"department".isNull, "Unknown")
    // Otherwise, keep the original value
    .otherwise($"department")
  )
  updatedDf.show()
}
