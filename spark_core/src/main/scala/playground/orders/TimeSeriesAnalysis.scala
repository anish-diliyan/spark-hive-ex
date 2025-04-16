package playground.orders

object TimeSeriesAnalysis extends App {
 /*
 WITH RECURSIVE dates AS (
  SELECT MIN(DATE(order_date)) as date
  FROM Orders
  UNION ALL
  SELECT date + INTERVAL '1 day'
  FROM dates
  WHERE date < (SELECT MAX(DATE(order_date)) FROM Orders)
),
daily_amounts AS (
  SELECT d.date,
         COALESCE(AVG(o.total_amount), 0) as daily_amount
  FROM dates d
  LEFT JOIN Orders o ON DATE(o.order_date) = d.date
  GROUP BY d.date
)
SELECT date,
       AVG(daily_amount) OVER (
         ORDER BY date
         ROWS BETWEEN 7 PRECEDING AND CURRENT ROW
       ) as moving_avg_7day
FROM daily_amounts
ORDER BY date;

  */
}
