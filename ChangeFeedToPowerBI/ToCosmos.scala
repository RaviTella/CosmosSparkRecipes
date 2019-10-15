// Databricks notebook source
val recordsToSend = 200   
val deviceIDs = List("101", "102")
val temperature = List(150, 170, 120, 220, 160,130, 190, 175, 235, 185)
val pressure = List(15, 17, 12, 22, 16,13, 19, 17, 23, 18)
case class Telemetry(deviceID: String, temperature: Integer, pressure: Integer, eventTime: String)

// COMMAND ----------

import org.joda.time
import org.joda.time.format._
import java.sql.Timestamp
import scala.util.Random
import scala.collection.mutable.ListBuffer

def getTelemetry() : List[Telemetry] = {
  var telemetry = new ListBuffer[Telemetry]()
  for (a <- 1 to recordsToSend) {
     val dateTime = new Timestamp(System.currentTimeMillis()) 
     telemetry += Telemetry(deviceIDs(Random.nextInt(deviceIDs.size)),temperature(Random.nextInt(temperature.size)),pressure(Random.nextInt(pressure.size)),dateTime.toString);
    }
  return telemetry.toList
}

// COMMAND ----------

import com.microsoft.azure.cosmosdb.spark.schema._
import com.microsoft.azure.cosmosdb.spark._
import com.microsoft.azure.cosmosdb.spark.config.Config

val writeConfig = Config(Map(
  "Endpoint" -> "",
  "Masterkey" -> "",
  "Database" -> "",
  "Collection" -> "",
  "Upsert" -> "true"
))

import org.apache.spark.sql.SaveMode
getTelemetry().toDF().write.mode(SaveMode.Overwrite).cosmosDB(writeConfig)

// COMMAND ----------


