package com.example.core

import org.slf4j.{Logger, LoggerFactory}

trait LoggerImpl {
  private val logger: Logger = LoggerFactory.getLogger(getClass)

   def debug(message: String): Unit = {
    if (logger.isDebugEnabled) {
      logger.debug(message)
    }
  }

   def info(message: String): Unit = {
    if (logger.isInfoEnabled) {
      logger.info(message)
    }
  }

   def warn(message: String): Unit = {
    if (logger.isWarnEnabled) {
      logger.warn(message)
    }
  }

   def error(message: String): Unit = {
    logger.error(message)
  }

   def error(message: String, throwable: Throwable): Unit = {
    logger.error(message, throwable)
  }
}

