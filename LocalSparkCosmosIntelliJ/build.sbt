name := "LocalSparkCosmosIntelliJ"

version := "0.1"

scalaVersion := "2.11.8"

val sparkVersion = "2.4.0"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "com.microsoft.azure" % "azure-cosmosdb-spark_2.4.0_2.11" % "1.3.5",
  "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.9.0"
)