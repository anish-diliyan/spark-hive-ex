package playground.orders

object Sessionization extends App {

  /*
  WITH SessionBoundaries AS (
  SELECT user_id,
         event_timestamp,
         CASE
           WHEN LAG(event_timestamp) OVER (PARTITION BY user_id ORDER BY event_timestamp) IS NULL
             OR UNIX_TIMESTAMP(event_timestamp) -
                UNIX_TIMESTAMP(LAG(event_timestamp) OVER (PARTITION BY user_id ORDER BY event_timestamp)) > 1800
           THEN 1
           ELSE 0
         END as session_start
  FROM Events
)
SELECT user_id,
       event_timestamp,
       SUM(session_start) OVER (PARTITION BY user_id ORDER BY event_timestamp) as session_id
FROM SessionBoundaries;

   */

}
