package playground.orders

object PatternMatching extends App {

  /*
  WITH ParsedLogs AS (
  SELECT
    REGEXP_EXTRACT(log_line, '(\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3})', 1) as ip_address,
    REGEXP_EXTRACT(log_line, '\[(.*?)\]', 1) as timestamp,
    REGEXP_EXTRACT(log_line, '"(\w+)\s+([^"]+)', 1) as request_type,
    REGEXP_EXTRACT(log_line, '"(\w+)\s+([^"]+)', 2) as url
  FROM Logs
)
SELECT ip_address,
       COUNT(DISTINCT url) as unique_urls,
       STRING_AGG(DISTINCT request_type, ',') as request_types
FROM ParsedLogs
GROUP BY ip_address;

   */
}
