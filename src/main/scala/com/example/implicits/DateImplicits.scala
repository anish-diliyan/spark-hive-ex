package com.example.implicits

import com.example.utils.CalendarUtils

import java.time.LocalDate

object DateImplicits {
   implicit class CspDateString(date: LocalDate) {
      def toCspDateString: String = CalendarUtils.parseDateToString(date)
   }
}
