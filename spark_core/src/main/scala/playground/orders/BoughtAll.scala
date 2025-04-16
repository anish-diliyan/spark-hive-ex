package playground.orders

object BoughtAll extends App {

  /*
  SELECT customer_id
FROM Orders o
JOIN OrderDetails od ON o.order_id = od.order_id
JOIN Products p ON od.product_id = p.product_id
WHERE p.category = 'Electronics'
GROUP BY customer_id
HAVING COUNT(DISTINCT p.product_id) = (
    SELECT COUNT(*)
    FROM Products
    WHERE category = 'Electronics'
);

   */

  /*
  val totalElectronicsProducts = productsDF
  .filter($"category" === "Electronics")
  .count()

ordersDF
  .join(orderDetailsDF, "order_id")
  .join(productsDF, "product_id")
  .filter($"category" === "Electronics")
  .groupBy("customer_id")
  .agg(countDistinct("product_id").as("bought_products"))
  .filter($"bought_products" === totalElectronicsProducts)
  .select("customer_id")
  .show()
   */
}
