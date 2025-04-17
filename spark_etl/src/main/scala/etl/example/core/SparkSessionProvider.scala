package etl.example.core

import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.SparkSession

object SparkSessionProvider {
  private lazy val session: SparkSession = createSession()

  private def createSession(): SparkSession = {
    val config = ConfigFactory.load()
    val appName = config.getString("spark.app.name")
    val master = config.getString("spark.master")
    val warehouseDir = config.getString("spark.sql.warehouse.dir")
    println("config values is: " + appName + " " + master + " " + warehouseDir)
    try {
      SparkSession
        .builder()
        .appName(appName)
        .master(master)
        .config("spark.sql.warehouse.dir", warehouseDir)
        .enableHiveSupport()
        .getOrCreate()
    } catch {
      case e: Exception =>
        e.printStackTrace()
        throw e
    }
  }
  def getSession: SparkSession = session
}
