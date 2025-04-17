package playground

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions.col

class InputDataReadOrd(spark: SparkSession) {

  private val filePath: String = "data-in/core/playground/orders/orders_database.json"
  private val databaseDF: DataFrame = spark.read.option("multiline", "true").json(filePath)

  def getComponentDF(componentName: String): DataFrame = {
    databaseDF.selectExpr(s"explode($componentName) as $componentName")
      .select(col(s"$componentName.*"))
  }

  def getProductsDF: DataFrame = getComponentDF("Products")

  def getCustomersDF: DataFrame = getComponentDF("Customers")

  def getOrdersDF: DataFrame = getComponentDF("Orders")

  def getOrderDetailsDF: DataFrame = getComponentDF("Order_Details")
}
