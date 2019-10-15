// Databricks notebook source
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.client.methods.HttpPost
import org.apache.http.HttpHeaders
import org.apache.http.entity.StringEntity
import com.google.gson.Gson

def sendToPBI(rowAsJson: String ): Unit = {
    val post = new HttpPost("")        
      post.addHeader(HttpHeaders.CONTENT_TYPE, "application/json")   
      post.setEntity(new StringEntity(rowAsJson))
      val response = (HttpClientBuilder.create().build()).execute(post)
}

// COMMAND ----------


import com.microsoft.azure.cosmosdb.spark.schema._
import org.apache.spark.sql.functions._
import com.microsoft.azure.cosmosdb.spark.config.Config
import com.microsoft.azure.cosmosdb.spark.streaming.CosmosDBSourceProvider
import org.apache.spark.sql._

val readConfig = Map(
  "Endpoint" -> "<>",
  "Masterkey" -> "<>",
  "Database" -> "<>",
  "Collection" -> "<>",
  "ReadChangeFeed" -> "true",
  "ChangeFeedQueryName" -> "telemetryChangeFeedQuery",
  "ChangeFeedStartFromTheBeginning" -> "false",
  "InferStreamSchema" -> "true",
  "ChangeFeedCheckpointLocation" -> "dbfs:/Telemetry"
)


val telemetryChangeFeedDF = spark.readStream.format(classOf[CosmosDBSourceProvider].getName).options(readConfig).load()
     .writeStream.outputMode("append").foreachBatch { (batchDF: DataFrame, batchId: Long) =>     
        batchDF.show      
        batchDF.toJSON.foreach { row =>
         sendToPBI(row) 
        }    
    }.start().awaitTermination()
