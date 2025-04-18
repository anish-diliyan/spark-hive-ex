package tutorial.mixed

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object MultipleWhen extends App {

  val spark: SparkSession = SparkSession
    .builder()
    .appName("Multiple When")
    .config("spark.master", "local")
    .getOrCreate()

  val df = spark.createDataFrame(Seq(
    (1, "John", 80),
    (2, "Mary", 40),
    (3, "Tom", 60),
    (4, "Anna", 90)
  )).toDF("id", "name", "score")

  import spark.implicits._
  val dfWithGrade = df.withColumn(
    "grade",
    when($"score" >= 85, "A")        // If score >= 85, assign "A"
    .when($"score" >= 70, "B")      // Else if score >= 70, assign "B"
    .when($"score" >= 50, "C")      // Else if score >= 50, assign "C"
    .otherwise("F")                 // Else assign "F" as the default
  )
  dfWithGrade.show()
}
