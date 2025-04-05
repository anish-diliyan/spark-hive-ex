package com.example.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object CalendarUtils {

  def parseDateToString(localDate: LocalDate): String = {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    localDate.format(formatter)
  }

}
