package com.example.implicits

import org.apache.spark.sql.DataFrame

object ColumnImplicits {

  implicit class RenameColumn(df: DataFrame) {
    def columnRenamed(renames: Map[String, String]): DataFrame = {
      renames.foldLeft(df)((origDf, item) => origDf.withColumnRenamed(item._1, item._2))
    }
  }

}
