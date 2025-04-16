package playground.orders

object IncPurchase extends App {

   /*
   SELECT customer_id
FROM (
    SELECT customer_id,
           order_date,
           total_amount,
           LAG(total_amount) OVER (PARTITION BY customer_id ORDER BY order_date) as prev_amount
    FROM Orders
) t
GROUP BY customer_id
HAVING COUNT(CASE WHEN total_amount > prev_amount THEN 1 END) >= 3;

    */

  /*
  val windowSpec = Window.partitionBy("customer_id").orderBy("order_date")

ordersDF
  .withColumn("prev_amount", lag("total_amount", 1).over(windowSpec))
  .filter($"total_amount" > $"prev_amount")
  .groupBy("customer_id")
  .agg(count("*").as("increasing_orders"))
  .filter($"increasing_orders" >= 3)
  .select("customer_id")
  .show()

   */
}
