package com.example.utils

import org.apache.spark.sql.functions.{col, lit}
import org.apache.spark.sql.{Column, DataFrame}

object DataFrameUtils {

  def sefUnionAsMissingColumnAsNUll(dataFrames: Seq[DataFrame]): Option[DataFrame] = {
     dataFrames match {
       case Nil => None
       case null => None
       case _ => Some( dataFrames.reduce{ (df1, df2) =>
         val df1Cols = df1.columns.toSet
         val df2Cols = df2.columns.toSet
         val totalCols = df1Cols ++ df2Cols
         df1.select(addMissingCols(df1Cols, totalCols): _*).unionByName(df2.select(addMissingCols(df2Cols, totalCols): _*))
       })
     }
  }

  def addMissingCols(dfCols: Set[String], combinedCols: Set[String]): List[Column] = {
     combinedCols.toList.map {
       case colName if dfCols.contains(colName) => col(colName)
       case colName => lit(null).as(colName)
     }
  }

}
