package playground

import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{DataFrame, SparkSession}

class InputDataReadEmp(spark: SparkSession) {

  private val filePath: String = "data-in/core/playground/employee/employees_database.json"
  private val databaseDF: DataFrame = spark.read.option("multiline", "true").json(filePath)

  def getComponentDF(componentName: String): DataFrame = {
    databaseDF.selectExpr(s"explode($componentName) as $componentName")
      .select(col(s"$componentName.*"))
  }

  def getEmployeesDF: DataFrame = getComponentDF("Employees")

  def getDepartmentsDF: DataFrame = getComponentDF("Departments")

  def getLocationsDF: DataFrame = getComponentDF("Locations")

  def getProjectsDF: DataFrame = getComponentDF("Projects")

  def getSalariesDF: DataFrame = getComponentDF("Salaries")
}
