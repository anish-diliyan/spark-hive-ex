package playground.orders

object DistanceCalculation extends App {
 /*
 WITH DistanceCalc AS (
  SELECT *,
         -- Note: This is a simplified version. Real SQL implementation would need
         -- a custom function for the Haversine formula
         SQRT(
           POWER(69.1 * (start_lat - end_lat), 2) +
           POWER(69.1 * (start_lon - end_lon) * COS(start_lat / 57.3), 2)
         ) * 1609.34 as distance
  FROM Locations
)
SELECT
  CASE
    WHEN distance <= 1000 THEN 'short'
    WHEN distance <= 5000 THEN 'medium'
    ELSE 'long'
  END as distance_category,
  COUNT(*) as num_trips,
  AVG(distance) as avg_distance
FROM DistanceCalc
GROUP BY
  CASE
    WHEN distance <= 1000 THEN 'short'
    WHEN distance <= 5000 THEN 'medium'
    ELSE 'long'
  END;

  */
}
