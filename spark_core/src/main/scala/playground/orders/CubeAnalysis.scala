package playground.orders

object CubeAnalysis extends App {

  /*
  SELECT region,
       product_category,
       year,
       SUM(amount) as total_sales,
       AVG(amount) as avg_sale,
       COUNT(*) as num_transactions,
       GROUPING_ID(region, product_category, year) as grouping_level
FROM Sales
GROUP BY CUBE(region, product_category, year)
ORDER BY grouping_level;

   */
}
