// Databricks notebook source
import com.microsoft.azure.eventhubs._
import java.util.concurrent._
import org.apache.spark.eventhubs.{ ConnectionStringBuilder, EventHubsConf, EventPosition }
import org.apache.spark.sql.functions.{ explode, split }

val connStr = ConnectionStringBuilder("<EVENT HUB CONNECTION STRING>")
  .setEventHubName("<EVENT HUB NAME>")
  .build
 val pool = Executors.newScheduledThreadPool(1)
 val eventHubClient = EventHubClient.create(connStr.toString(), pool)

// COMMAND ----------

val deviceIDs = List("101", "102")
val temperature = List(150, 170, 120, 220, 160,130, 190, 175, 235, 185)
val pressure = List(15, 17, 12, 22, 16,13, 19, 17, 23, 18)
case class Telemetry(deviceID: String, temperature: Integer, pressure: Integer, eventTime: String)

// COMMAND ----------

import com.google.gson.Gson
import org.joda.time
import org.joda.time.format._
import java.sql.Timestamp
import scala.util.Random

def getTelemetry() : String = {
  val dateTime = new Timestamp(System.currentTimeMillis()) 
  val telemetry = Telemetry(deviceIDs(Random.nextInt(deviceIDs.size)),temperature(Random.nextInt(temperature.size)),pressure(Random.nextInt(pressure.size)),dateTime.toString);   val gson = new Gson
  val jsonString = gson.toJson(telemetry)
  return jsonString
}


// COMMAND ----------

def sendEvents(delay: Long) = {
  for(i <- 1 to 10){
    Thread.sleep(delay)
    var message = getTelemetry()
    val messageData= EventData.create(message.getBytes("UTF-8"))
    eventHubClient.get().sendSync(messageData)
    System.out.println("Sent event: " + message + "\n")
    }
  }

    sendEvents(5000)
