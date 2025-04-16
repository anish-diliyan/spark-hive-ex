package playground.orders

object Hierarchy extends App {
/*
WITH RECURSIVE EmpHierarchy AS (
    SELECT id, name, manager_id, 1 as level
    FROM Employees
    WHERE manager_id IS NULL
    UNION ALL
    SELECT e.id, e.name, e.manager_id, h.level + 1
    FROM Employees e
    JOIN EmpHierarchy h ON e.manager_id = h.id
)
SELECT * FROM EmpHierarchy;

 */

  /*
  def getHierarchy(df: DataFrame, level: Int = 1): DataFrame = {
  val levelDF = if (level == 1) {
    df.filter($"manager_id".isNull)
      .withColumn("level", lit(level))
  } else {
    df.join(
      getHierarchy(df, level - 1),
      $"manager_id" === getHierarchy(df, level - 1)("id")
    ).withColumn("level", lit(level))
  }
  levelDF
}

// Assuming maximum hierarchy depth of 10
val result = (1 to 10).map(i => getHierarchy(employeesDF, i)).reduce(_ union _)
result.show()
   */
}
