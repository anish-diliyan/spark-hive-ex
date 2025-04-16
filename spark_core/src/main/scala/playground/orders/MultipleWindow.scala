package playground.orders

object MultipleWindow extends App {
 /*
 WITH OrderStats AS (
  SELECT *,
         DENSE_RANK() OVER (
           PARTITION BY EXTRACT(YEAR FROM order_date),
                        EXTRACT(MONTH FROM order_date)
           ORDER BY order_date
         ) as daily_rank,
         ROW_NUMBER() OVER (
           PARTITION BY customer_id
           ORDER BY order_date
         ) as customer_order_number,
         SUM(total_amount) OVER (
           PARTITION BY customer_id
           ORDER BY order_date
         ) as customer_running_total,
         AVG(total_amount) OVER (
           PARTITION BY EXTRACT(YEAR FROM order_date),
                        EXTRACT(MONTH FROM order_date)
           ORDER BY order_date
           ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
         ) as month_to_date_avg
  FROM Orders
)
SELECT * FROM OrderStats;

  */
}
