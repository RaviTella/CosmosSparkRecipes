![](logo.png)

# Overview
This sample demonstrates a recipe for consuming events from Event Hub using structured spark streaming and streaming them to Cosmos DB.

# Getting started
## First:
* Create Azure Databricks cluster
* Install the latest azure-cosmosdb-spark_[version]-uber jar for the version of Apache Spark you are running
* Create a Cosmos DB collection for SQL API. Use "/deviceID" as the partition key
* Create an EventHub 

## Then:
* Import "EventHubToCosmosStreaming.scala" and "SendToEventHub.scala" into Databricks workspace
* Update the connection and configuration placeholders with appropriate values 
* Run "EventHubToCosmosStreaming.scala" notebook to get the structured spark streaming job started
* Run "SendToEventHub.scala" to publish telemetry to Event Hub
* Check Cosmos DB container for the new telemetry
