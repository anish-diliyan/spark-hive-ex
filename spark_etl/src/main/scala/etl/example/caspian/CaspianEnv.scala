package etl.example.caspian

sealed trait CaspianEnv

object CaspianEnv {
  case object DEV extends CaspianEnv
}

