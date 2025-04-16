package playground.orders

object RollingSum extends App {

  /*
  SELECT order_date,
       total_amount,
       SUM(total_amount) OVER (
           ORDER BY order_date
           RANGE BETWEEN INTERVAL '30' DAY PRECEDING AND CURRENT ROW
       ) as rolling_30_day_sum
FROM Orders;

   */

  /*
  import org.apache.spark.sql.expressions.Window

val thirtyDayWindow = Window
  .orderBy("order_date")
  .rangeBetween(-30 * 86400, 0)  // 30 days in seconds

ordersDF
  .withColumn("rolling_30_day_sum",
    sum($"total_amount").over(thirtyDayWindow))
  .show()

   */
}
