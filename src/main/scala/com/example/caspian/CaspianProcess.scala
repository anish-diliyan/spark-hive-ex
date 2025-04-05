package com.example.caspian

import java.time.LocalDate

trait CaspianProcess {
  def run(ctx: CaspianContext, date: LocalDate, env: CaspianEnv, migration: Boolean = false): Unit
}

