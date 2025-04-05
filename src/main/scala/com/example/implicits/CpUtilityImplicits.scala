package com.example.implicits

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

object CpUtilityImplicits {

  // Move regex patterns to constants to avoid recompilation
  private val MULTIPLE_SPACES_PATTERN = "\\s+"
  private val NEWLINE_TAB_PATTERN = "[\r\n\t]"
  private val HIDDEN_CHARS_PATTERN = "[^\\x20-\\x7E]"
  private val SPECIAL_CHARS_PATTERN = "[^a-zA-Z0-9]"

  implicit class CpClCpUtilityImplicits(cpDf: DataFrame) {
    def cleanse: DataFrame = {
      val columnsToClean = Set("NAME, ADDRESS, BANK_ACCT, BANK_NAME, BANK_ID, BANK_ADDRESS") // Columns to be cleansed

      def cleanseColumn(columnName: String) = { // Function to create cleaning expression for a column
        val baseCol = col(columnName) // Create base column reference once
        val cleanedCol = trim(  // Trim leading and trailing spaces
          regexp_replace(
            regexp_replace(
              regexp_replace(
                lower(baseCol),  // Convert to lowercase
                MULTIPLE_SPACES_PATTERN, " "),  // Replace multiple spaces with single space
              NEWLINE_TAB_PATTERN, ""),  // Remove newlines and tabs
            HIDDEN_CHARS_PATTERN, "") // Remove hidden characters
        )
        // Condition to nullify if only special characters remain
        when(
          baseCol.isNull
            || trim(baseCol) === ""
            || trim(cleanedCol) === ""
            || regexp_replace(cleanedCol, SPECIAL_CHARS_PATTERN, "") === "",
          lit(null)
        ).otherwise(cleanedCol)
      }

      // Apply transformations only to specified columns
      columnsToClean.foldLeft(cpDf) { (df, colName) =>
        if (df.columns.contains(colName)) df.withColumn(colName, cleanseColumn(colName))
        else df
      }
    }
  }

}
