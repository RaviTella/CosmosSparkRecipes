// Databricks notebook source
import org.apache.spark.eventhubs.{ ConnectionStringBuilder, EventHubsConf, EventPosition }
import org.apache.spark.sql.functions.{ explode, split }
import org.apache.spark.sql.functions._

val connectionString = ConnectionStringBuilder("<EVENT HUB CONNECTION STRING>")
  .setEventHubName("<EVENT HUB NAME>")
  .build

val eventHubsConf = EventHubsConf(connectionString)
  .setStartingPosition(EventPosition.fromEndOfStream)
  
val eventhubMessage = spark.readStream
  .format("eventhubs")
  .options(eventHubsConf.toMap)
  .load()


// COMMAND ----------

import org.apache.spark.sql.types._

val telemetrySchema = StructType(Seq(
      StructField("deviceID", StringType, true),
      StructField("temperature", DoubleType, true),
      StructField("pressure", DoubleType, true),
      StructField("eventTime", StringType, true)    
    ))
val telemetry = eventhubMessage.select(from_json('body.cast("string"), telemetrySchema) as "fields").select($"fields.*")

// COMMAND ----------

import com.microsoft.azure.cosmosdb.spark.schema._
import org.apache.spark.sql.functions._
import com.microsoft.azure.cosmosdb.spark.config.Config
import com.microsoft.azure.cosmosdb.spark.streaming.CosmosDBSinkProvider
import org.apache.spark.sql._

val writeConfig = Map(
  "Endpoint" -> "<ENDPOINT>",
  "Masterkey" -> "<MASTER KEY>",
  "Database" -> "DATABASE>",
  "Collection" -> "<COLLECTION>",
  "Upsert" -> "true",
  "WritingBatchSize" -> "500",
  "checkpointLocation" -> "dbfs:/checkpointlocation_telemetry"
)

 telemetry
.writeStream
.format(classOf[CosmosDBSinkProvider].getName)
.options(writeConfig)
.start()
.awaitTermination()

