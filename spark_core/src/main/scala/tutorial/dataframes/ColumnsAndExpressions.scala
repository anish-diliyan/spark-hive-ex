package tutorial.dataframes

import org.apache.spark.sql.functions.{col, column, expr}
import org.apache.spark.sql.{Column, DataFrame, SparkSession}

object ColumnsAndExpressions extends App {
  private val spark = SparkSession
    .builder()
    .appName("Columns And Expressions")
    .config("spark.master", "local")
    .getOrCreate()

  val carsDF: DataFrame = spark.read
    .option("inferSchema", "true")
    .json("data/core/cars.json")

  carsDF.show()

  // Columns
  val firstColumn: Column = carsDF.col("Name")

  // Selecting a name column from carsDF
  val carsNameDF: DataFrame = carsDF.select(firstColumn)
  carsNameDF.show()

  // Various ways to select columns
  import spark.implicits._ // Needed for Scala Symbol and interpolated string
  carsDF.select(
    carsDF.col("Name"),
    col("Acceleration"),
    column("Weight_in_lbs"),
    'Year, // Scala Symbol, auto converted to Column
    $"Horsepower", // interpolated string, returns Column
    expr("Origin") // Expression
  )
  carsDF.select("Name", "Year")

  // EXPRESSIONS: Selecting columns name is the simplest version of expression
  val simpleExpression: Column = carsDF.col("Weight_in_lbs")
  val weightInKilogram = simpleExpression / 2.2

  val carsWithWeightsDF: DataFrame = carsDF.select(
    col("Name"),
    simpleExpression,
    weightInKilogram.as("Weight_in_kg"),
    expr("Weight_in_lbs / 2.2").as("Weight_in_kg_2")
  )

  carsWithWeightsDF.show()

  val carsWithSelectExprWeightsDF: DataFrame = carsDF.selectExpr(
    "Name",
    "Weight_in_lbs / 2.2 as Weight_in_kg"
  )

  carsWithSelectExprWeightsDF.show()

  // Adding a new column
  val carsWithKilogramDF: DataFrame = carsDF.withColumn("Weight_in_kg", col("Weight_in_lbs") / 2.2)

  // Renaming a column
  val carsWithColumnRenamedDF: DataFrame = carsWithKilogramDF.withColumnRenamed("Weight_in_kg", "Weight in kg")

  // careful with column names inside expr
  carsDF.selectExpr("Weight_in_lbs / 2.2 as `Weight in Kg`").show()

  // remove a column
  carsDF.drop("Weight_in_lbs", "Name").show()
}
