package playground.orders

object QualityChecks extends App {
 /*
 WITH QualityIssues AS (
  SELECT *,
    ARRAY_REMOVE(ARRAY[
      CASE
        WHEN email IS NULL OR email NOT REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,6}$'
        THEN 'invalid_email'
      END,
      CASE
        WHEN phone IS NULL OR phone NOT REGEXP '^\+?[1-9]\d{1,14}$'
        THEN 'invalid_phone'
      END,
      CASE
        WHEN birth_date > CURRENT_DATE OR
             DATEDIFF(CURRENT_DATE, birth_date)/365.25 > 120
        THEN 'invalid_birth_date'
      END
    ], NULL) as quality_issues
  FROM Users
)
SELECT quality_issues,
       COUNT(*) as records_affected
FROM QualityIssues
WHERE ARRAY_LENGTH(quality_issues, 1) > 0
GROUP BY quality_issues;

  */
}
