ThisBuild / name := "spark-learning"
ThisBuild / version := "0.1.0"
ThisBuild / scalaVersion := "2.12.15"

// Versions as val for better maintenance
val sparkVersion = "3.3.2"
val postgresVersion = "42.6.0"
val log4jVersion = "2.20.0"

ThisBuild / resolvers ++= Seq(
  "Maven Central" at "https://repo1.maven.org/maven2/",
  "Apache Public" at "https://repository.apache.org/content/groups/public/"
)

lazy val commonDependencies = Seq(
  // Spark Core Dependencies
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-hive" % sparkVersion % "provided",

  // Postgres Dependencies
  "org.postgresql" % "postgresql" % postgresVersion,

  // config reader dependency
  "com.typesafe" % "config" % "1.4.2",

  // Logging Dependencies
  "org.apache.logging.log4j" % "log4j-api" % log4jVersion,
  "org.apache.logging.log4j" % "log4j-core" % log4jVersion,
  "org.apache.logging.log4j" % "log4j-slf4j-impl" % log4jVersion,

  // Test Dependencies
  "org.scalatest" %% "scalatest" % "3.2.15" % Test
)

lazy val spark_etl = (project in file("spark_etl"))
  .settings(
    name := "spark_etl",
    libraryDependencies ++= commonDependencies
  )

lazy val spark_core = (project in file("spark_core"))
  .settings(
    name := "spark_core",
    libraryDependencies ++= commonDependencies
  )