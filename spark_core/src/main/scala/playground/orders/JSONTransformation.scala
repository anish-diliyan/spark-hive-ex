package playground.orders

object JSONTransformation extends App {
 /*
 WITH JsonParsed AS (
  SELECT user_id,
         event_type,
         JSON_TABLE(
           properties,
           '$' COLUMNS (
             property_name VARCHAR(255) PATH '$.name',
             property_value VARCHAR(255) PATH '$.value'
           )
         ) as parsed_properties
  FROM Events
),
PivotedData AS (
  SELECT user_id,
         event_type,
         MAX(CASE WHEN property_name = 'property1' THEN property_value END) as property1,
         MAX(CASE WHEN property_name = 'property2' THEN property_value END) as property2
         -- Add more properties as needed
  FROM JsonParsed
  GROUP BY user_id, event_type
)
SELECT *,
       (CASE WHEN property1 IS NOT NULL OR property2 IS NOT NULL THEN 1 ELSE 0 END)
         as has_required_properties,
       -- Count non-null properties
       (CASE WHEN property1 IS NOT NULL THEN 1 ELSE 0 END +
        CASE WHEN property2 IS NOT NULL THEN 1 ELSE 0 END)
         as total_properties
FROM PivotedData;

  */
}
