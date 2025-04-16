package playground.orders

object ProductPairs extends App {
 /*
 SELECT p1.product_name,
       p2.product_name,
       COUNT(*) as pair_count
FROM OrderDetails od1
JOIN OrderDetails od2 ON od1.order_id = od2.order_id
JOIN Products p1 ON od1.product_id = p1.product_id
JOIN Products p2 ON od2.product_id = p2.product_id
WHERE od1.product_id < od2.product_id
GROUP BY p1.product_name, p2.product_name
HAVING COUNT(*) > 10;

  */

  /*
  val od1 = orderDetailsDF.alias("od1")
val od2 = orderDetailsDF.alias("od2")
val p1 = productsDF.alias("p1")
val p2 = productsDF.alias("p2")

od1.join(od2, od1("order_id") === od2("order_id"))
  .filter(od1("product_id") < od2("product_id"))
  .join(p1, od1("product_id") === p1("product_id"))
  .join(p2, od2("product_id") === p2("product_id"))
  .groupBy(p1("product_name"), p2("product_name"))
  .agg(count("*").as("pair_count"))
  .filter($"pair_count" > 10)
  .show()

   */
}
