package playground.orders

object YearGrowth extends App {
  /*
  SELECT
    YEAR(order_date) as year,
    MONTH(order_date) as month,
    SUM(total_amount) as current_amount,
    LAG(SUM(total_amount), 12) OVER (ORDER BY order_date) as prev_year_amount,
    ((SUM(total_amount) - LAG(SUM(total_amount), 12) OVER (ORDER BY order_date)) /
     LAG(SUM(total_amount), 12) OVER (ORDER BY order_date)) * 100 as yoy_growth
FROM Orders
GROUP BY YEAR(order_date), MONTH(order_date)
ORDER BY year, month;

   */

  /*
  import org.apache.spark.sql.expressions.Window

val windowSpec = Window.orderBy("year", "month")

ordersDF
  .withColumn("year", year($"order_date"))
  .withColumn("month", month($"order_date"))
  .groupBy("year", "month")
  .agg(sum("total_amount").as("current_amount"))
  .withColumn("prev_year_amount",
    lag("current_amount", 12).over(windowSpec))
  .withColumn("yoy_growth",
    ((($"current_amount" - $"prev_year_amount") / $"prev_year_amount") * 100))
  .show()
   */

}
