package com.example.core

import org.apache.spark.sql.SparkSession

import java.io.File
import scala.util.control.NonFatal
import scala.util.{Try, Failure => TryFailure, Success => TrySuccess}
import java.security.MessageDigest
import org.apache.commons.codec.binary.Hex

sealed trait MigrationStatus
case object Success extends MigrationStatus
case class Failure(error: String) extends MigrationStatus
case class MigrationResult(scriptName: String, status: MigrationStatus)

class HiveMigrationHandler(spark: SparkSession) {

  def executeMigration(): List[MigrationResult] = {
    initializeMigrationTable()
    val scriptsToExecute = getPendingMigrationScripts
    scriptsToExecute.map(executeMigrationScript)
  }

  private def initializeMigrationTable(): Unit = {
    spark.sql("CREATE DATABASE IF NOT EXISTS migration")
    spark.sql("""
      CREATE TABLE IF NOT EXISTS migration.migration_history (
        script_name STRING,
        status STRING,
        error_message STRING,
        content_hash STRING,
        execution_timestamp TIMESTAMP
      ) USING hive
    """)
  }

  private def calculateFileHash(content: String): String = {
    val digest = MessageDigest.getInstance("SHA-256")
    val hash = digest.digest(content.getBytes("UTF-8"))
    Hex.encodeHexString(hash)
  }

  private def getPendingMigrationScripts: List[String] = {
    val allScripts = getMigrationFilesName

    // Get executed scripts info from migration_history
    val executedScriptsDF = spark.sql("""
    SELECT script_name, status, content_hash
    FROM migration.migration_history
    WHERE (script_name, execution_timestamp) IN (
      SELECT script_name, MAX(execution_timestamp)
      FROM migration.migration_history
      GROUP BY script_name
    )
  """)

    // Convert DataFrame to Map[String, (String, String)]
    val executedScriptsInfo = executedScriptsDF.collect().map { row =>
      val scriptName = row.getString(0)
      val status = row.getString(1)
      val hash = row.getString(2)
      scriptName -> (status, hash)
    }.toMap

    // Filter scripts that need to be executed
    allScripts.filter { scriptName =>
      val currentContent = scala.io.Source.fromResource(s"migrations/$scriptName").mkString
      val currentHash = calculateFileHash(currentContent)

      executedScriptsInfo.get(scriptName) match {
        case Some((status, storedHash)) => status == "FAILURE" || storedHash != currentHash
        case None => true
      }
    }
  }

  private def recordMigration(result: MigrationResult, errorMessage: Option[String] = None): Unit = {
    val status = result.status match {
      case Success => "SUCCESS"
      case Failure(_) => "FAILURE"
    }

    val content = scala.io.Source.fromResource(s"migrations/${result.scriptName}").mkString
    val contentHash = calculateFileHash(content)

    spark.sql(s"""
      INSERT INTO migration.migration_history
      VALUES (
        '${result.scriptName}',
        '$status',
        ${errorMessage.map(msg => s"'$msg'").getOrElse("NULL")},
        '$contentHash',
        current_timestamp()
      )
    """)
  }

  private def executeMigrationScript(scriptName: String): MigrationResult = {
    try {
      val script = scala.io.Source.fromResource(s"migrations/$scriptName").mkString
      val statements = script.split(";").map(_.trim).filter(_.nonEmpty)

      val executionResult = statements.foldLeft[Try[Unit]](TrySuccess(())) {
        case (TrySuccess(_), statement) => Try(spark.sql(statement))
        case (failure @ TryFailure(_), _) => failure
      }

      executionResult match {
        case TrySuccess(_) =>
          val result = MigrationResult(scriptName, Success)
          recordMigration(result)
          result
        case TryFailure(exception) =>
          val errorMessage = Option(exception.getMessage).getOrElse("Unknown error")
          val result = MigrationResult(scriptName, Failure(errorMessage))
          recordMigration(result, Some(errorMessage))
          result
      }
    } catch {
      case NonFatal(e) =>
        val errorMessage = Option(e.getMessage).getOrElse("Unknown error")
        val result = MigrationResult(scriptName, Failure(errorMessage))
        recordMigration(result, Some(errorMessage))
        result
    }
  }

  private def getMigrationFilesName: List[String] = {
    val migrationDir = new File("src/main/resources/migrations")
    migrationDir
      .listFiles()
      .filter(_.getName.endsWith(".hql"))
      .sortBy(_.getName)
      .toList
      .map(_.getName)
  }
}
