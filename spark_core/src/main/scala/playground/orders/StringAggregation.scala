package playground.orders

object StringAggregation extends App {

  /*
  -- Using PostgreSQL's STRING_AGG or MySQL's GROUP_CONCAT
SELECT o.customer_id,
       STRING_AGG(p.product_name, ' | ') as formatted_products,
       COUNT(*) as total_items
FROM Orders o
JOIN OrderDetails od ON o.order_id = od.order_id
JOIN Products p ON od.product_id = p.product_id
GROUP BY o.customer_id
HAVING COUNT(DISTINCT p.product_name) > 5;

   */
}
